package java16_0802;

import sun.net.spi.nameservice.dns.DNSNameService;

public class Demo3 {
    public static void main(String[] args) {
        ClassLoader classLoader1 = Demo3.class.getClassLoader();
        System.out.println(classLoader1);
        ClassLoader classLoader2 = String.class.getClassLoader();
        System.out.println(classLoader2);
        ClassLoader classLoader3 = DNSNameService.class.getClassLoader();
        System.out.println(classLoader3);
    }
}
