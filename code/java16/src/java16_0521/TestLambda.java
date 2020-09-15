package java16_0521;

import java.util.Arrays;
import java.util.Comparator;

public class TestLambda {
    static class Student {
        public String name;
        public int score;

        public Student(String name, int score) {
            this.name = name;
            this.score = score;
        }

        @Override
        public String toString() {
            return "Student{" +
                    "name='" + name + '\'' +
                    ", score=" + score +
                    '}';
        }
    }

    public static void main(String[] args) {
        Student[] students = {
                new Student("张三", 98),
                new Student("李四", 90),
                new Student("王五", 95),
                new Student("赵六", 92),
                new Student("蔡徐坤", 99),
        };
        // 按照分数升序排序, 得把具体的排序规则, 告诉 srot 方法
//        Arrays.sort(students, new Comparator<Student>() {
//            @Override
//            public int compare(Student o1, Student o2) {
//                return o2.score - o1.score;
//            }
//        });
        Arrays.sort(students, (Student o1, Student o2) -> {
            return o1.score - o2.score;
        });
        System.out.println(Arrays.toString(students));
    }
}
