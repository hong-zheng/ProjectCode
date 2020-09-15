package java30_0629;

import test.CalcServlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class ShowResponseAPI extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 浏览器随便发过来个请求, 返回一个页面.
        // 这个页面每秒钟能够自动刷新一次.
        // 在 Response 的 header 中设定一个 Refresh 这样的字段就行了. 值是一个整数
        // 就表示隔几秒钟刷新一次.
        resp.setIntHeader("Refresh", 1);
        resp.setContentType("text/html; charset=utf-8");
        // 页面内容来显式当前时间.
        // 1. 可以先获取 时间戳 , 再根据时间戳转换成一个格式化时间(SimpleDateFormat)
        // 2. 还可以使用 Calendar(日历), 也能直接获取到预期的时间.
        // 格里高利 历. => 公历
        Calendar calendar = new GregorianCalendar();
        int hour = calendar.get(Calendar.HOUR);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);
        String am_pm = calendar.get(Calendar.AM_PM) == 0 ? "AM" : "PM";

        // String result = "time: " + hour + ":" + minute + ":" + second + " " + am_pm;
        String result = String.format("time: %02d:%02d:%02d %s", hour, minute, second, am_pm);

        Writer writer = resp.getWriter();
        writer.write("<html>");
        writer.write("<div>");
        writer.write(result);
        writer.write("</div>");
        writer.write("</html>");
    }
}
