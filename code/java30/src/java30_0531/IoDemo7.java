package java30_0531;

import java.io.*;

public class IoDemo7 {
    static class Person implements Serializable {
        public String name;
        public int age;
    }

    public static void serializePerson(Person person) {
        // 把 Person 对象序列化并保存到文件中
        // ObjectOutputStream 既帮我们完成序列化, 也完成了 IO 操作.
        // 要想让对象能够借助 ObjectOutputStream 来序列化, 就必须让类实现 Serializable 接口
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(
                new FileOutputStream("d:/person.txt"))) {
            objectOutputStream.writeObject(person);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Person deserializePerson() {
        // 把 Person 对象从文件中读出来, 并进行反序列化, 并返回.
        // readObject 既完成了 IO 操作, 也完成了反序列化操作.
        try (ObjectInputStream objectInputStream = new ObjectInputStream(
                new FileInputStream("d:/person.txt"))) {
            Person person = (Person) objectInputStream.readObject();
            return person;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        // 针对 Person 对象进行序列化和反序列化
        // 借助序列化操作来把 Person 对象保存到文件中
//        Person person = new Person();
//        person.name = "汤老湿";
//        person.age = 29;
        // serializePerson(person);

        Person person2 = deserializePerson();
        System.out.println("name: " + person2.name + " age: " + person2.age);
    }
}
