package test;

import sun.net.spi.nameservice.dns.DNSNameService;

public class Test {
    public static void main(String[] args) {
        ClassLoader classLoader1 = Test.class.getClassLoader();
        System.out.println(classLoader1);
        ClassLoader classLoader2 = String.class.getClassLoader();
        System.out.println(classLoader2);
        ClassLoader classLoader3 = DNSNameService.class.getClassLoader();
        System.out.println(classLoader3);

        System.out.println("==================");
        System.out.println(classLoader1.getParent());
        System.out.println(classLoader3.getParent());
    }
}
