package blossom.project.designmode.observe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// 观察者接口
interface Observer {
    void update(Map<String, String> config);
}

// 配置中心类（主题）
class ConfigCenter {
    private List<Observer> observers = new ArrayList<>();
    private Map<String, String> config = new HashMap<>();

    // 注册观察者
    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    // 移除观察者
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    // 通知所有观察者配置已更新
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(new HashMap<>(config));
        }
    }

    // 设置配置项并通知观察者
    public void setConfig(String key, String value) {
        config.put(key, value);
        notifyObservers();
    }
}

// 具体观察者实现
class Service implements Observer {
    private String name;

    public Service(String name) {
        this.name = name;
    }

    @Override
    public void update(Map<String, String> config) {
        System.out.println("Service " + name + " received config update: " + config);
    }
}