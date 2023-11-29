package blossom.project.designmode.adapt.interfaceadapt;

public class AdapterDemo {
    public static void main(String[] args) {
        TargetInterface adapter = new ConcreteAdapter();
        adapter.methodA(); // 输出: Method A implementation
        adapter.methodB(); // 无输出
        adapter.methodC(); // 无输出
    }
}