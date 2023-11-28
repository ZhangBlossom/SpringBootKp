package blossom.project.designmode.dbroute.db;

/**
 * @author: ZhangBlossom
 * @date: 2023/11/28 12:32
 * @contact: QQ:4602197553
 * @contact: WX:qczjhczs0114
 * @blog: https://blog.csdn.net/Zhangsama1
 * @github: https://github.com/ZhangBlossom
 */
public class DynamicDataSource {

    public final static String DEFAULE_DATA_SOURCE = "DB_2022";

    private final static ThreadLocal<String> local = new ThreadLocal<String>();

    private DynamicDataSource(){}


    public static String get(){return  local.get();}

    public static void reset(){
         local.set(DEFAULE_DATA_SOURCE);
    }

    public static void set(String source){local.set(source);}

    public static void set(int year){local.set("DB_" + year);}

}
