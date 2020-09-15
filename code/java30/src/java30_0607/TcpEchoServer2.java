package java30_0607;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

// 这个类和前面代码一样, 只是为了加深理解, 再写一遍.
// 服务器核心逻辑:
// 服务器启动之后, 会尝试循环处理多个连接.
// 处理一次连接的流程:
// 1. 先通过 accept 来接受客户端建立的连接. (接听电话)
// 2. 循环的处理多次请求
// 处理一次请求的流程:
// 1. 读取请求并解析
// 2. 根据请求计算响应
// 3. 把响应写回给客户端
public class TcpEchoServer2 {
    private ServerSocket serverSocket = null;

    public TcpEchoServer2(int port) throws IOException {
        serverSocket = new ServerSocket(port);
    }

    public void start() throws IOException {
        System.out.println("服务器启动");
        // 服务器启动之后, 会尝试循环处理多个连接.
        while (true) {
            // 1. 先通过 accept 来接受客户端建立的连接. (接听电话)
            Socket clientSocket = serverSocket.accept();
            // 2. 循环的处理多次请求
            processConnect(clientSocket);
        }
    }

    private void processConnect(Socket clientSocket) {
        // 处理一次连接过程中, 就需要处理多次请求
        System.out.printf("[%s:%d] 客户端上线\n", clientSocket.getInetAddress().toString(),
                clientSocket.getPort());
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             BufferedWriter bufferedWriter = new BufferedWriter((new OutputStreamWriter(clientSocket.getOutputStream())))) {
            while (true) {
                // 处理一次请求的流程:
                // 1. 读取请求并解析
                String request = bufferedReader.readLine();
                // 2. 根据请求计算响应
                String response = process(request);
                // 3. 把响应写回给客户端
                //    此处的 \n 为了和后面客户端实现时候的 readLine 来对应
                bufferedWriter.write(response + "\n");
                bufferedWriter.flush();

                System.out.printf("[%s:%d] req: %s; resp: %s\n", clientSocket.getInetAddress().toString(),
                        clientSocket.getPort(), request, response);
            }
        } catch (IOException e) {
            // e.printStackTrace();
            System.out.printf("[%s:%d] 客户端下线", clientSocket.getInetAddress().toString(),
                    clientSocket.getPort());
        }
    }

    private String process(String request) {
        return request;
    }

    public static void main(String[] args) throws IOException {
        // 端口号的范围 0 -> 65535
        // 端口也不能太小 (不能使用 0 -> 1023)
        TcpEchoServer2 server = new TcpEchoServer2(9090);
        server.start();
    }
}
