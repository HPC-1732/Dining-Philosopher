public class FillTable {
    public static Table[] tables;
    public static Fork[][] forks;
    private int numTables;
    public static int philosophersPerTable;

    public FillTable(int numTables, int philosophersPerTable){

        this.numTables = numTables;
        this.philosophersPerTable = philosophersPerTable;
        tables = new Table[numTables];
        forks = new Fork[numTables][philosophersPerTable];

        // Initialize forks and tables
        for (int t = 0; t < numTables; t++) {
            for (int f = 0; f < philosophersPerTable; f++) {
                forks[t][f] = new Fork();
            }
            tables[t] = new Table(forks[t]);
        }
    }

    // Create philosophers for the first 5 tables
    public void fillTable() {
        for (int t = 0; t < numTables - 1; t++) {
            for (int p = 0; p < philosophersPerTable; p++) {
                System.err.println(p + t * philosophersPerTable);
                Philosopher philosopher = new Philosopher(
                    p + t * philosophersPerTable, 
                    t,
                    forks[t][p], 
                    forks[t][(p + 1) % philosophersPerTable], 
                    tables[t]
                );
                // tables[t].addPhilosopher(philosopher);
                appendPhilosopher(t, philosopher);
                philosopher.start();
            }
        }
    }

    static void appendPhilosopher(int tableNumber, Philosopher philosopher){
        tables[tableNumber].addPhilosopher(philosopher);
    }
}
