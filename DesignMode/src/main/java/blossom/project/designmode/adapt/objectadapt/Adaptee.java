package blossom.project.designmode.adapt.objectadapt;

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

// 对象适配器
class ObjectAdapter implements Target {
    private Adaptee adaptee;

    public ObjectAdapter(Adaptee adaptee) {
        this.adaptee = adaptee;
    }

    @Override
    public void request() {
        adaptee.specificRequest();
    }
}