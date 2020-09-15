package java30_0727;

import sun.net.spi.nameservice.dns.DNSNameService;

public class Demo1 {
    public static void main(String[] args) {
        ClassLoader classLoader1 = Demo1.class.getClassLoader();
        System.out.println(classLoader1);
        ClassLoader classLoader2 = String.class.getClassLoader();
        System.out.println(classLoader2);
        ClassLoader classLoader3 = DNSNameService.class.getClassLoader();
        System.out.println(classLoader3);
    }
}
