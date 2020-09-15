package java16_0621;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class TcpEchoServer {
    // 1. 初始化服务器
    // 2. 进入主循环
    //   1) 先去从内核中获取到一个 TCP 的连接
    //   2) 处理这个 TCP 的连接
    //     a) 读取请求并解析
    //     b) 根据请求计算响应
    //     c) 把响应写回给客户端
    private ServerSocket serverSocket = null;

    public TcpEchoServer(int port) throws IOException {
        // 这个操作和前面的 UDP 类似, 也是要绑定端口号.
        serverSocket = new ServerSocket(port);
    }

    public void start() throws IOException {
        System.out.println("服务器启动");
        while (true) {
            // 1) 先从内核中获取到一个 TCP 连接
            Socket clientSocket = serverSocket.accept();
            // 2) 处理这个连接
            processConnection(clientSocket);
        }
    }

    private void processConnection(Socket clientSocket) {
        System.out.printf("[%s:%d] 客户端上线\n", clientSocket.getInetAddress().toString(),
                clientSocket.getPort());
        // 通过 clientSocket 来和客户端交互, 先做好准备工作. 获取到 clientSocket 中的流对象
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()))) {
            // 此处咱们先实现一个 "长连接" 版本的服务器.
            // 一次连接的处理过程中, 需要处理多个请求和响应.
            // 这个循环何时结束? 当客户端断开连接时, 就结束了.
            // 当客户端断开连接的时候, 服务器再去调用 readLine 或者 write 方法都会触发异常 (IOException)
            while (true) {
                // 1. 读取请求并解析(此处的 readLine 对应客户端发送数据的格式, 必须是按行发送)
                String request = bufferedReader.readLine();
                // 2. 根据请求计算响应
                String response = process(request);
                // 3. 把响应写回到客户端(客户端要按行来读)
                bufferedWriter.write(response + "\n");
                bufferedWriter.flush();
                System.out.printf("[%s:%d] req: %s; resp: %s\n", clientSocket.getInetAddress().toString(),
                        clientSocket.getPort(), request, response);
            }
        } catch (IOException e) {
            // e.printStackTrace();
            System.out.printf("[%s:%d] 客户端下线\n", clientSocket.getInetAddress().toString(),
                    clientSocket.getPort());
        }
    }

    public String process(String request) {
        return request;
    }

    public static void main(String[] args) throws IOException {
        TcpEchoServer server = new TcpEchoServer(9090);
        server.start();
    }
}
