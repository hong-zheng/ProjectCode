package java30_0614;

import javax.management.relation.RelationNotFoundException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HttpServerV3 {
    static class User {
        public String name;
        public String password;
        public int age;
        public String sex;
    }

    private ServerSocket serverSocket = null;
    // 使用这个 hash 表来管理所有的 会话.
    // 每个键值对对应每个用户的 会话.
    private Map<String, User> sessions = new HashMap<>();

    public HttpServerV3(int port) throws IOException {
        this.serverSocket = new ServerSocket(port);
    }

    public void start() throws IOException {
        System.out.println("服务器启动");
        ExecutorService executorService = Executors.newCachedThreadPool();
        while (true) {
            Socket clientSocket = serverSocket.accept();
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    process(clientSocket);
                }
            });
        }
    }

    public void process(Socket clientSocket) {
        try  {
            // 1. 构造请求对象
            Request request = Request.build(clientSocket.getInputStream());
            System.out.println("request: " + request);
            // 2. 准备一个响应对象
            Response response = Response.build(clientSocket.getOutputStream());
            // 3. 根据请求计算响应
            if ("GET".equalsIgnoreCase(request.getMethod())) {
                doGet(request, response);
            } else if ("POST".equalsIgnoreCase(request.getMethod())) {
                doPost(request, response);
            } else {
                response.setStatus(405);
                response.setMessage("Method Not Allowed");
            }
            // 4. 把响应结果写回到客户端
            response.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void doGet(Request request, Response response) throws IOException {
        if (request.getUrl().startsWith("/test")) {
            response.setStatus(200);
            response.setMessage("OK");
            response.setHeader("Content-Type", "text/html; charset=utf-8");
            response.writeBody("<html>");
            response.writeBody("200 OK");
            response.writeBody("</html>");
        } else if (request.getUrl().startsWith("/index.html")) {
            // 如果 path 是指向 index.html 这样的资源,
            // 就从磁盘上访问这个文件, 把文件内容读取出来, 并且写回到客户端这里.
            response.setStatus(200);
            response.setMessage("OK");
            response.setHeader("Content-Type", "text/html; charset=utf-8");
            // 把文件按行读取出来, 依次写入到 Response 的 body 中
            // 要想读文件, 需要先获取到该文件对应的 InputStream 对象
            InputStream inputStream = HttpServerV3.class.getClassLoader().getResourceAsStream("index.html");
            // 接下来的操作好办了, 还是把 inputStream 包装成 BufferReader, 然后按行读取即可.
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                response.writeBody(line + "\n");
            }
            bufferedReader.close();
        } else if (request.getUrl().startsWith("/check")) {
            // 通过这个页面来检查用户的登陆状态.
            // 此处直接检查 请求 中的 Cookie 中所包含的用户身份信息.
            String sessionId = request.getCookie("sessionId");
            String result = "";
            if (sessionId == null) {
                result = "登陆失败!";
            } else {
                User user = sessions.get(sessionId);
                if (user == null) {
                    result = "登陆失败!";
                } else {
                    result = String.format("登陆成功! name: %s, age: %d, sex: %s\n",
                            user.name, user.age, user.sex);
                }
            }
            response.setStatus(200);
            response.setMessage("OK");
            response.setHeader("Content-Type", "text/html; charset=utf-8");
            response.writeBody("<html>");
            response.writeBody(result);
            response.writeBody("</html>");
        }
    }

    private void doPost(Request request, Response response) {
        if (request.getUrl().startsWith("/login")) {
            // 处理登陆情况.
            // 此处就需要检查用户名和密码.
            // 请求提交的 用户名和密码, 是在 HTTP 请求的 body 中.
            // 而 body 中的数据, 是被 Request 解析了一下放到 parameters 这个 HashMap 中了.
            // 通过 parameters hash 表就能得到用户发送的用户名和密码是啥.
            String userName = request.getParameter("username");
            String password = request.getParameter("password");
            System.out.println("userName: " + userName);
            System.out.println("password: " + password);

            String result = "登陆失败";
            if ("aaa".equals(userName) && "123".equals(password)) {
                String sessionId = generateSessionId();
                result = "登陆成功";
                response.setHeader("Set-Cookie", "sessionId=" + sessionId);

                // 如果登陆成功, 就往 sessions 中插入一个键值对. 用来表示这个用户的 session
                User user = new User();
                user.name = userName;
                user.password = "123";
                user.age = 20;
                user.sex = "男";
                // hash 表的 key 希望是一个 "唯一" 的字符串.
                // 两个用户的会话的 session ID 不能重复~~
                sessions.put(sessionId, user);
                System.out.println("新建session: " + sessionId);
            }
            // 此处在处理登陆的逻辑中, 直接返回一个 303 的响应, 重定向到的 check 页面.
            response.setStatus(303);
            response.setMessage("See Other");
            response.setHeader("Location", "/check");
        }
    }

    // 通过这个方法来生成一个唯一的 sessionId, 保证两次调用生成的 id 是不重复的.
    // 核心思路 时间戳 + 随机数
    private static String generateSessionId() {
        long time = System.currentTimeMillis();
        Random random = new Random();
        // 生成 0->100000 之间的随机整数
        int r = random.nextInt(100000);
        return "" + time + r;
    }

    public static void main(String[] args) throws IOException {
        HttpServerV3 server = new HttpServerV3(9090);
        server.start();
    }
}
