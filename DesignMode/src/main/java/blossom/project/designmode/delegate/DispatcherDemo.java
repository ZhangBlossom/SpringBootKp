package blossom.project.designmode.delegate;

public class DispatcherDemo {
    public static void main(String[] args) {
        DispatcherServlet dispatcher = new DispatcherServlet();

        // 模拟不同类型的请求
        dispatcher.doDispatch("GET");
        dispatcher.doDispatch("POST");
        dispatcher.doDispatch("PUT"); // 将输出"Unsupported request type: PUT"
    }
}