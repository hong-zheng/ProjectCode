package java16_0514;

import java.util.Scanner;

class Cat {
    private String name;

    public void eat(String food) {
        System.out.println(name + " 正在吃 " + food);
    }

    public Cat(String name) {
        this.name = name;
    }
}

public class TestReflect {
    public static void main(String[] args) throws ClassNotFoundException {
        // 获取类对象, 获取到 Cat 类的说明书.
        // 第一种获取方式是最灵活的. 写代码的时候根本就不需要知道类名. 在实际运行时再获取类名.
        // 第二种和第三种方式都是需要在写代码的时候就要知道类名.
        // 1. 直接通过全限定类名类获取.
        Scanner scanner = new Scanner(System.in);
        String name = scanner.next();
        Class catClass = Class.forName(name);
        // 2. 通过类的实例来获取
        Cat cat = new Cat("咪咪");
        Class catClass2 = cat.getClass();
        // 3. 通过类来直接获取
        Class catClass3 = Cat.class;

        System.out.println(catClass == catClass2);
        System.out.println(catClass == catClass3);
    }
}
