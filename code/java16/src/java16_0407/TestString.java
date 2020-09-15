package java16_0407;

import java.lang.reflect.Field;

public class TestString {
    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        String str1 = "hehe";
        String str2 = "Hehe";
//        System.out.println(str1.equals(str2));
//        System.out.println(str1.equalsIgnoreCase(str2));
        System.out.println(str1.compareTo(str2));
    }
}
