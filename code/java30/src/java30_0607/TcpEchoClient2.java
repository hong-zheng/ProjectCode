package java30_0607;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

// 这个类和前面代码一样, 只是为了加深理解, 再写一遍.
// 1. 从命令行中读取用户输入
// 2. 把请求发送给服务器
// 3. 从服务器读取响应
// 4. 把响应结果写到显示器上.
public class TcpEchoClient2 {
    private Socket socket = null;

    public TcpEchoClient2(String ip, int port) throws IOException {
        // 和服务器建立连接(拨号的过程)
        socket = new Socket(ip, port);
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))) {
            while (true) {
                // 1. 从命令行中读取用户输入
                System.out.print("> ");
                String request = scanner.nextLine();
                // 2. 把请求发送给服务器
                //    这里的 \n 和后面服务器的 readLine 操作是对应的.
                bufferedWriter.write(request + "\n");
                bufferedWriter.flush();
                // 3. 从服务器读取响应
                String response = bufferedReader.readLine();
                // 4. 把响应结果写到显示器上.
                System.out.println(response);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        TcpEchoClient2 client = new TcpEchoClient2("127.0.0.1", 9090);
        client.start();
    }
}
