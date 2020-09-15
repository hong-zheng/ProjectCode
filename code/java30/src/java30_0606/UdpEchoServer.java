package java30_0606;

import javax.swing.table.DefaultTableCellRenderer;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class UdpEchoServer {
    // 创建一个服务器程序, 首先需要一个 Socket 对象
    private DatagramSocket socket = null;

    // 服务器程序启动的时候要和哪个端口号关联起来.
    public UdpEchoServer(int port) throws SocketException {
        socket = new DatagramSocket(port);
    }

    // 实现服务器的启动逻辑
    public void start() throws IOException {
        System.out.println("服务器启动!");
        // 一般的服务器都是需要 7*24 小时持续工作的. 这个程序正常情况下是不会自己结束的.
        while (true) {
            // 在这个一直执行的主循环中, 主要要执行以下逻辑:
            // 1. 读取请求并解析
            //    此处的读取过程就是把数据放到 DatagramPacket 对象中
            //    此处的解析过程很简单, 把这个 byte[] 中的数据转成字符串.
            DatagramPacket requestPacket = new DatagramPacket(new byte[4096], 4096);
            socket.receive(requestPacket);
            String request = new String(requestPacket.getData(), 0, requestPacket.getLength()).trim();
            // 2. 根据请求计算响应
            String response = process(request);
            // 3. 把响应结果写回给客户端
            //    此处也需要构造一个 DatagramPacket 对象
            //    构造的时候需要把 response 中持有的数据内容对应的缓冲区, 给设置到 DatagramPacket 中
            //    除了要构造内容之外, 还需要传入一个 "客户端" 对应的地址和端口号.
            DatagramPacket responsePacket = new DatagramPacket(
                    response.getBytes(), response.getBytes().length, requestPacket.getSocketAddress()
            );
            socket.send(responsePacket);
            // 加入一个日志, 更方便用户来理解服务器的运行过程.
//            String log = String.format("[%s:%d] req: %s; resp: %s",
//                    requestPacket.getAddress().toString(), requestPacket.getPort(),
//                    request, response);
            String log = "[" + requestPacket.getAddress().toString() + ":" + requestPacket.getPort()
                    + "] req: " + request + "; resp: " + response;
            System.out.println(log);
        }
    }

    public String process(String request) {
        // 当前做的是一个回显服务器. 客户端发啥, 服务器就返回啥.
        return request;
    }

    public static void main(String[] args) throws IOException {
        UdpEchoServer server = new UdpEchoServer(9090);
        server.start();
    }
}
