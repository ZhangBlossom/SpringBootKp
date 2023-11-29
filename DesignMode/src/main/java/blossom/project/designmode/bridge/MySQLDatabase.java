package blossom.project.designmode.bridge;

// MySQL 数据库实现
public class MySQLDatabase implements Database {
    @Override
    public void connect() {
        System.out.println("Connecting to MySQL database...");
    }

    @Override
    public void executeQuery(String query) {
        System.out.println("Executing MySQL Query: " + query);
    }
}

// Oracle 数据库实现
