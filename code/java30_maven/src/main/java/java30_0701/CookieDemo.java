package java30_0701;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;

public class CookieDemo extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        System.out.println("firstName: " + firstName);
        System.out.println("lastName: " + lastName);

        // 让代码返回一个响应数据
        // 响应数据中, 带上一个 Cookie 信息
        resp.setContentType("text/html; charset=utf-8");
        resp.setStatus(200);

        // 在此处把刚才的 用户提交的数据, 写到 Cookie 中返回给浏览器.
        // [注意事项!!!] Cookie 是在响应报文的 header 部分. 一旦需要修改 header
        // 必须保证修改操作出现在 resp.getWriter 之前. 否则对 header 的修改可能不生效

        // 1. 创建 cookie 对象(本质上也是键值对)
        Cookie firstNameCookie = new Cookie("firstName", firstName);
        Cookie lastNameCookie = new Cookie("lastName", lastName);
        // 2. 把 Cookie 对象加入到响应对象中. 此时响应报文中就会带有
        //    Set-Cookie 字段, 内容就包含着刚才的 键值对信息
        resp.addCookie(firstNameCookie);
        resp.addCookie(lastNameCookie);

        Writer writer = resp.getWriter();
        writer.write("<html>");
        writer.write("数据提交成功!");
        writer.write("</html>");
    }
}
