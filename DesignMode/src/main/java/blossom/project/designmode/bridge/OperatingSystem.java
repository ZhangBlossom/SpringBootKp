package blossom.project.designmode.bridge;

public abstract class OperatingSystem {
    // 持有数据库的引用
    protected Database database;

    public OperatingSystem(Database database) {
        this.database = database;
    }

    public abstract void start();
    public abstract void stop();

    // 使用数据库执行查询
    public void executeDatabaseQuery(String query) {
        database.connect();
        database.executeQuery(query);
    }
}