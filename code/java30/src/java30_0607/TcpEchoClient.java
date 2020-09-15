package java30_0607;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class TcpEchoClient {
    private Socket socket = null;

    public TcpEchoClient(String ip, int port) throws IOException {
        // 此处的 ip 和 port 都是服务器的信息.
        // 当实例化这个 socket 对象的时候, 就是在和服务器建立连接(打电话拨号)
        // 方式1
        // socket = new Socket(ip, port);
        // 方式2
        socket = new Socket();
        socket.connect(new InetSocketAddress(ip, port));
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))) {
            while (true) {
                // 1. 从命令行读取用户输入
                System.out.print("> ");
                String request = scanner.nextLine();
                if (request == null) {
                    System.out.println("客户端发送数据结束");
                    break;
                }
                // 2. 把用户输入的内容发送给服务器
                //    当前 TCP echo server 在尝试按行读取数据.
                //    必须要保证客户端发送的数据是带 \n , 服务器才能 readLine
                bufferedWriter.write(request + "\n");
                bufferedWriter.flush();
                // 3. 读取服务器的响应, 此处的 readLine 和服务器的 write 是对应的.
                String response = bufferedReader.readLine();
                if (response == null) {
                    // 服务器断开了连接
                    System.out.println("连接已经断开");
                    break;
                }
                // 4. 把响应结果显示到显示器上.
                System.out.println(response);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        String serverIp = "127.0.0.1";
        int serverPort = 9090;
        TcpEchoClient client = new TcpEchoClient(serverIp, serverPort);
        client.start();
    }
}
