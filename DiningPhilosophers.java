// DiningPhilosophers.java
public class DiningPhilosophers {
    public static void main(String[] args) {
        final int numTables = 6;
        final int philosophersPerTable = 5;

        Table[] tables = new Table[numTables];
        Fork[][] forks = new Fork[numTables][philosophersPerTable];

        // Initialize forks for each table
        for (int t = 0; t < numTables; t++) {
            for (int f = 0; f < philosophersPerTable; f++) {
                forks[t][f] = new Fork();
            }
            tables[t] = new Table(forks[t]);
        }

        // Create philosophers for the first 5 tables
        for (int t = 0; t < numTables - 1; t++) {
            for (int p = 0; p < philosophersPerTable; p++) {
                Philosopher philosopher = new Philosopher(
                    p + t * philosophersPerTable, 
                    forks[t][p], 
                    forks[t][(p + 1) % philosophersPerTable], 
                    tables[t]
                );
                tables[t].addPhilosopher(philosopher);
                philosopher.start();
            }
        }

        // Monitor the sixth table for deadlock
        while (true) {
            if (tables[5].isDeadlocked()) {
                System.out.println("Deadlock detected at the sixth table.");
                break;
            }
        }
    }
}
