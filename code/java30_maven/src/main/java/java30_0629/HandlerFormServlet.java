package java30_0629;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;

public class HandlerFormServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 获取到 form 中的内容
        String userName = req.getParameter("username");
        String password = req.getParameter("password");
        Writer writer = resp.getWriter();
        writer.write("<html>");
        writer.write("userName: " + userName);
        writer.write("<br/>");
        writer.write("password: " + password);
        writer.write("</html>");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // doPost 方法的具体实现, 逻辑和 doGet 一模一样.
        // 虽然 参数 从 url 中移动到了 body 中.
        // 仍然是通过 getParameter 方法获取到预期的内容
        doGet(req, resp);
    }
}
