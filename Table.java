// Table.java
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Table {
    private final List<Philosopher> philosophers = new ArrayList<>();
    private final Fork[] forks;
    public static int lastPhilosopherAtTable3;
    public static boolean[] isTableDeadLocked = {false, false, false};
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
            // System.err.println(waitingPhilosophers + " are waiting to get the right fork ");
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
            philosophers.remove(philosopher);
            philosopher.updateInfo(pos);
            pos++;
            FillTable.appendPhilosopher(2, philosopher);
            System.out.println("Philosopher " + philosopher.getPhilosopherId() + " has moved to the sixth table.");
            // philosopher.start();
            lastPhilosopherAtTable3 = philosopher.getPhilosopherId();
            deadlockDetected = false; // Reset the deadlock flag after moving
        } finally {
            lock.unlock();
        }
    }
}
