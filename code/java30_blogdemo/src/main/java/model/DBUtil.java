package model;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

// 专门负责获取数据库连接操作
// 一个程序中, DBUtil 中要管理 DataSource 对象.
// DataSource 对象只有一个实例
// 就可以完成所有需要的
// 数据库连接管理操作. 是一个 "单例类"
public class DBUtil {
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/java30_blogdemo?characterEncoding=utf-8&useSSL=true"; // 指定数据库地址, 端口号, 数据库名字
    private static final String USERNAME = "root"; // 连接数据库的用户名
    private static final String PASSWORD = "2222"; // 连接数据库的密码

    private static volatile DataSource dataSource = null;

    // 获取咱们唯一的一个 DataSource 实例
    public static DataSource getDataSource() {
        if (dataSource == null) {
            synchronized (DBUtil.class) {
                if (dataSource == null) {
                    dataSource = new MysqlDataSource();
                    // 这个代码是一个典型的 "向下转型" 的代码
                    ((MysqlDataSource) dataSource).setURL(URL);
                    ((MysqlDataSource) dataSource).setUser(USERNAME);
                    ((MysqlDataSource) dataSource).setPassword(PASSWORD);
                }
            }
        }
        return dataSource;
    }

    // 通过 DataSource 对象获取数据库连接
    public static Connection getConnection() {
        // 调用 getConnection 就是在和服务器建立连接.
        try {
            return getDataSource().getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // 断开连接的操作
    public static void close(Connection connection, PreparedStatement statement, ResultSet resultSet) {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
