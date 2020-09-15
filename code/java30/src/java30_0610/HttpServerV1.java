package java30_0610;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HttpServerV1 {
    private ServerSocket serverSocket = null;

    public HttpServerV1(int port) throws IOException {
        serverSocket = new ServerSocket(port);
    }

    public void start() throws IOException {
        System.out.println("服务器启动");
        ExecutorService executorService = Executors.newCachedThreadPool();
        while (true) {
            // 1. 调用 accept (调用频率要足够高)
            Socket clientSocket = serverSocket.accept();
            // 2. 根据返回 Socket 对象进行具体的交互操作
            executorService.execute(() -> {
                process(clientSocket);
            });
        }
    }

    private void process(Socket clientSocket) {
        System.out.printf("[%s:%d] 客户端发来请求!\n", clientSocket.getInetAddress().toString(),
                clientSocket.getPort());
        // [短连接] 一个连接中只处理一次请求和响应(之前写的代码, 一次连接中要循环处理多个请求和响应)
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()))) {
            // 进行具体的解析请求 + 构造响应的操作
            // 1. 解析请求
            //  1.1 先解析首行,
            //  形如 GET http://123.207.58.25/ HTTP/1.1
            String firstLine = bufferedReader.readLine();
            String[] firstLineTokens = firstLine.split(" ");
            String method = firstLineTokens[0];
            String url = firstLineTokens[1];
            String version = firstLineTokens[2];
            //  1.2 解析请求头(header)
            Map<String, String> headers = new HashMap<>();
            String line = "";
            while ((line = bufferedReader.readLine()) != null && line.length() != 0) {
                // 如果 socket 中的内容读完了, 或者读到空行了, 就结束循环.
                // 此处读到的每一行数据都是一个 header 中的键值对, 把这个键值对插入到 hash 表中.
                // 注意! 分割符是 冒号空格 , 是由两个字符构成的.
                String[] headerToken = line.split(": ");
                headers.put(headerToken[0], headerToken[1]);
            }
            //  1.3 TODO 解析 body (这个暂时先不管, 对于 GET 没有 body)
            //  1.4 打印请求解析的结果.
            System.out.println(method + " " + url + " " + version);
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                System.out.println(entry.getKey() + ": " + entry.getValue());
            }
            System.out.println();

            // 2. 根据请求计算响应(由于当前的响应是固定, 具体计算响应的过程就没啥意义)
            //    此处我们改进一下代码, 根据用户请求中不同的 url 来做出不同的处理
            String body = "";
            if (url.equals("/200")) {
                bufferedWriter.write("HTTP/1.1 200 OK\n");
                body = "<html>200 访问正确</html>";
            } else if (url.equals("/404")) {
                bufferedWriter.write("HTTP/1.1 404 Not Found\n");
                body = "<html>404 资源没找到</html>";
            } else if (url.equals("/303")) {
                bufferedWriter.write("HTTP/1.1 303 See Other\n");
                bufferedWriter.write("Location: http://www.sogou.com\n");
            }

            // 3. 构造响应, 按理说是要根据不同的请求, 来生成不同的响应的.
            //    此处为了简单, 先无脑生成一个 helloworld 的网页.
            bufferedWriter.write("Content-Length: " + body.getBytes().length + "\n");
            bufferedWriter.write("Content-Type: text/html; charset=utf-8\n");
            bufferedWriter.write("\n");
            bufferedWriter.write(body);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 服务器主动关闭连接(短连接)
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        HttpServerV1 server = new HttpServerV1(9090);
        server.start();
    }
}
