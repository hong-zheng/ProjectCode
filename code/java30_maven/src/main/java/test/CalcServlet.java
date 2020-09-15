package test;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CalcServlet extends HttpServlet {
    // 此处我们约定, 用于通过 url 中的参数来描述操作数
    // url 形如: http://127.0.0.1:8080/FirstBlood/calc?a=10&b=20
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // resp 的 headers 也是被组织成了一个 hash 表.
        // 其中这个 ContentType 这个字段太重要, 太常用了. 单独有一个专门的 API 给它用
        resp.setContentType("text/html; charset=utf-8");
        // Tomcat 在解析 Request 对象的时候, 同样也是把 参数 部分, 解析成键值对结构, 放到 Map 中.
        // 1. 先从 req 对象中获取到 a 和 b 的两个值的内容
        String aStr = req.getParameter("a");
        String bStr = req.getParameter("b");
        if (aStr == null || aStr.equals("") || bStr == null || bStr.equals("")) {
            // 参数不合法. 返回一个提示信息
            resp.getWriter().write("<html>非法的参数</html>");
            return;
        }
        // 2. 针对参数进行计算
        int a = Integer.parseInt(aStr);
        int b = Integer.parseInt(bStr);
        int result = a + b;
        String body = String.format("<html>result = %d</html>", result);
        resp.getWriter().write(body);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.service(req, resp);
    }
}
