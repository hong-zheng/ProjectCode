package java30_0531;

import java.io.*;

public class IoDemo3 {
    // 带缓冲区的流的使用
    public static void main(String[] args) throws IOException {
        // 还是以刚才的拷贝文件为例
        // 1. 先打开文件
        BufferedInputStream bufferedInputStream = new BufferedInputStream(
                new FileInputStream("d:/rose.jpg"));
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(
                new FileOutputStream("d:/rose2.jpg"));
        // 2. 读取文件并写入到另外一个文件, 也可以用户指定一个缓冲区
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = bufferedInputStream.read(buffer)) != -1) {
            bufferedOutputStream.write(buffer, 0, len);
        }
        // 3. 关闭文件, 释放资源.
        //    针对 buffered 版本的流对象进行关闭的时候, 也会自动关闭
        //    内部包含的 InputStream 和 OutputStream 对象.
        //    本质上我们是希望关闭动作释放文件描述符表中的对应表项.
        //    此处的代码中 BufferedInputStream 和 FileInputStream 其实对应的是同一个文件, 同一个表项.
        //    BufferOutputStream 和 FileOutputStream 也是对应的同一个文件, 同一个表项.
        bufferedInputStream.close();
        bufferedOutputStream.close();

        // 为了处理异常, 让代码更加严谨, 仍然可以使用 try ( ) 形式来进行处理
//        try (BufferedInputStream bufferedInputStream = new BufferedInputStream(
//                     new FileInputStream("d:/rose.jpg"));
//             BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(
//                     new FileOutputStream("d:/rose2.jpg")) ) {
//            byte[] buffer = new byte[1024];
//            int len = 0;
//            while ((len = bufferedInputStream.read(buffer)) != -1) {
//                bufferedOutputStream.write(buffer, 0, len);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
}
