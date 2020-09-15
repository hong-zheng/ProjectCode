package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

// 这个类负责操作 Article 表. 都是 JDBC 操作, 和 UserDao 非常相似
public class ArticleDao {
    public void add(Article article) {
        // 1. 获取连接
        Connection connection = DBUtil.getConnection();
        // 2. 拼装 SQL
        String sql = "insert into article values(null, ?, ?, ?)";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1, article.getTitle());
            statement.setString(2, article.getContent());
            statement.setInt(3, article.getUserId());
            // 3. 执行 SQL
            int ret = statement.executeUpdate();
            if (ret != 1) {
                throw new SQLException("插入文章失败");
            }
            System.out.println("插入文章成功");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // 4. 关闭连接
            DBUtil.close(connection, statement, null);
        }
    }

    // 根据 id 找到对应的 Article 对象
    public Article selectById(int id) {
        // 1. 获取连接
        Connection connection = DBUtil.getConnection();
        // 2. 拼装 SQL
        // 此处有个 bug, select 和 from 之间没写 *
        // String sql = "select from article where id = ?";
        String sql = "select * from article where id = ?";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            // 3. 执行 SQL
            resultSet = statement.executeQuery();
            // 4. 遍历结果集(id 是主键, 一定是唯一)
            if (resultSet.next()) {
                Article article = new Article();
                article.setId(resultSet.getInt("id"));
                article.setTitle(resultSet.getString("title"));
                article.setContent(resultSet.getString("content"));
                article.setUserId(resultSet.getInt("userId"));
                return article;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // 5. 关闭连接
            DBUtil.close(connection, statement, resultSet);
        }
        return null;
    }

    // 罗列出所有的 Article (不考虑分页)
    // 如果想实现分页, 也不难, 参数中新增一个 page 参数.
    // 构造 SQL 查找的时候使用 limit offset 关键字查找.
    public List<Article> selectAll() {
        List<Article> articles = new ArrayList<>();
        // 1. 获取到连接
        Connection connection = DBUtil.getConnection();
        // 2. 拼装 SQL 语句
        String sql = "select * from article";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(sql);
            // 3. 执行 SQL
            resultSet = statement.executeQuery();
            // 4. 遍历结果集, 此时直接查找所有结果, 记录可能有多条
            while (resultSet.next()) {
                Article article = new Article();
                article.setId(resultSet.getInt("id"));
                article.setTitle(resultSet.getString("title"));
                article.setContent(resultSet.getString("content"));
                article.setUserId(resultSet.getInt("userId"));
                articles.add(article);
            }
            return articles;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // 5. 关闭数据库连接
            DBUtil.close(connection, statement, resultSet);
        }
        return null;
    }

    // 根据 id 删除指定的数据库表中的 Article 记录
    public void delete(int id) {
        // 1. 获取连接
        Connection connection = DBUtil.getConnection();
        // 2. 拼装 sql
        String sql = "delete from article where id = ?";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            // 3. 执行 SQL
            int ret = statement.executeUpdate();
            if (ret != 1) {
                throw new SQLException("删除文章失败, id: " + id);
            }
            System.out.println("删除文章成功! id: " + id);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // 4. 关闭连接
            DBUtil.close(connection, statement, null);
        }
    }

    public static void main(String[] args) {
        ArticleDao articleDao = new ArticleDao();
        // 1. 验证新增文章
//        Article article = new Article();
//        article.setTitle("我是标题2");
//        article.setContent("我是正文我是正文我是正文我是正文我是正文我是正文我是正文我是正文我是正文我是正文我是正文我是正文我是正文我是正文我是正文我是正文我是正文我是正文我是正文我是正文我是正文我是正文我是正文我是正文我是正文我是正文我是正文我是正文我是正文我是正文我是正文我是正文我是正文我是正文我是正文我是正文我是正文我是正文我是正文我是正文我是正文我是正文我是正文我是正文我是正文我是正文我是正文我是正文我是正文我是正文我是正文我是正文我是正文我是正文我是正文我是正文我是正文我是正文我是正文我是正文我是正文我是正文我是正文我是正文我是正文我是正文我是正文我是正文我是正文我是正文我是正文我是正文我是正文我是正文我是正文我是正文我是正文我是正文我是正文我是正文我是正文我是正文我是正文我是正文我是正文我是正文我是正文我是正文我是正文我是正文我是正文我是正文我是正文我是正文我是正文我是正文我是正文我是正文我是正文我是正文我是正文");
//        article.setUserId(100);
//        articleDao.add(article);

        // 2. 验证查找文章
//        Article article = articleDao.selectById(1);
//        System.out.println(article);

        // 3. 验证查找所有文章
//        List<Article> articles = articleDao.selectAll();
//        System.out.println(articles);

        // 4. 删除指定文章
        articleDao.delete(1);
    }
}
