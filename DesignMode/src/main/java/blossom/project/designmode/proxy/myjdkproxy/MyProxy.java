package blossom.project.designmode.proxy.myjdkproxy;

import blossom.project.designmode.proxy.client.MyService;

import javax.tools.JavaCompiler;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: ZhangBlossom
 * @date: 2023/11/28 12:32
 * @contact: QQ:4602197553
 * @contact: WX:qczjhczs0114
 * @blog: https://blog.csdn.net/Zhangsama1
 * @github: https://github.com/ZhangBlossom
 * MyProxy类
 */
public class MyProxy {
    public static final String LINE = "\r\n";

    public static Object newProxyInstance(MyClassLoader classLoader, Class<?>[] interfaces, MyInvocationHandler h) {
        try {
            //1、动态生成源代码.java文件
            String src = generateJavaFile(interfaces);

            //2：将.java文件输出到磁盘 最好是输出到resouce目录下
            String filePath = MyProxy.class.getResource("").getPath();
            //System.out.println(filePath);
            //File resourceDir = new File("src/main/resources/$Proxy0.java"); // 或者其他你的资源目录路径
            File file = new File(filePath + "$Proxy0.java");
            FileWriter fw = new FileWriter(file);
            //写出文件内容
            fw.write(src);
            fw.flush();
            fw.close();

            //3：动态的把生成的.java文件编译成.class文件
            JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
            StandardJavaFileManager manage = compiler.getStandardFileManager(null, null, null);
            Iterable iterable = manage.getJavaFileObjects(file);
            //编译任务
            JavaCompiler.CompilationTask task = compiler.getTask(null, manage, null, null, null, iterable);
            task.call();
            //编译完成 关闭管理器
            manage.close();

            //4：将编译生成的.class文件加载到JVM中
            //这里findClass的代码要想好规则
            Class proxyClass = classLoader.findClass("$Proxy0");
            //拿到构造方法
            Constructor c = proxyClass.getConstructor(MyInvocationHandler.class);

            //5：返回我自己实现的代理对象
            return c.newInstance(h);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    private static String generateJavaFile(Class<?>[] interfaces) {
        StringBuffer sb = new StringBuffer();
        //设定包路径 package xxx；
        sb.append(MyProxy.class.getPackage() + ";" + LINE);
        //导入要被代理的接口
        sb.append("import " + interfaces[0].getName() + ";" + LINE);
        //导入反射类
        sb.append("import java.lang.reflect.*;" + LINE);
        // 实现我的接口 这里就不继承JDK的proxy类了，因为手写的话默认没有人家的proxy类
        // 也就是这里我得自己参考Proxy类模仿一个
        sb.append("public class $Proxy0 implements " + interfaces[0].getName() + "{" + LINE);
        //设定成员属性MyInvocationHandler
        sb.append("MyInvocationHandler h;" + LINE);
        //构造
        sb.append("public $Proxy0(MyInvocationHandler h) { " + LINE);
        sb.append("this.h = h;");
        sb.append("}" + LINE);
        //参考JAD文件编写所有方法 这里可以用反射Methods
        for (Method m : interfaces[0].getMethods()) {


            sb.append("public " + m.getReturnType().getName() + " " + m.getName() + "(" + ") " + "{" + LINE);
            sb.append("try{" + LINE);
            //反射拿到方法
            sb.append("Method m = " + interfaces[0].getName() + ".class.getMethod(\"" + m.getName() + "\",new " +
                    "Class[]{" + "});" + LINE);
            //TODO 方法执行 还得考虑判断是否有返回值
            sb.append("this.h.invoke" + "(this,m,new Object[]{" + "});" + LINE);
            sb.append("}catch(Error _ex) { }");
            sb.append("catch(Throwable e){" + LINE);
            sb.append("throw new UndeclaredThrowableException(e);" + LINE);
            sb.append("}");
            //TODO 如果有返回值的话还得考虑增加一个返回值的处理方法
            sb.append("}");
        }
        sb.append("}" + LINE);
        return sb.toString();
    }

    public static void main(String[] args) {
        String s = generateJavaFile(new Class[]{MyService.class});
        System.out.println(s);
    }


    private static String lowerCase(String src) {
        char[] chars = src.toCharArray();
        chars[0] += 32;
        return String.valueOf(chars);
    }
}
