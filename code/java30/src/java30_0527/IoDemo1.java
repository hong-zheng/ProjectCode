package java30_0527;

import java.io.File;
import java.io.IOException;

public class IoDemo1 {
    public static void main(String[] args) {
        // d:/test.txt 这就是一个文件的路径.
        // 在 Windows 上也可以写成 d:\test.txt
//        File file = new File("d:/test.txt");
//        File file2 = new File("d:\\test.txt");
        // 针对项目目录中的 test.txt, 使用 File 来表示, 就有两种方式.
//        // 1. 使用绝对路径
//        File file = new File("D:\\project\\classroom_code\\java30\\testIO\\test.txt");
//        // 2. 使用相对路径(默认的 "工作目录" 项目的根目录)
//        File file2 = new File("./testIO/test.txt");

        // 判定文件/路径是否存在
//        File file = new File("D:\\project\\classroom_code\\java30\\testIO\\test.txt");
//        System.out.println(file.exists());

        // 判定文件是否是目录
//        File file = new File("D:\\project\\classroom_code\\java30\\testIO\\test.txt");
//        System.out.println(file.isDirectory());
//        File file2 = new File("D:\\project\\classroom_code\\java30\\testIO");
//        System.out.println(file2.isDirectory());

        // 判定文件是否是普通文件
//        File file = new File("D:\\project\\classroom_code\\java30\\testIO\\test.txt");
//        System.out.println(file.isFile());
//        File file2 = new File("D:\\project\\classroom_code\\java30\\testIO");
//        System.out.println(file2.isFile());

        // 删除文件
//        File file = new File("D:\\project\\classroom_code\\java30\\testIO\\test.txt");
//        file.delete();

        // 创建文件
//        File file = new File("D:\\project\\classroom_code\\java30\\testIO\\test.txt");
//        try {
//            file.createNewFile();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        // 查看当前系统上的路径分割符
        // System.out.println(File.separator);

//        // 创建目录
//        File file = new File("d:/java30");
//        // mk => make   dir => directory
//        file.mkdir();

        // 创建多级目录
        // File file = new File("d:/aaa/bbb/ccc/ddd");
        // file.mkdir();
        // file.mkdirs();

        // 获取父目录的路径和File对象
//        File file = new File("d:/aaa/bbb/ccc/ddd");
//        // 获取到的是一个路径字符串
//        System.out.println(file.getParent());
//        // 获取到的是一个 File 对象
//        System.out.println(file.getParentFile());

        // 获取目录中包含的内容
//        File file = new File("d:/");
//        File[] files = file.listFiles();
//        for (File f : files) {
//            System.out.println(f);
//        }

        // 递归的把一个目录中所有的目录和文件都获取到
        File root = new File("d:/program2");
        listAllFiles(root);
    }

    private static void listAllFiles(File file) {
        if (file.isDirectory()) {
            // 如果当前的 file 是一个目录, 就递归的往下去找
            File[] files = file.listFiles();
            for (File f : files) {
                listAllFiles(f);
            }
        } else {
            // 如果当前的 file 是一个文件, 就直接打印该文件的路径
            System.out.println(file);
        }
    }
}
