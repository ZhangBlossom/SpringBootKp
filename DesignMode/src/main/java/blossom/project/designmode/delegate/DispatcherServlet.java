package blossom.project.designmode.delegate;

import java.util.HashMap;
import java.util.Map;

class DispatcherServlet {
    private Map<String, Handler> handlerMap = new HashMap<>();

    public DispatcherServlet() {
        handlerMap.put("GET", new GetRequestHandler());
        handlerMap.put("POST", new PostRequestHandler());
    }

    public void doDispatch(String requestType) {
        Handler handler = handlerMap.get(requestType.toUpperCase());
        if (handler != null) {
            handler.handleRequest(requestType);
        } else {
            System.out.println("Unsupported request type: " + requestType);
        }
    }
}