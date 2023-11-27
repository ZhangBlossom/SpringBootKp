/**
 * @author: ZhangBlossom
 * @date: 2023/11/27 20:06
 * @contact: QQ:4602197553
 * @contact: WX:qczjhczs0114
 * @blog: https://blog.csdn.net/Zhangsama1
 * @github: https://github.com/ZhangBlossom
 * LazySingleton类
 */
public class LazySingleton {
    private static LazySingleton INSTANCE;

    public synchronized static LazySingleton getInstance(){
        if (INSTANCE==null){
            INSTANCE = new LazySingleton();
        }
        return INSTANCE;
    }

    public static void main(String[] args) {
        new Thread(()->{
            LazySingleton.getInstance();
        }).start();

        new Thread(()->{
            LazySingleton.getInstance();
        }).start();
    }
}
