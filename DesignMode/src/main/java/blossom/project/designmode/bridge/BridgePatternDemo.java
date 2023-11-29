package blossom.project.designmode.bridge;

public class BridgePatternDemo {
    public static void main(String[] args) {
        // 创建一个 MySQL 数据库实例
        Database mysql = new MySQLDatabase();
        // 创建一个 Windows 操作系统实例，使用 MySQL 数据库
        OperatingSystem windows = new WindowsOS(mysql);
        windows.start();
        windows.executeDatabaseQuery("SELECT * FROM users");
        windows.stop();

        // 创建一个 Oracle 数据库实例
        Database oracle = new OracleDatabase();
        // 创建一个 Linux 操作系统实例，使用 Oracle 数据库
        OperatingSystem linux = new LinuxOS(oracle);
        linux.start();
        linux.executeDatabaseQuery("SELECT * FROM employees");
        linux.stop();
    }
}