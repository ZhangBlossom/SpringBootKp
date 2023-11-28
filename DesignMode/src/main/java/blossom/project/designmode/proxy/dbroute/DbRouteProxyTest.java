package blossom.project.designmode.proxy.dbroute;


import blossom.project.designmode.proxy.dbroute.proxy.UserServiceDynamicProxy;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author: ZhangBlossom
 * @date: 2023/11/28 12:32
 * @contact: QQ:4602197553
 * @contact: WX:qczjhczs0114
 * @blog: https://blog.csdn.net/Zhangsama1
 * @github: https://github.com/ZhangBlossom
 */
public class DbRouteProxyTest {
    public static void main(String[] args) {
        try {
            User user = new User();

//            user.setCreateTime(new Date().getTime());

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            Date date = sdf.parse("2023/03/01");
            user.setCreateTime(date.getTime());

            IUserService orderService = (IUserService)new UserServiceDynamicProxy().getInstance(new UserService());
//            IOrderService orderService = (IOrderService)new UserServiceStaticProxy(new UserService());
            orderService.createUser(user);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

}
