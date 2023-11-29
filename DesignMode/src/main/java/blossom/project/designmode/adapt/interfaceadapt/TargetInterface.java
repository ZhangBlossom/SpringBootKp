package blossom.project.designmode.adapt.interfaceadapt;

// 目标接口，包含多个方法
interface TargetInterface {
    void methodA();
    void methodB();
    void methodC();
}

// 接口适配器/缺省适配器，为接口提供默认实现
abstract class InterfaceAdapter implements TargetInterface {
    @Override
    public void methodA() { }

    @Override
    public void methodB() { }

    @Override
    public void methodC() { }
}

// 具体实现类，只需覆盖感兴趣的方法
class ConcreteAdapter extends InterfaceAdapter {
    @Override
    public void methodA() {
        System.out.println("Method A implementation");
    }

    // 方法B和C使用默认实现（无操作）
}