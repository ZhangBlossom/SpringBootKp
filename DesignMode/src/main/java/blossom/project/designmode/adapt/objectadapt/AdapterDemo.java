package blossom.project.designmode.adapt.objectadapt;

public class AdapterDemo {
    public static void main(String[] args) {
        Adaptee adaptee = new Adaptee();
        Target target = new ObjectAdapter(adaptee);
        target.request(); // 输出: Specific request
    }
}