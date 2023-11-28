package blossom.project.designmode.proxy.dbroute;

/**
 * @author: ZhangBlossom
 * @date: 2023/11/28 12:32
 * @contact: QQ:4602197553
 * @contact: WX:qczjhczs0114
 * @blog: https://blog.csdn.net/Zhangsama1
 * @github: https://github.com/ZhangBlossom
 */
public class UserDao {
    public int insert(User user){
        System.out.println("...success...!");
        return 1;
    }
}
