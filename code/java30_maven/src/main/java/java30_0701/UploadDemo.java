package java30_0701;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;

@MultipartConfig
public class UploadDemo extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Part 对象就对应着图片内容
        Part image = req.getPart("myfile");
        // 读取到文件内容后, 把图片写入磁盘中.
        File path = new File("D:\\program2\\apache-tomcat-8.5.50\\images\\" + image.getSubmittedFileName());
        image.write(path.toString());

        resp.setContentType("text/html; charset=utf-8");
        resp.getWriter().write("上传成功");
    }
}
