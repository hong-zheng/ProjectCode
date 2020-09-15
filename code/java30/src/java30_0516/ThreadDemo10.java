package java30_0516;

public class ThreadDemo10 {
    public static void main(String[] args) {
        // Thread.State 是一个枚举类型.
        for (Thread.State state: Thread.State.values()) {
            System.out.println(state);
        }
    }
}
