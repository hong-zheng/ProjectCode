package java30_0701;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.Writer;

public class SessionDemo extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 收到用户请求时, 创建一个 session 对象
        // 参数表示是一个 bool 值. true 表示要新建 session 对象.
        // 参数是 false 表示不新建对象, 而是在现有的 sessions hash 表中查找
        // 当前的 session 是否存在.
        HttpSession httpSession = req.getSession(true);
        // 此处就可以在 session 中存储任何你需要的自定制的信息.
        // 此处咱们再搞一个, 当前用户的访问次数. 也存到 session 中.
        // 如果是新用户, count 从 1 开始.
        // 如果是老用户, count 就应该从之前 session 中保存的值开始.
        Integer count = 0;
        String userName = null;
        if (httpSession.isNew()) {
            count = 1;
            // session 对象内部也是按照键值对的方式来组织用户信息的.
            httpSession.setAttribute("userName", "tz");
        } else {
            count = (Integer)httpSession.getAttribute("visitCount");
            count = count + 1;
            userName = (String)httpSession.getAttribute("userName");
        }
        httpSession.setAttribute("visitCount", count);

        // 返回页面内容
        // 此处在页面中打印 visitCount 的值
        Writer writer = resp.getWriter();
        writer.write("<html>");
        writer.write("userName: " + userName);
        writer.write("visitCount: " + count);
        writer.write("</html>");
    }
}
