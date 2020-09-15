package java16_0713;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;

@MultipartConfig
public class ServletDemo10 extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 收到图片, 直接把图片保存到
        // d:/java16/images 目录中~~
        // 先把路径准备好~~
        String basePath = "d:/java16/images/";
        Part image = req.getPart("image");
        // 这个方法就能得到上传的文件名.
        String path = basePath + image.getSubmittedFileName();
        // image.getContentType(); // image/jpeg
        image.write(path);

        resp.setContentType("text/html; charset=utf-8");
        resp.getWriter().write("图片上传成功!");
    }
}
