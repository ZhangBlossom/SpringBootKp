package blossom.project.designmode.dbroute;

/**
 * @author: ZhangBlossom
 * @date: 2023/11/28 12:32
 * @contact: QQ:4602197553
 * @contact: WX:qczjhczs0114
 * @blog: https://blog.csdn.net/Zhangsama1
 * @github: https://github.com/ZhangBlossom
 */
public class UserService implements IUserService {
    private UserDao userDao;

    public UserService(){
        //如果使用Spring应该是自动注入的
        //我们为了使用方便，在构造方法中将orderDao直接初始化了
        userDao = new UserDao();
    }

    public int createUser(User user) {
        System.out.println("..........");
        return userDao.insert(user);
    }
}
