package java30_0531;

import java.io.*;

public class IoDemo6 {
    // 写一个字符流的带缓冲区版本
    public static void main(String[] args) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("d:/test.txt"));
             BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("d:/test2.txt"))) {
            // 字符流的缓冲区版本, 读方法其实种类更多一些
            // 其中 read 的各个版本和 FileReader 都是一致的.
            // 除此之外还有一个 readLine, 能够一次读一行数据.
            // readLine 如果读取文件结束, 就会返回 null
            // 读操作有 readLine 版本. 写操作没有. 如果想写一行, 直接手动在后面加上个 \n
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                bufferedWriter.write(line);
                bufferedWriter.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
