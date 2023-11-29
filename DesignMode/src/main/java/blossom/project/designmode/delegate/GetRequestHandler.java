package blossom.project.designmode.delegate;

// 处理GET请求的处理器
class GetRequestHandler implements Handler {
    @Override
    public void handleRequest(String requestType) {
        System.out.println("Handling GET request");
    }
}

// 处理POST请求的处理器
class PostRequestHandler implements Handler {
    @Override
    public void handleRequest(String requestType) {
        System.out.println("Handling POST request");
    }
}