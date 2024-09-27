// Philosopher.java
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Philosopher extends Thread {
    public static final int id;
    public static Fork leftFork;
    public static Fork rightFork;
    public static Table table;
    public boolean movedToSixthTable = false;
    public boolean isWaitingForRightFork = false;
    public boolean hasLeftFork = false;
    private static final Random random = new Random();

    public Philosopher(int id, Fork leftFork, Fork rightFork, Table table) {
        this.id = id;
        this.leftFork = leftFork;
        this.rightFork = rightFork;
        this.table = table;
    }

    @Override
    public void run() {
        try {
            while (!movedToSixthTable) {
                think();
                hungry();
                while(!pickUpForks()){}
                eat();
                putDownForks();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void think() throws InterruptedException {
        System.out.println("Philosopher " + id + " is thinking.");
        Thread.sleep(random.nextInt(10000)); // Simulate thinking (0 to 10 seconds)
    }

    private void hungry() {
        System.out.println("Philosopher " + id + " is hungry.");
    }

    private boolean pickUpForks() throws InterruptedException {
        // Try to pick up the left fork first
        if(hasLeftFork){
            System.out.println("Philosopher " + id + " is trying to pick the right fork");
        }
        else if(!leftFork.pickUp()) {
            System.err.println("Philosopher " + id + " failed to pick the left fork");
            return false;   
        }
        if(!hasLeftFork) 
            System.err.println("Philosopher " + id + " picked the left fork");
        hasLeftFork = true;

        Thread.sleep(4000); // Wait for 4 seconds, then try to pick up the right fork 
        if (!rightFork.pickUp()) {

            isWaitingForRightFork = true;
            if (table.isDeadlocked()) {
                System.err.println("Deadlock detected");
                moveToSixthTable();
            }

            System.out.println("Philosopher " + id + " failed to pick the right fork");
           
            return false;
        }

        System.out.println("Philosopher " + id + " picked up both forks.");
        return true;
    }

    private void eat() throws InterruptedException {
        System.out.println("Philosopher " + id + " is eating.");
        Thread.sleep(random.nextInt(5000)); // Simulate eating (0 to 5 seconds)
    }

    private void putDownForks() {
        leftFork.putDown();
        rightFork.putDown();
        hasLeftFork = false;
        isWaitingForRightFork = false;
        System.out.println("Philosopher " + id + " put down both forks.");
    }

    private void moveToSixthTable() {
        System.out.println("Philosopher " + id + " is moving to the sixth table due to deadlock.");
        movedToSixthTable = true;
        table.moveToSixthTable(this); // Notify the table about the move
    }

    public int getPhilosopherId() {
        return id;
    }
    public boolean isWaitingForRightFork() {
        System.err.println(id + " " + isWaitingForRightFork);
        return isWaitingForRightFork;
    }
}
