package api;

import model.User;
import model.UserDao;
import view.HtmlGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegisterServlet extends HttpServlet {
    // 根据刚才 接口设计 环节的约定. 处理登陆逻辑是通过 POST 请求实现的.
    // 下面使用的方法就应该是 doPost 方法.
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html; charset=utf-8");
        // 1. 获取到浏览器提交的用户名和密码(如果获取不到咋办, 错误处理逻辑).
        //    用户名密码是在 HTTP 请求的 body 中过来的. getParameter 获取
        String userName = req.getParameter("userName");
        String password = req.getParameter("password");
        if (userName == null || "".equals(userName)
            || password == null || "".equals(password)) {
            // 浏览器提交的用户名密码有误.
            // 返回一个错误的页面即可(会反复用到)
            // 咱们创建一个专门的类来帮我们生成一个错误页面(字符串拼接)
            String html = HtmlGenerator.getMessagePage("用户名或密码不能为空",
                    "register.html");
            resp.getWriter().write(html);
            return;
        }
        // 2. 根据用户名, 在数据库中查一下, 看看该用户名是否存在.
        //    如果已经存在, 提示用户注册失败
        UserDao userDao = new UserDao();
        User existUser = userDao.selectByName(userName);
        if (existUser != null) {
            // 说明该用户名已经存在, 提醒用户注册失败, 要换个名字
            String html = HtmlGenerator.getMessagePage("用户名已经重复! 请更换用户名!",
                    "register.html");
            resp.getWriter().write(html);
            return;
        }
        // 3. 根据提交的用户名和密码, 构造成 User 对象, 并且插入到数据库中.
        User newUser = new User();
        newUser.setName(userName);
        newUser.setPassword(password);
        userDao.add(newUser);
        // 4. 返回注册成功的页面.
        String html = HtmlGenerator.getMessagePage("注册成功!",
                "login.html");
        resp.getWriter().write(html);
    }
}
