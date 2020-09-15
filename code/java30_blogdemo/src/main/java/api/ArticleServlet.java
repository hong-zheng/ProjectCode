package api;

import model.Article;
import model.ArticleDao;
import model.User;
import model.UserDao;
import view.HtmlGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class ArticleServlet extends HttpServlet {
    // 如果你用的是 IE 浏览器, 可能出现提交的数据乱码的情况.
    // req.setCharacterEncoding("utf-8");

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html; charset=utf-8");
        // 1. 先验证用户是否登陆. 如果没登陆, 要求要先登陆才查看.
        //    验证办法就是看看该用户的 session 是否存在
        HttpSession httpSession = req.getSession(false);
        if (httpSession == null) {
            String html = HtmlGenerator.getMessagePage("您尚未登陆, 无法查看文章列表",
                    "login.html");
            resp.getWriter().write(html);
            return;
        }
        User user = (User) httpSession.getAttribute("user");
        // 2. 根据 req 中的 articleId 参数, 来区分当前是获取文章列表, 还是获取指定文章内容
        String articleId = req.getParameter("articleId");
        String delete = req.getParameter("delete");
        if (delete != null && !"".equals(delete)) {
            // 如果存在 delete 参数, 就执行删除逻辑.
            // 不存在 delete, 再进行其他操作.
            deleteArticle(articleId, user, resp);
        } else if (articleId == null || "".equals(articleId)) {
            // 3. 如果是获取所有文章列表, 调用数据库操作, 查找到文章相关列表, 并进行返回
            getAllArticle(user, resp);
        } else {
            // 4. 如果是获取指定文章内容, 调用数据库操作, 查找到指定文章的内容, 并进行返回
            getOneArticle(Integer.parseInt(articleId), user, resp);
        }
    }

    private void getAllArticle(User user, HttpServletResponse resp) throws IOException {
        // 1. 从数据库中查找文章数据
        ArticleDao articleDao = new ArticleDao();
        List<Article> articles = articleDao.selectAll();
        // 2. 把数据包装成 html
        //    getArticleListPage 方法, 构造一个 html 字符串.
        //    写回到响应中即可.
        String html = HtmlGenerator.getArticleListPage(user, articles);
        resp.getWriter().write(html);
    }

    private void getOneArticle(int articleId, User curUser, HttpServletResponse resp) throws IOException {
        // 1. 先从数据库中查找文章数据
        ArticleDao articleDao = new ArticleDao();
        Article article = articleDao.selectById(articleId);
        if (article == null) {
            // 数据库中没有查到对应的文章.
            // 此时让用户跳转回文章列表页就行了.
            String html = HtmlGenerator.getMessagePage("您想查看的文章不存在",
                    "article");
            resp.getWriter().write(html);
            return;
        }
        // 2. 根据作者的用户 id 查找对应的作者的用户名.
        UserDao userDao = new UserDao();
        User author = userDao.selectById(article.getUserId());
        // 3. 把数据包装成 html
        String html = HtmlGenerator.getArticleDetailPage(curUser, author, article);
        resp.getWriter().write(html);
    }

    // 处理发布文章的逻辑
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html; charset=utf-8");
        // 1. 判定当前用户是否登陆. 如果没登陆, 需要提示先登陆后才能发布文章.
        HttpSession httpSession = req.getSession(false);
        if (httpSession == null) {
            String html = HtmlGenerator.getMessagePage("您尚未登陆",
                    "login.html");
            resp.getWriter().write(html);
            return;
        }
        User user = (User) httpSession.getAttribute("user");
        // 2. 如果登陆成功, 通过请求, 获取到客户端提交的 标题 和 正文数据.
        String title = req.getParameter("title");
        String content = req.getParameter("content");
        if (title == null || content == null
                || "".equals(title) || "".equals(content)) {
            String html = HtmlGenerator.getMessagePage("提交的内容有误!",
                    "article");
            resp.getWriter().write(html);
            return;
        }
        // 3. 构造一个 Article 对象. 插入到数据库中.
        Article article = new Article();
        article.setUserId(user.getUserId());
        article.setTitle(title);
        article.setContent(content);
        ArticleDao articleDao = new ArticleDao();
        articleDao.add(article);
        // 4. 返回一个提示页面, 发布成功.
        String html = HtmlGenerator.getMessagePage("发布文章成功!",
                "article");
        resp.getWriter().write(html);
    }

    // 通过这个方法来删除文章~~
    private void deleteArticle(String articleId, User user, HttpServletResponse resp) throws IOException {
        // 1. 验证参数
        if (articleId == null) {
            // 参数不完整, 返回一个提示页面.
            String html = HtmlGenerator.getMessagePage("文章 id 非法.",
                    "article");
            resp.getWriter().write(html);
            return;
        }
        int id = Integer.parseInt(articleId);
        // 2. 从数据库中查找文章信息.
        ArticleDao articleDao = new ArticleDao();
        Article article = articleDao.selectById(id);
        if (article == null) {
            // 参数不完整, 返回一个提示页面.
            String html = HtmlGenerator.getMessagePage("要删除的文章不存在",
                    "article");
            resp.getWriter().write(html);
            return;
        }
        // 3. 验证用户信息(用户只能删除自己的文章. 不能删除别人的文章)
        //    user 表示当前登陆的用户. article 中存的 userId 是作者的 id
        if (article.getUserId() != user.getUserId()) {
            String html = HtmlGenerator.getMessagePage("您无法删除别人的文章",
                    "article");
            resp.getWriter().write(html);
            return;
        }
        // 4. 真正进行删除.
        articleDao.delete(id);
        // 5. 返回一个删除成功的页面.
        String html = HtmlGenerator.getMessagePage("删除成功!",
                "article");
        resp.getWriter().write(html);
    }
}
