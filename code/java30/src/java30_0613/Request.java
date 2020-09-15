package java30_0613;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

// 描述一个 HTTP 请求
// 理论上来讲, 请求中也是需要有 body 的. 但是暂时先不管(放到 V3 版本中再说)
public class Request {
    private String method;
    private String url;
    private String version;
    private Map<String, String> headers = new HashMap<>();
    // 加上 URL 中的参数的处理结果.
    private Map<String, String> parameters = new HashMap<>();

    // 这里的属性不太适合提供 setter 方法. 这里的内容应该都是由对象自动解析的, 而不应该在类外部来修改
    public String getMethod() {
        return method;
    }

    public String getUrl() {
        return url;
    }

    public String getVersion() {
        return version;
    }

    public String getHeader(String key) {
        return headers.get(key);
    }

    public String getParameter(String key) {
        return parameters.get(key);
    }

    // 实现构建请求对象的逻辑. 构建过程比较复杂, 使用一个专门的 "工厂方法" 来完成.
    // 参数来自于 Socket 对象
    public static Request build(InputStream inputStream) {
        Request request = new Request();
        // 实现具体的解析请求的过程, 严格遵守 HTTP 协议
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            // 1. 读取首行
            String firstLine = bufferedReader.readLine();
            String[] firstLineToken = firstLine.split(" ");
            request.method = firstLineToken[0];
            request.url = firstLineToken[1];
            request.version = firstLineToken[2];
            // 2. 解析 URL 中的参数部分
            //  2.1 先找 ? 在 url 中的位置
            int pos = request.url.indexOf("?");
            if (pos != -1) {
                // 2.2 一个 url 中也是可能不带参数的, 没有 ? 这种就不管了.
                //     如果有 ? 再来处理参数
                String parameters = request.url.substring(pos + 1);
                // 2.3 把整个参数字符串先按照 & 来切分成若干个键值对
                String[] kvToken = parameters.split("&");
                for (String kv : kvToken) {
                    // 针对切分出的键值对的每个部分, 再按照 = 来切分
                    String[] result = kv.split("=");
                    request.parameters.put(result[0], result[1]);
                }
            }
            // 3. 处理请求头 header 部分
            String line = "";
            while ((line = bufferedReader.readLine()) != null && line.length() != 0) {
                String[] headerToken = line.split(": ");
                request.headers.put(headerToken[0], headerToken[1]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return request;
    }

    @Override
    public String toString() {
        return "Request{" +
                "method='" + method + '\'' +
                ", url='" + url + '\'' +
                ", version='" + version + '\'' +
                ", headers=" + headers +
                ", parameters=" + parameters +
                '}';
    }
}
