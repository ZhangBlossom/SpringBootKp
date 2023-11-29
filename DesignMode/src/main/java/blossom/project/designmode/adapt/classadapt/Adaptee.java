package blossom.project.designmode.adapt.classadapt;

// 被适配者
class Adaptee {
    public void specificRequest() {
        System.out.println("Specific request");
    }
}

// 目标接口
interface Target {
    void request();
}

// 类适配器
class ClassAdapter extends Adaptee implements Target {
    @Override
    public void request() {
        specificRequest();
    }
}