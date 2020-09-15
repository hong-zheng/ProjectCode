package java30_0606;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class TcpEchoServer {
    private ServerSocket serverSocket = null;

    public TcpEchoServer(int port) throws IOException {
        serverSocket = new ServerSocket(port);
    }

    public void start() throws IOException {
        System.out.println("服务器启动");
        while (true) {
            Socket clientSocket = serverSocket.accept();
            processConnect(clientSocket);
        }
    }

    private void processConnect(Socket clientSocket) {
        System.out.printf("[%s:%d] 客户端上线!\n", clientSocket.getInetAddress().toString(),
                clientSocket.getPort());
        // 为了后面读写做准备, 主要准备好对应的流对象
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()))) {
            while (true) {
                // 1. 读取请求并解析
                String request = bufferedReader.readLine();
                if (request == null) {
                    // 读到 eof 了, 此时说明客户端已经关闭.
                    System.out.printf("[%s:%d] 客户端下线![1]\n", clientSocket.getInetAddress().toString(),
                            clientSocket.getPort());
                    return;
                }
                // 2. 根据请求计算响应
                String response = process(request);
                // 3. 把响应结果写回给客户端
                bufferedWriter.write(response + "\n"); // [注意! 此处的 \n 也很重要] 此处的 \n 是为了让客户端能够正常 readLine
                bufferedWriter.flush(); // [注意! 此处的 flush 很重要]

                // 打印一个日志
                String log = String.format("[%s:%d] req: %s; resp: %s", clientSocket.getInetAddress().toString(),
                        clientSocket.getPort(), request, response);
                System.out.println(log);
            }
        } catch (IOException e) {
            // e.printStackTrace();
            System.out.printf("[%s:%d] 客户端下线![2]\n", clientSocket.getInetAddress().toString(),
                    clientSocket.getPort());
            return;
        }
    }

    private String process(String request) {
        return request;
    }

    public static void main(String[] args) throws IOException {
        TcpEchoServer server = new TcpEchoServer(9090);
        server.start();
    }
}
