package blossom.project.designmode.observe;

import java.util.Random;

class ConfigChangeSimulator implements Runnable {
    private ConfigCenter configCenter;
    private Random random = new Random();

    public ConfigChangeSimulator(ConfigCenter configCenter) {
        this.configCenter = configCenter;
    }

    @Override
    public void run() {
        try {
            while (true) {
                // 模拟配置变更
                String key = "key" + random.nextInt(10);
                String value = "value" + random.nextInt(100);
                configCenter.setConfig(key, value);

                // 休眠一段时间以模拟配置更新间隔
                Thread.sleep(3000);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

public class ObserverPatternExample {
    public static void main(String[] args) {
        ConfigCenter configCenter = new ConfigCenter();

        Service serviceA = new Service("A");
        Service serviceB = new Service("B");

        configCenter.registerObserver(serviceA);
        configCenter.registerObserver(serviceB);

        // 创建并启动配置变更模拟器
        ConfigChangeSimulator simulator = new ConfigChangeSimulator(configCenter);
        new Thread(simulator).start();
    }
}
