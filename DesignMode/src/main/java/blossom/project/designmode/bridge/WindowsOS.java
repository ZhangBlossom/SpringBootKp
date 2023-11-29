package blossom.project.designmode.bridge;

// Windows 操作系统
public class WindowsOS extends OperatingSystem {
    public WindowsOS(Database database) {
        super(database);
    }

    @Override
    public void start() {
        System.out.println("Starting Windows OS...");
    }

    @Override
    public void stop() {
        System.out.println("Stopping Windows OS...");
    }
}

// Linux 操作系统
