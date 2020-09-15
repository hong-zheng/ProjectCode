package java30_0613;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// 表示服务器主干逻辑
public class HttpServerV2 {
    private ServerSocket serverSocket = null;

    public HttpServerV2(int port) throws IOException {
        serverSocket = new ServerSocket(port);
    }

    public void start() throws IOException {
        System.out.println("服务器启动");
        ExecutorService executorService = Executors.newCachedThreadPool();
        while (true) {
            Socket clientSocket = serverSocket.accept();
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        process(clientSocket);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    private void process(Socket clientSocket) throws IOException {
        // 1. 构造并解析请求(本质上就是一个反序列化操作)
        Request request = Request.build(clientSocket.getInputStream());
        System.out.println("request: " + request);
        // 2. 准备一个响应对象
        Response response = Response.build(clientSocket.getOutputStream());
        // 3. 根据请求来生成响应
        if (request.getUrl().equals("/200")) {
            response.setStatus(200);
            response.setMessage("OK");
            response.setHeader("Content-Type", "text/html; charset=utf-8");
            response.writeBody("<html>");
            response.writeBody("200 OK");
            response.writeBody("</html>");
        } else if (request.getUrl().startsWith("/calc")) {
            // URL: /calc?a=10&b=20
            int a = Integer.parseInt(request.getParameter("a"));
            int b = Integer.parseInt(request.getParameter("b"));
            int result = a + b;

            response.setStatus(200);
            response.setMessage("OK");
            response.setHeader("Content-Type", "text/html; charset=utf-8");
            response.writeBody("<html>");
            response.writeBody("result = " +  result);
            response.writeBody("</html>");
        } else if (request.getUrl().startsWith("/cookie")) {
            // 从服务器获取一个 cookie 写回到客户端
            response.setStatus(200);
            response.setMessage("OK");
            String cookie = "sessionId=aaabbbccc";
            response.setHeader("Set-Cookie", cookie);
            response.setHeader("Content-Type", "text/html; charset=utf-8");
            response.writeBody("<html>");
            response.writeBody("服务器给客户端设置 cookie 了, cookie 的内容为: " + cookie);
            response.writeBody("</html>");
        }

        // 4. 把响应写回到客户端(本质上就是一个序列化操作)
        response.flush();
        clientSocket.close(); // 此处关闭 socket 对象也就能自动关闭 InputStream 和 OutputStream
    }

    public static void main(String[] args) throws IOException {
        HttpServerV2 server = new HttpServerV2(9090);
        server.start();
    }
}
