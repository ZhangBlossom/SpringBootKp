package blossom.project.designmode.bridge;

public class OracleDatabase implements Database {
    @Override
    public void connect() {
        System.out.println("Connecting to Oracle database...");
    }

    @Override
    public void executeQuery(String query) {
        System.out.println("Executing Oracle Query: " + query);
    }
}