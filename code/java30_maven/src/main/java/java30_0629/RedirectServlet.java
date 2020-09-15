package java30_0629;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RedirectServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 设定一个重定向的响应, 自动帮我们构造了一个 302 这样的响应数据
        // 浏览器收到之后就会自动进行跳转.
        resp.sendRedirect("https://www.sogou.com");
    }
}
