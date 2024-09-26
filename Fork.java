// Fork.java
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Fork {
    private final Lock lock = new ReentrantLock();

    public boolean pickUp() {
        return lock.tryLock(); // Try to acquire the fork (lock)
    }

    public void putDown() {
        lock.unlock(); // Release the fork (lock)
    }
}
