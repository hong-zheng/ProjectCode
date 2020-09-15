package java30_0701;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;

public class CookieDemo2 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 当前浏览器中已经保存了两个 Cookie 了.
        // firstName 和 lastName
        // 在代码中读取到的浏览器请求之后, 就可以获取到这两个 Cookie 内容

        // getCookies 获取到请求中的所有 Cookie
        Cookie[] cookies = req.getCookies();

        resp.setStatus(200);
        resp.setContentType("text/html; charset=utf-8");
        Writer writer = resp.getWriter();
        writer.write("<html>");
        for (Cookie cookie : cookies) {
            writer.write("<div>");
            writer.write(cookie.getName() + ": " + cookie.getValue());
            writer.write("</div>");
        }
        writer.write("</html>");
    }
}
