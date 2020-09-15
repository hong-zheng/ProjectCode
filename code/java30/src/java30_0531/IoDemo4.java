package java30_0531;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class IoDemo4 {
    public static void main(String[] args) {
        // 分别使用带缓冲区和不带缓冲区的方式来分别读取一个比较大的文件, 感受时间上的差异.
        // testNoBuffer();
        testBuffer();
    }

    private static void testBuffer() {
        long beg = System.currentTimeMillis();
        try (BufferedInputStream bufferedInputStream = new BufferedInputStream(
                new FileInputStream("d:/sky.jpeg"))) {
            int ch = 0;
            // 此处的一个字节一个读并不是真的从磁盘这样读. 而是先把一片数据
            // 读到内存的缓冲区中, 然后再一个字节一个字节的从缓冲区里读.
            while ((ch = bufferedInputStream.read()) != -1) {
                // 读到的数据也啥都不处理
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();
        System.out.println("time: " + (end - beg));
    }

    private static void testNoBuffer() {
        // 不使用缓冲区来读取一个文件(严格的一个字节一个字节的读, 用户也不要手动指定缓冲区了)
        // 变量名尽量不要缩写. beg??? beg => begin
        long beg = System.currentTimeMillis();
        try (FileInputStream fileInputStream = new FileInputStream("d:/sky.jpeg")) {
            int ch = 0;
            // 看起来 read 返回的是 int, 其实仍然是一次读一个字节.
            // 用 int 的理由是为了能够表示 -1 , 用来表示文件读取结束.
            while ((ch = fileInputStream.read()) != -1) {
                // 啥都不干~~~
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();
        System.out.println("time: " + (end - beg));
    }
}
