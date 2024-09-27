// DiningPhilosophers.java
public class DiningPhilosophers {
    
    public static boolean deadLockAtLastTable = false;
    
    public static void main(String[] args) {

        final int numTables = 2;
        final int philosophersPerTable = 2;
        FillTable fillTable = new FillTable(numTables, philosophersPerTable);
        fillTable.fillTable();
        while(!deadLockAtLastTable){}
        System.exit(0);
    }
}
