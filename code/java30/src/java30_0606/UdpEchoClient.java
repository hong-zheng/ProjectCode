package java30_0606;

import java.io.IOException;
import java.net.*;
import java.util.Scanner;

public class UdpEchoClient {
    DatagramSocket socket = null;
    // 客户端必须要知道服务器的 ip 和端口号
    String serverIp;
    int serverPort;

    public UdpEchoClient(String ip, int port) throws SocketException {
        // 后面真的发送数据报的时候, 就会用到下面的 ip 和 端口了.
        this.serverIp = ip;
        this.serverPort = port;
        // 此时针对客户端给服务器发数据的场景, serverPort 就是目的端口, 源端口需要由操作系统自动分配一个空闲的.
        socket = new DatagramSocket();
    }

    public void start() throws IOException {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            // 1. 由用户在命令行输入一个字符串.
            System.out.print("> ");
            String request = scanner.nextLine();
            if (request.equals("exit")) {
                System.out.println("goodbye");
                break;
            }
            // 2. 把这个字符串构造成一个请求, 发送给服务器.
            //    客户端 send 的这个请求对象, 内部的数据就和服务器接受到的请求对象内部数据是一致的.
            //    这个数据报中的内容就是通过网络通信的方式传输过去的. (各层协议一直封装分用)
            DatagramPacket requestPacket = new DatagramPacket(request.getBytes(),
                    request.getBytes().length, InetAddress.getByName(serverIp), serverPort);
            socket.send(requestPacket);
            // 3. 读取服务器返回的数据
            DatagramPacket responsePacket = new DatagramPacket(new byte[4096], 4096);
            socket.receive(responsePacket);
            String response = new String(responsePacket.getData(), 0, responsePacket.getLength()).trim();
            // 4. 把返回的数据打印在显示器上
            String log = String.format("req: %s; resp: %s", request, response);
            System.out.println(log);
        }
    }

    public static void main(String[] args) throws IOException {
        // 这里的 ip 和端口都是服务器的 ip 和端口
        String ip = "127.0.0.1";
        // String ip = "47.98.116.42";
        int port = 9090;
        UdpEchoClient client = new UdpEchoClient(ip, port);
        client.start();
    }
}
