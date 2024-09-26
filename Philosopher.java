// Philosopher.java
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Philosopher extends Thread {
    private final int id;
    private final Fork leftFork;
    private final Fork rightFork;
    private final Table table;
    private boolean movedToSixthTable = false;
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
                if (pickUpForks()) {
                    eat();
                    putDownForks();
                }
                if (table.isDeadlocked()) {
                    moveToSixthTable();
                }
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
        if (!leftFork.pickUp()) {
            return false;
        }

        // Wait for 4 seconds, then try to pick up the right fork
        System.out.println("Philosopher " + id + " picked up the left fork.");
        if (!rightFork.pickUp()) {
            leftFork.putDown(); // Couldn't pick up right fork, put down the left
            System.out.println("Philosopher " + id + " couldn't pick up the right fork. Putting down the left fork.");
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
}
