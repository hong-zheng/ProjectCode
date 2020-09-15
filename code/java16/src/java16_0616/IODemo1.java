package java16_0616;

import java.io.File;
import java.io.IOException;

public class IODemo1 {
    public static void main(String[] args) throws IOException {
        // 文件操作
//        File file = new File("d:/test.txt");
//        System.out.println("文件是否存在: " + file.exists());
//        System.out.println("文件是否是普通文件: " + file.isFile());
//        System.out.println("文件是否是目录: " + file.isDirectory());
//        file.delete();
//        System.out.println("文件是否存在: " + file.exists());
        // file.createNewFile();
//        System.out.println(File.separator);

        // 目录操作
//        File file = new File("d:/test_dir/1/2/3/4");
//        System.out.println(file.exists());
//        System.out.println(file.isDirectory());
//        System.out.println(file.getParent());

        // listFiles
        File file = new File("d:/test_dir");
//        File[] files = file.listFiles();
//        for (File f : files) {
//            System.out.println(f);
//        }
        listAllFiles(file);
    }

    // 递归的罗列出一个目录中的所有文件.
    private static void listAllFiles(File f) {
        if (f.isDirectory()) {
            // 如果是目录, 就把目录中包含的文件都罗列出来.
            File[] files = f.listFiles();
            for (File file : files) {
                listAllFiles(file);
            }
        } else {
            // 把这个文件的路径直接打印出来.
            System.out.println(f);
        }
    }
}
