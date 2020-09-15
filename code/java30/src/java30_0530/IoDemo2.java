package java30_0530;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class IoDemo2 {
    public static void main(String[] args) throws IOException {
        copyImage();
    }

    // IOException 是 FileNotFoundException 的父类. throws IOException 的话就不需要
    // 再额外声明 FileNotFoundException 了.
    public static void copyImage() {
        FileInputStream fileInputStream = null;
        FileOutputStream fileOutputStream = null;
        try {
            fileInputStream = new FileInputStream("d:/rose.jpg");
            fileOutputStream = new FileOutputStream("d:/rose2.jpg");
            // 0. 打开文件, 在构造方法中来指定打开哪个文件.
            //    可以用一个 File 对象来指定, 也可以直接用一个 路径 字符串来指定.
            //    对于 InputStream 来说, 如果文件不存在, 直接就会抛出异常.
            //    对于 OutputStream 来说, 如果文件不存在, 就会尝试创建文件.
            // 1. 读取玫瑰花文件.
            //    read() 版本是一次读一个字节. 读到的内容会放到返回值中.
            //    read(byte[]) 版本是一次读 N 个字节, N 取决于 byte[] 的长度, 会尽量尝试把数组填满.
            //                 byte[] 就相当于是用户指定的缓冲区, 返回值表示实际读到的字节数. 如果返回 -1 表示读取结束了
            //    read(byte[], int offset, int len) 版本是针对上一个版本的加强版本.
            //    此处的 buffer 都是用户指定的缓冲区, 而 FileInputStream 和 FileOutputStream 内部没有缓冲区.
            byte[] buffer = new byte[1024];
            int len = 0;
            // 这样的循环读取就能保证把整个文件都读完.
            // 这个代码的含义是, 先调用 read, 把返回值写入 len 变量中.
            // 再拿 len 和 -1 进行比较.
            // 常见写法
//        while ((len = fileInputStream.read(buffer)) != -1) {
//
//        }
            // 如果觉得不好理解
            while (true) {
                len = fileInputStream.read(buffer);
                System.out.println("len: " + len);
                if (len == -1) {
                    break;
                }
                // 2. 把文件内容写入到另外一个文件中.
                // write 有三个版本
                // write(int b)  一次写一个字节.
                // write(byte[] buffer) 一次写 N 个字节. 把整个数组中的内容全写进去.
                // write(byte[] buffer, int offset, int len) 第二个版本的加强版.
                fileOutputStream.write(buffer, 0, len);
            }
            // 读写完成之后, 一定要记得关闭文件.
            // 关闭顺序无所谓.
            // [注意!!!!!!] 这个代码虽然写了 close , 但是仍然存在潜在的问题.
            // close 可能会执行不到!!!!
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fileInputStream.close();
                fileOutputStream.close();
            } catch (IOException | NullPointerException e) {
                e.printStackTrace();
            }
        }
    }

    // 上面的写法虽然能够顺利考虑到各种安全问题, 但是写起来太麻烦了, 容易写错.
    // 还有一种更简单的写法
    public static void copyImage2() {
        try (FileInputStream fileInputStream = new FileInputStream("d:/rose.jpg");
             FileOutputStream fileOutputStream = new FileOutputStream("d:/rose2.jpg")) {
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = fileInputStream.read(buffer)) != -1) {
                fileOutputStream.write(buffer, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
