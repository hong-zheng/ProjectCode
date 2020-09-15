package api;

import model.User;
import model.UserDao;
import view.HtmlGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginServlet extends HttpServlet {
    // 通过 doPost 来处理登陆的具体过程
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html; charset=utf-8");
        // 1. 读取用户提交的用户名和密码(考虑非法情况)
        String userName = req.getParameter("userName");
        String password = req.getParameter("password");
        if (userName == null || "".equals(userName)
            || password == null || "".equals(password)) {
            String html = HtmlGenerator.getMessagePage("用户名或密码不能为空",
                    "login.html");
            resp.getWriter().write(html);
            return;
        }
        // 2. 根据用户名在数据库中查找指定的用户信息, 对比密码看是否匹配
        UserDao userDao = new UserDao();
        User user = userDao.selectByName(userName);
        if (user == null || !password.equals(user.getPassword())) {
            // 用户如果不存在, 或者密码不匹配.
            String html = HtmlGenerator.getMessagePage("用户名或密码错误",
                    "login.html");
            resp.getWriter().write(html);
            return;
        }
        // 3. 如果密码也正确, 说明登陆成功了, 就创建一个新的 session,
        //    把用户信息保存到 session 中
        HttpSession httpSession = req.getSession(true);
        // 下次访问, 根据 sessionId 就能找到对应的 httpSession 对象, 进而找到
        // 对应的 user 对象
        httpSession.setAttribute("user", user);
        // 4. 返回一个 登陆成功 页面.
        //    成功之后, 点击跳转到博客列表页面.
        String html = HtmlGenerator.getMessagePage("登陆成功!",
                "article");
        resp.getWriter().write(html);
    }
}
