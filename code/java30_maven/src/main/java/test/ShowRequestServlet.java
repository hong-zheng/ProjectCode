package test;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;

public class ShowRequestServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String encoding = req.getCharacterEncoding();
        String contentType = req.getContentType();
        String contextPath = req.getContextPath();
        String userAgent = req.getHeader("User-Agent");
        String method = req.getMethod();
        String aStr = req.getParameter("a");
        String protocol = req.getProtocol();
        String queryString = req.getQueryString();
        String requestUri = req.getRequestURI();
        int contentLength = req.getContentLength();

        // 此处的显示方式有两种方式.
        // 1. 把内容构造到页面上
        resp.setContentType("text/html; charset=utf-8");
        Writer writer = resp.getWriter();
        writer.write("<html>");
        writer.write("encoding: " + encoding);
        writer.write("<br/>");
        writer.write("contentType: " + contentType);
        writer.write("<br/>");
        writer.write("contextPath: " + contextPath);
        writer.write("<br/>");
        writer.write("User-Agent: " + userAgent);
        // writer.write("<br></br>");
        writer.write("<br/>");
        writer.write("method: " + method);
        writer.write("<br/>");
        writer.write("aStr: " + aStr);
        writer.write("<br/>");
        writer.write("protocol: " + protocol);
        writer.write("<br/>");
        writer.write("queryString: " + queryString);
        writer.write("<br/>");
        writer.write("requestUri: " + requestUri);
        writer.write("<br/>");
        writer.write("ContentLength: " + contentLength);
        writer.write("<br/>");
        writer.write("</html>");
        // 2. 把内容打印到 控制台 上 (System.out.println)
    }
}
