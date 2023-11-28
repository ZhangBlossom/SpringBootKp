package blossom.project.designmode.proxy.dbroute.proxy;


import blossom.project.designmode.proxy.dbroute.IUserService;
import blossom.project.designmode.proxy.dbroute.User;
import blossom.project.designmode.proxy.dbroute.db.DynamicDataSource;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
* ZhangBlossom
 */
public class UserServiceStaticProxy implements IUserService {
    private SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");

    private IUserService orderService;
    public UserServiceStaticProxy(IUserService orderService) {
        this.orderService = orderService;
    }

    public int createUser(User user) {
        Long time = user.getCreateTime();
        Integer dbRouter = Integer.valueOf(yearFormat.format(new Date(time)));
        System.out.println("静态代理类自动分配到【DB_" +  dbRouter + "】数据源处理数据" );
        DynamicDataSource.set(dbRouter);

        this.orderService.createUser(user);
        DynamicDataSource.reset();

        return 0;
    }
}
