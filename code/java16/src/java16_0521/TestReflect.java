package java16_0521;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

class Cat {
    private String name = "小猫";

    public Cat() {

    }

    public Cat(String name) {
        this.name = name;
    }

    public void eat(String food) {
        System.out.println(name + " 正在吃 " + food);
    }

    public void eat(String food1, String food2) {
        System.out.println(name + " 正在吃 " + food1 + ", " + food2);
    }
}

public class TestReflect {
    // 通过反射来实例化对象
    public static void testInstance() throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        Class catClass = Class.forName("java16_0521.Cat");

        // Cat cat = new Cat();
        Cat cat = (Cat) catClass.newInstance();
    }

    // 通过反射来获取对象的属性
    public static void testField() throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
        // 1. 先获取到类对象.
        Class catClass = Class.forName("java16_0521.Cat");
        // 2. 借助类对象, 获取到指定的 Field 对象
        //    现在这一步获取到的 field 对象相当于从一个大图纸中获取了一个局部的图纸.
        Field field = catClass.getDeclaredField("name");
        field.setAccessible(true); // 专门处理 private 成员的方式. 破门而入.
        // 3. 根据图纸来修改/获取对象的相关字段
        Cat cat = new Cat();
        // 可以通过 get 方法获取对应属性
        // 也可以通过 set 方法来修改属性
        field.set(cat, "咪咪");
        String name = (String) field.get(cat);
        System.out.println(name);
    }

    public static void testMethod() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        // 1. 先获取到类对象.
        Class catClass = Class.forName("java16_0521.Cat");
        // 2. 根据类对象, 根据名字获取到指定的 Method 对象
        //    getMethod 从第二个参数开始, 其实是用来描述当前 eat 对应的方法应该是哪个版本(当出现 eat 被重载的时候, 能够借助参数列表的类型区分出来).
        //    上面的代码是获取到一个参数的版本的 eat
        //    下面的代码是获取到两个参数的版本的 eat
        Method method = catClass.getMethod("eat", String.class);
        method.setAccessible(true);
        // Method method = catClass.getMethod("eat", String.class, String.class);
        // 3. 借助 Method 对象来调用指定的方法(对于非静态方法, 需要指定实例来调用).
        Cat cat = new Cat();
        method.invoke(cat, "鱼");
    }

    public static void testConstructor() throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        // 1. 获取类对象
        Class catClass = Class.forName("java16_0521.Cat");
        // 2. 借助类对象获取 Constructor 对象
        //    下面的操作意思是获取到参数为一个 String 的构造方法.
        Constructor constructor = catClass.getConstructor(String.class);
        constructor.setAccessible(true);
        // 3. 根据 Constructor 实例化对象
        Cat cat = (Cat) constructor.newInstance("小黑");

        cat.eat("猫粮");
    }

    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchFieldException, NoSuchMethodException, InvocationTargetException {
        // testField();
        // testMethod();
        testConstructor();
    }
}
