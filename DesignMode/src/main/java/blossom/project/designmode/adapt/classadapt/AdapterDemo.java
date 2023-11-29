package blossom.project.designmode.adapt.classadapt;

public class AdapterDemo {
    public static void main(String[] args) {
        Target target = new ClassAdapter();
        target.request(); // 输出: Specific request
    }
}