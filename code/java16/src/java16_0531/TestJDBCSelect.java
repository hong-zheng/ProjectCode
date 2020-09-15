package java16_0531;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TestJDBCSelect {
    public static void main(String[] args) throws SQLException {
        // 1. 创建 DataSource 对象
        DataSource dataSource = new MysqlDataSource();
        ((MysqlDataSource) dataSource).setURL("jdbc:mysql://127.0.0.1:3306/java16_0531?characterEncoding=utf-8&useSSL=true");
        ((MysqlDataSource) dataSource).setUser("root");
        ((MysqlDataSource) dataSource).setPassword("2222");

        // 2. 创建 Connection 对象, 和数据库建立连接.
        Connection connection = dataSource.getConnection();

        // 3. 借助 PrepareStatement 拼装 SQL 语句.
        String sql = "select * from student";
        PreparedStatement statement = connection.prepareStatement(sql);

        // 4. 执行 SQL 语句
        ResultSet resultSet = statement.executeQuery();

        // 5. 遍历结果集. 遍历过程和使用迭代器遍历集合类有点像
        //    结果集相当于一张表. 这个表里有很多行, 每一行是一条记录(又包含很多列)
        //    next() 一方面是判定当前是否存在下一行. 另一方面如果存在下一行就获取到这一行.
        //    可以直观的把 resultSet 对象想象成是一个 "光标"
        while (resultSet.next()) {
            // resultSet 的光标指向了当前行, 就可以把当前行中的列数据都获取到
            // 当前表中的每一行包含三个列. id, name, classId, 可以根据列名来获取对应的列数据.
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            int classId = resultSet.getInt("classId");
            System.out.println("id: " + id + " name: " + name + " classId: " + classId);
        }

        // 6. 关闭释放资源.
        //    后创建的对象需要先释放.
        resultSet.close();
        statement.close();
        connection.close();
    }
}
