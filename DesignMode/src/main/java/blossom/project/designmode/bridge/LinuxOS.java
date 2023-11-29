package blossom.project.designmode.bridge;

public class LinuxOS extends OperatingSystem {
    public LinuxOS(Database database) {
        super(database);
    }

    @Override
    public void start() {
        System.out.println("Starting Linux OS...");
    }

    @Override
    public void stop() {
        System.out.println("Stopping Linux OS...");
    }
}