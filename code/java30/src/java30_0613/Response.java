package java30_0613;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

// 描述一个 HTTP 响应
// 并且完成最终的构造过程.
public class Response {
    private String version = "HTTP/1.1";
    private int status;
    private String message;
    private Map<String, String> headers = new HashMap<>();
    // 正文部分可能需要逐步构造. 搞成 StringBuilder 更方便拼接
    private StringBuilder body = new StringBuilder();
    // 最终数据需要写入到一个 OutputStream 对象中
    private OutputStream outputStream = null;

    // 和 Request.build 类似, 这个 build 也是一个工厂方法
    public static Response build(OutputStream outputStream) {
        Response response = new Response();
        response.outputStream = outputStream;
        return response;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setHeader(String key, String value) {
        this.headers.put(key, value);
    }

    public void writeBody(String content) {
        body.append(content);
    }

    public void flush() {
        // 把一个完整的 Response 数据写入到 OutputStream 中
        try (BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream))) {
            bufferedWriter.write(version + " " + status + " " + message + "\n");
            // 写 header 之前别忘了有一个特殊的 header 需要构造进去
            headers.put("Content-Length", body.toString().getBytes().length + "");
            for (HashMap.Entry<String, String> entry : headers.entrySet()) {
                bufferedWriter.write(entry.getKey() + ": " + entry.getValue() + "\n");
            }
            bufferedWriter.write("\n");
            bufferedWriter.write(body.toString());
            // 带缓冲区的流对象, 每次调用 write 都是写到缓冲区中. 调用 flush 操作就是真的把缓冲区
            // 的数据写入到最终的 Socket 中.
            bufferedWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
