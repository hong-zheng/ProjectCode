package java16_0712;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// 演示服务器给浏览器写回 cookie
public class ServletDemo7 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 1. 先构造 Cookie 对象, 每个 Cookie 对象就是一个键值对.
        Cookie userName = new Cookie("userName", "tz");
        Cookie age = new Cookie("age", 18 + "");
        // 2. 把 Cookie 放到响应中.
        resp.addCookie(userName);
        resp.addCookie(age);
        // 3. 创建一个响应报文
        resp.setContentType("text/html; charset=utf-8");
        resp.getWriter().write("返回 cookie 成功!");
    }
}
