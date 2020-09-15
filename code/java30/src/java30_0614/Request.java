package java30_0614;

import com.sun.org.apache.regexp.internal.REUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class Request {
    private String method;
    private String url;
    private String version;
    private Map<String, String> headers = new HashMap<>();
    private Map<String, String> parameters = new HashMap<>();
    // cookie 本来是一个字符串, 但是这个字符串中我们也经常按照键值对的方式来组织.
    private Map<String, String> cookies = new HashMap<>();
    private String body = "";

    public static Request build(InputStream inputStream) {
        Request request = new Request();
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            // 1. 处理首行
            String firstLine = bufferedReader.readLine();
            String[] firstLineToken = firstLine.split(" ");
            request.method = firstLineToken[0];
            request.url = firstLineToken[1];
            request.version = firstLineToken[2];
            // 2. 解析 url 中的参数
            int pos = request.url.indexOf("?");
            if (pos != -1) {
                String parameters = request.url.substring(pos + 1);
                parseParameters(parameters, request.parameters);
            }
            // 3. 处理 header
            String line = "";
            while ((line = bufferedReader.readLine()) != null && line.length() != 0) {
                String[] headerToken = line.split(": ");
                request.headers.put(headerToken[0], headerToken[1]);
            }
            // 4. 处理 cookie (cookie 只是一个字符串, 具体里面是啥格式, 都是程序猿自己约定的)
            //    就可以按照像刚才 url 中的 parameters 的方式来组织 cookie 的内容.
            // String cookie = request.cookies.get("Cookie");
            String cookie = request.headers.get("Cookie");
            if (cookie != null)  {
                // cookie 在请求中存在, 就按照之前约定的方式来解析一下.
                parseParameters(cookie, request.cookies);
            }
            // 5. 处理 body 中的数据.
            if ("post".equalsIgnoreCase(request.method)
                || "put".equalsIgnoreCase(request.method)) {
                // 如果不是 post 请求, 就不处理 body
                // body 的长度取决于 Content-Length
                int len = Integer.parseInt(request.headers.get("Content-Length"));
                // len 表示的是 body 的长度, 单位是字节
                // 下面 new 的数组长度, 也是len, 但是是 len 这么长的字符.
                // 换句话说, 下面的缓冲区长度的字节数实际是比 body 的长度要长的.
                char[] buffer = new char[len];
                bufferedReader.read(buffer, 0, len);
                request.body = new String(buffer);
                // body 的格式取决于 Content-Type. 此处为了简单, 只考虑一种 body 的格式.
                // form 表单提交数据的格式. (形如 username2222=aaa&password1234=123)
                // parameters 中既包含 url 中的参数, 也包含 body 中的数据.
                parseParameters(request.body, request.parameters);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return request;
    }

    private static void parseParameters(String input, Map<String, String> output) {
        // 1. 先按照 & 切分.
        String[] kvTokens = input.split("&");
        // 2. 再按照 = 切分.
        for (String kv : kvTokens) {
            String[] result = kv.split("=");
            output.put(result[0], result[1]);
        }
    }

    public String getMethod() {
        return method;
    }

    public String getUrl() {
        return url;
    }

    public String getVersion() {
        return version;
    }

    public String getBody() {
        return body;
    }

    public String getHeader(String key) {
        return headers.get(key);
    }

    public String getCookie(String key) {
        return cookies.get(key);
    }

    public String getParameter(String key) {
        return parameters.get(key);
    }

    @Override
    public String toString() {
        return "Request{" +
                "method='" + method + '\'' +
                ", url='" + url + '\'' +
                ", version='" + version + '\'' +
                ", headers=" + headers +
                ", parameters=" + parameters +
                ", cookies=" + cookies +
                ", body='" + body + '\'' +
                '}';
    }
}
