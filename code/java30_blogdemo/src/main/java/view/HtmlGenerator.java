package view;

import model.Article;
import model.User;

import java.util.List;

public class HtmlGenerator {
    // 通过这个方法, 拼装一个 html 用于表示出错信息
    // message 表示具体的错误信息.
    // nextUrl 表示接下来要跳转到哪里
    // 这个所谓的 "生成 html " 的方法, 本质上就是在拼接字符串.
    public static String getMessagePage(String message, String nextUrl) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<html>");
        stringBuilder.append("<head>");
        stringBuilder.append("<meta charset=\"utf-8\">");
        stringBuilder.append("<title>提示页面</title>");
        stringBuilder.append("</head>");
        stringBuilder.append("<body>");
        stringBuilder.append("<h3>");
        stringBuilder.append(message);
        stringBuilder.append("</h3>");
        stringBuilder.append(String.format("<div><a href=\"%s\"> 接下来点击这里跳转 </a></div>", nextUrl));
        stringBuilder.append("</body>");
        stringBuilder.append("</html>");
        return stringBuilder.toString();
    }

    public static String getArticleListPage(User user, List<Article> articles) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<html>");
        stringBuilder.append("<head>");
        stringBuilder.append("<meta charset=\"utf-8\">");
        stringBuilder.append("<title>" + "博客列表页" + "</title>");
        stringBuilder.append("</head>");
        stringBuilder.append("<body>");

        stringBuilder.append("<h3> 欢迎您! " + user.getName() + "</h3>");

        // 此处需要整个循环, 通过循环来把每个文章的信息都获取到, 拼装到一起
        for (Article article : articles) {
            // 拼装文章的标题
            // 拼装文章的链接(点击标题, 能跳转到文章的详细内容中)
            stringBuilder.append(String.format("<div><a href=\"article?articleId=%d\"> %s </a>" +
                            "<a href=\"article?articleId=%d&delete=1\">删除</a></div>",
                    article.getId(), article.getTitle(), article.getId()));
        }

        // 此处添加一块新的内容, 用来发布新的文章.
        stringBuilder.append("<br>");
        stringBuilder.append("<hr>"); // hr 是一个分割线
        stringBuilder.append("<br>");
        stringBuilder.append("<div> 发布文章 </div>");

        // 整体的 form 表单
        stringBuilder.append("<form method=\"post\" action=\"article\">");
        // 有一个单行输入框, 作为输入标题的地方
        stringBuilder.append("<div><input type=\"text\" style=\"width: 50%\" name=\"title\" placeholder=\"请输入标题\"></div>");
        // 有一个多行输入框, 作为输入正文的地方
        stringBuilder.append("<div><textarea name=\"content\" style=\"width: 50%; height: 500px\"></textarea></div>");
        // 还要有一个 "发布" 按钮
        stringBuilder.append("<div><input type=\"submit\" style=\"width: 100px; height: 50px\" value=\"发布文章\"></div>");
        stringBuilder.append("</form>");

        stringBuilder.append("</body>");
        stringBuilder.append("</html>");
        return stringBuilder.toString();
    }

    public static String getArticleDetailPage(User user, User author, Article article) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<html>");
        stringBuilder.append("<head>");
        stringBuilder.append("<meta charset=\"utf-8\">");
        stringBuilder.append("<title>" + "博客详情页" + "</title>");
        stringBuilder.append("</head>");
        stringBuilder.append("<body>");

        stringBuilder.append("<h3>" + "欢迎您!! " + user.getName() + " </h3>");
        stringBuilder.append("<h1>" + article.getTitle() + " </h1>");
        // 正常来说, 此处应该显示的是文章的作者名字, 而不应该是一个 userId
        // 此处显示作者的名字
        // stringBuilder.append("<h4>" + article.getUserId() + " </h4>");
        stringBuilder.append("<h4>" + author.getName() + " </h4>");
        // 为了解决 \n 不识别的问题, 方案有两个:
        // 1. 把 \n 替换成 <br>
        // 2. 使用 <pre> 标签也行.
        stringBuilder.append("<div>" + article.getContent().replaceAll("\n", "<br>") + "</div>");

        stringBuilder.append("</body>");
        stringBuilder.append("</html>");
        return stringBuilder.toString();
    }
}
