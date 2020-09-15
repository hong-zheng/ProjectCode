package java30_0513;

// 创建线程的几种方式.
// 1. 创建一个类, 显式的继承自 Thread, 重写 run 方法.
// 2. 创建一个匿名内部类, 继承自 Thread, 也重写 run 方法.
// 3. 创建一个类, 实现 Runnable 接口, 再把借助这个新类的对象来构造 Thread 对象.
//    这种创建线程的方式, 相当于把线程内部要执行的逻辑和 Thread 对象本身分离开了.
//    这种写法代码耦合性更小.
// 4. 创建一个匿名内部类, 实现 Runnable 接口, 重写 run 方法.
// 5. 使用 lambda 表示来表示新线程中的逻辑.
public class ThreadDemo3 {
    static class MyRunnable implements Runnable {
        @Override
        public void run() {
            System.out.println("我是一个新线程");
        }
    }

    public static void main(String[] args) {
        // 显式创建类实现 Runnable 接口
//        Thread t = new Thread(new MyRunnable());
//        t.start();

        // 使用匿名内部类实现 Runnable 接口
//        Thread t = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                System.out.println("我是一个新线程");
//            }
//        });
//        t.start();

        // 使用 lambda 表达式来创建线程
//        Thread t = new Thread(() -> {
//            System.out.println("我是一个新线程");
//        });
//        t.start();
    }
}
