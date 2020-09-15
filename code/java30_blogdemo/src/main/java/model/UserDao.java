package model;

import javax.swing.text.html.HTMLDocument;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

// DAO 数据访问层
// UserDao 专门负责访问 User 表
public class UserDao {
    // 当前主要提供两个方法即可.
    // 1. 新增用户(实现注册)
    public void add(User user) {
        // 该方法就是要把 user 对象插入到数据库中.
        // 1. 获取到数据库连接
        Connection connection = DBUtil.getConnection();
        // 2. 拼装 SQL 语句
        String sql = "insert into user values(null, ?, ?)";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1, user.getName());
            statement.setString(2, user.getPassword());
            // 3. 执行 SQL
            //    ret 表示这次操作影响到的行数. 按理说插入一条记录成功, 返回值就是 1
            int ret = statement.executeUpdate();
            if (ret != 1) {
                throw new SQLException("插入用户信息失败");
            }
            System.out.println("插入用户信息成功!");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // 4. 关闭数据库连接
            DBUtil.close(connection, statement, null);
        }
    }

    // 2. 根据用户名查找用户信息(登陆)
    public User selectByName(String name) {
        // 根据用户名, 在数据库中查找到对应的记录, 并且包装成一个 User 对象
        // 1. 和数据库建立连接
        Connection connection = DBUtil.getConnection();
        // 2. 拼装 SQL 语句
        String sql = "select * from user where name = ?";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1, name);
            // 3. 执行 SQL
            resultSet = statement.executeQuery();
            // 4. 遍历结果集合
            // 预期查找到的用户名只有一个结果(一般是要求用户名不重复的).
            // 虽然数据库建表的时候没设定 unique 约束, 但是不打紧, 可以在业务层实现.
            if (resultSet.next()) {
                User user = new User();
                user.setUserId(resultSet.getInt("userId"));
                user.setName(resultSet.getString("name"));
                user.setPassword(resultSet.getString("password"));
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(connection, statement, resultSet);
        }
        return null;
    }

    public User selectById(int userId) {
        // 1. 获取数据库连接
        Connection connection = DBUtil.getConnection();
        // 2. 拼装 SQL
        String sql = "select * from user where userId = ?";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, userId);
            // 3. 执行 SQL
            resultSet = statement.executeQuery();
            // 4. 遍历结果集合
            if (resultSet.next()) {
                User user = new User();
                user.setUserId(resultSet.getInt("userId"));
                user.setName(resultSet.getString("name"));
                user.setPassword(resultSet.getString("password"));
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // 5. 关闭连接.
            DBUtil.close(connection, statement, resultSet);
        }
        return null;
    }

    // 直接加一个 main 方法. 只要直接运行 UserDao 这个类就会调用 main 方法
    // 进而就可以在 main 方法中完成单元测试.
    // main 不一定非得在 UserDao 里. 也可以创建额外的类, 用其他类来调用 UserDao.
    // 具体形式不重要. 只要能完成单元测试, 达到预期的目标即可.
    public static void main(String[] args) {
        UserDao userDao = new UserDao();
        // 1. 测试 add 方法
//        User user = new User();
//        user.setName("汤老湿3");
//        user.setPassword("2224");
//        userDao.add(user);

        // 2. 测试 selectByName
//        User user = userDao.selectByName("汤老湿");
//        System.out.println(user);
    }

}
