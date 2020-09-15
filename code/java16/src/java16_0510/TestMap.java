package java16_0510;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

class Student {
    public String name;
    public int age;
    public String grade;
    public String school;

    public Student(String name, int age, String grade, String school) {
        this.name = name;
        this.age = age;
        this.grade = grade;
        this.school = school;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", grade='" + grade + '\'' +
                ", school='" + school + '\'' +
                '}';
    }
}

public class TestMap {
    public static void main(String[] args) {
        Student s1 = new Student("蔡徐坤", 20, "大四", "陕科大");
        Student s2 = new Student("罗志祥", 30, "大三", "西工院");
        Student s3 = new Student("蒋凡", 40, "大十", "阿里巴巴");

        Map<String, Student> studentMap = new TreeMap<>();
        studentMap.put(s1.name, s1);
        studentMap.put(s2.name, s2);
        studentMap.put(s3.name, s3);

        // 当前键值对 name -> student. 给定姓名就可以查找到对应的学生信息
        String name = "蔡徐坤";

        Student student = studentMap.get(name);
        System.out.println(student);

        Student s4 = new Student("蔡徐坤", 100, "大一", "社会大学");
        studentMap.put(s4.name, s4);

        Student student2 = studentMap.get(name);
        System.out.println(student2);

        // 遍历一个 Map
        // Entry 条目. 也就是键值对.
        for (HashMap.Entry<String, Student> entry : studentMap.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }

        Iterator<Map.Entry<String, Student>> iterator = studentMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Student> entry = iterator.next();
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }

        // 如果想按照其他映射来查找, 例如按年龄, 就需要其他的 Map
//        Map<Integer, Student> studentMap2 = new HashMap<>();
//        studentMap2.put(s1.age, s1);
//        studentMap2.put(s2.age, s2);
//        studentMap2.put(s3.age, s3);
//
//        Student student2 = studentMap2.get(20);
//        System.out.println(student2);
    }
}
