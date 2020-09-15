package java16_0618;

import java.io.*;

public class IODemo3 {
    public static void main(String[] args) throws IOException {
        copyFile2();
    }

    private static void copyFile() throws IOException {
        // 需要创建的实例是 BufferedInputStream 和 BufferedOutputStream
        // 要创建这样的实例, 需要先创建 FileInputStream 和 FileOutputStream
        FileInputStream fileInputStream = new FileInputStream("d:/test_dir/rose.jpg");
        FileOutputStream fileOutputStream = new FileOutputStream("d:/test_dir/rose2.jpg");
        BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
        byte[] buffer = new byte[1024];
        int length = -1;
        while ((length = bufferedInputStream.read(buffer)) != -1) {
            bufferedOutputStream.write(buffer, 0, length);
        }
        // 此处涉及到四个流对象~~
        // 调用这一组 close 时, 就会自动关闭内部包含的 FileInputStream 和 FileOutputStream
        // 此处不需要写 四次 关闭~~
        bufferedInputStream.close();
        bufferedOutputStream.close();
        // fileInputStream.close(); // 这里不就相当于是调用两次 close 了么? 阅读原码就会发现, close 两次不会有副作用.
    }

    private static void copyFile2() {
        try (BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream("d:/test_dir/rose.jpg"));
             BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream("d:/test_dir/rose2.jpg"))) {
            int len = -1;
            byte[] buffer = new byte[1024];
            while ((len = bufferedInputStream.read(buffer)) != -1) {
                System.out.println("len: " + len);
                bufferedOutputStream.write(buffer, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void copyFile3() {
        try (BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream("d:/test_dir/rose.jpg"));
             BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream("d:/test_dir/rose2.jpg"))) {
            int len = -1;
            byte[] buffer = new byte[1024];
            while (true) {
                len = bufferedInputStream.read(buffer);
                if (len == -1) {
                    break;
                }
                bufferedOutputStream.write(buffer, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
