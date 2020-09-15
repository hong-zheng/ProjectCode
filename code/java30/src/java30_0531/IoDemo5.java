package java30_0531;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class IoDemo5 {
    // 使用字符流来拷贝一个文本文件.
    public static void main(String[] args) throws IOException {
//        // 1. 打开文件
//        FileReader fileReader = new FileReader("d:/test.txt");
//        FileWriter fileWriter = new FileWriter("d:/test2.txt");
//        // 2. 读写文件
//        int ch = 0;
//        // 此处的 read 每次读到的是一个 字符 (char)
//        while ((ch = fileReader.read()) != -1) {
//            fileWriter.write(ch);
//        }
//        // 3. 关闭文件释放资源
//        fileReader.close();
//        fileWriter.close();

        // 为了更好的处理异常, 还是更建议使用 try ( )
        try (FileReader fileReader = new FileReader("d:/test.txt");
             FileWriter fileWriter = new FileWriter("d:/test2.txt")) {
            char[] buffer = new char[1024];
            int len = 0;
            while ((len = fileReader.read(buffer)) != -1) {
                fileWriter.write(buffer, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
