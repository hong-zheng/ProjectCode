package java16_0609;

public class ThreadDemo20 {
    public static void main(String[] args) throws InterruptedException {
        Object object = new Object();
        synchronized (object) {
            System.out.println("等待前");
            object.wait();
            System.out.println("等待后");
        }
    }
}
