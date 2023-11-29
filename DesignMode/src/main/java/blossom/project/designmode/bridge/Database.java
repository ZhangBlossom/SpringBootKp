package blossom.project.designmode.bridge;

public interface Database {
    void connect();
    void executeQuery(String query);
}