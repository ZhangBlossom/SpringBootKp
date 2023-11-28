package blossom.project.designmode.proxy.test;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import javax.tools.StandardJavaFileManager;
import javax.tools.JavaFileObject;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;


/**
 * 在Java中，如果你想在运行代码时将文件内容（通常是Java源代码）
 * 编译成.class文件，可以使用Java的内置编译器API，
 * 这个API从Java 6开始被引入。具体来说，你可以使用javax.tools
 * .JavaCompiler类和相关的API来动态编译Java源代码。
 * java编译器代码
 * 作用是在java运行的时候可以将我们的java文件
 * 动态的加载到我们的内存中进行编译 得到class文件
 * 从而使得我们在运行的时候可以动态加载一些我们的类
 *
 * 在这个例子中，JavaCompiler用于编译指定路径的Java源文件。
 * StandardJavaFileManager用于管理文件，并将其传递给编译任务。
 * 这个过程会生成.class文件，保存在源代码文件相同的目录下。
 */

public class DynamicCompiler {
    public static void main(String[] args) {
        // 获取系统Java编译器
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

        // 获取标准文件管理器实例
        try (StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null)) {
            // 准备编译源代码文件
            File file = new File("D://desktop//$Proxy.java"); // 替换为你的Java源文件路径
            Iterable<? extends JavaFileObject> compilationUnits = fileManager.getJavaFileObjects(file);

            // 编译源代码
            JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager, null, null, null, compilationUnits);
            boolean success = task.call();

            if (success) {
                System.out.println("Compilation successful.");
            } else {
                System.out.println("Compilation failed.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
