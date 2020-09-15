package java16_0411;

class Student {
    public String name;
    public String sex;
}

public class MyArrayList<E> {
    // 直接 new E[100] 这种方式是不行滴
    // private E[] array = new E[100];
    private E[] array = (E[])new Object[100];
    private int size;

    public void add(E o) {
        array[size] = o;
        size++;
    }

    E get(int index) {
        return array[index];
    }

    public static void main(String[] args) {
//        MyArrayList myArrayList = new MyArrayList();
//        myArrayList.add("我");
//        myArrayList.add("爱");
//        myArrayList.add("Java");
//
//        String ret = (String)myArrayList.get(0);
//
//        MyArrayList myArrayList2 = new MyArrayList();
//        myArrayList2.add(new Student());
//        myArrayList2.add(new Student());
//        myArrayList2.add(new Student());
//
//        String ret2 = (String)myArrayList2.get(0);

        MyArrayList<String> myArrayList = new MyArrayList<>();
        myArrayList.add("hehe");
        String s = myArrayList.get(0);

        MyArrayList<Student> myArrayList2 = new MyArrayList<>();
        myArrayList2.add(new Student());
        Student student = myArrayList2.get(0);
    }
}
