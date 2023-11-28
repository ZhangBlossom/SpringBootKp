package blossom.project.designmode.prototype;

import java.io.*;

class RefType implements Serializable {
    private int data;

    public RefType(int data) {
        this.data = data;
    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }
}

class DeepCloneExample implements Serializable {
    private RefType refType;

    public DeepCloneExample(RefType refType) {
        this.refType = refType;
    }

    // 实现深克隆
    public DeepCloneExample deepClone() {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(this);

            ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bis);
            return (DeepCloneExample) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public RefType getRefType() {
        return refType;
    }

    public static void main(String[] args) {
        RefType refType = new RefType(100);
        DeepCloneExample original = new DeepCloneExample(refType);
        DeepCloneExample cloned = original.deepClone();

        System.out.println("Original object refType data: " + original.getRefType().getData()); // 输出: 100
        System.out.println("Cloned object refType data: " + cloned.getRefType().getData());     // 输出: 100

        // 修改原始对象的引用类型字段
        refType.setData(200);

        // 检查克隆对象的refType字段是否受到影响
        System.out.println("Original object refType data (after modification): " + original.getRefType().getData()); // 输出: 200
        System.out.println("Cloned object refType data (after modification): " + cloned.getRefType().getData());     // 输出: 100
    }
}
