// DiningPhilosophers.java
public class DiningPhilosophers {
    
    public static boolean deadLockAtLastTable = false;
    public static long startTime = System.currentTimeMillis();
    public static void main(String[] args) {

        final int numTables = 6;
        final int philosophersPerTable = 5;
        FillTable fillTable = new FillTable(numTables, philosophersPerTable);
        fillTable.fillTable();
    }
}
