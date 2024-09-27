// Table.java
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Table {
    private final List<Philosopher> philosophers = new ArrayList<>();
    private final Fork[] forks;
    private final Lock lock = new ReentrantLock(); // To synchronize philosopher movement
    private static final int MAX_PHILOSOPHERS = 2;
    private boolean deadlockDetected = false;
    private int pos = 0;

    public Table(Fork[] forks) {
        this.forks = forks;
    }

    public void addPhilosopher(Philosopher philosopher) {
        philosophers.add(philosopher);
    }

    public boolean isDeadlocked() {
        lock.lock();
        try {
            // Simple deadlock detection (all philosophers waiting for a fork)
            long waitingPhilosophers = philosophers.stream().filter(Philosopher::isWaitingForRightFork).count();
            System.err.println(waitingPhilosophers + " are waiting to get the right fork ");
            if (waitingPhilosophers == MAX_PHILOSOPHERS) {
                deadlockDetected = true;
            }
            return deadlockDetected;
        } finally {
            lock.unlock();
        }
    }

    public void moveToSixthTable(Philosopher philosopher) {
        lock.lock();
        try {
            if (!deadlockDetected) return;
            if(pos > 5){
                DiningPhilosophers.deadLockAtLastTable = true;
                System.out.println("Philosopher " + philosopher.id + " moved to sixth table before a dead lock");
                lock.unlock();
                return;
            }
            philosophers.remove(philosopher);
            philosopher.leftFork = FillTable.forks[5][pos];
            philosopher.rightFork = FillTable.forks[5][(pos + 1) % FillTable.philosophersPerTable];
            pos++;
            philosopher.table = FillTable.Table[5]
            FillTable.appendPhilosopher(5, philosopher)
            System.out.println("Philosopher " + philosopher.getPhilosopherId() + " has moved to the sixth table.");
            deadlockDetected = false; // Reset the deadlock flag after moving
        } finally {
            lock.unlock();
        }
    }
}
