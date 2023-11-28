package blossom.project.designmode.proxy.myjdkproxy;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;

/**
 * @author: ZhangBlossom
 * @date: 2023/11/28 12:33
 * @contact: QQ:4602197553
 * @contact: WX:qczjhczs0114
 * @blog: https://blog.csdn.net/Zhangsama1
 * @github: https://github.com/ZhangBlossom
 * MyClassLoader类
 */
public class MyClassLoader extends ClassLoader{

    private File classPathFile;
    public MyClassLoader(){
        //1：这里要和MyProxy中的.java文件的位置一致
        String classPath = MyClassLoader.class.getResource("").getPath();
        this.classPathFile = new File(classPath);
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {

        String className = MyClassLoader.class.getPackage().getName() + "." + name;
        System.out.println(className);
        if(classPathFile  != null){
            File classFile = new File(classPathFile,name.replaceAll("\\.","/") + ".class");
            if(classFile.exists()){
                FileInputStream fis = null;
                ByteArrayOutputStream fos = null;
                try{
                    fis = new FileInputStream(classFile);
                    fos = new ByteArrayOutputStream();
                    //设置缓冲区 设定的大一点读的快一点
                    byte [] buffer = new byte[1024 * 16];
                    int len;
                    while ((len = fis.read(buffer)) != -1){
                        fos.write(buffer,0,len);
                    }
                    return defineClass(className,fos.toByteArray(),0,fos.size());
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
