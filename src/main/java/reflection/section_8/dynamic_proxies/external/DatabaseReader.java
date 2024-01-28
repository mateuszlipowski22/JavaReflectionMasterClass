package reflection.section_8.dynamic_proxies.external;

public interface DatabaseReader {
     int countRowsInTable(String tableName) throws InterruptedException;
     String[] readRow(String sqlQuery) throws InterruptedException ;
}
