package java30_0525;

public class ThreadDemo27 {
    static class BlockingQueue {
        private int[] array = new int[1000];
        private volatile int head = 0;
        private volatile int tail = 0;
        private volatile int size = 0;

        public void put(int val) throws InterruptedException {
            synchronized (this) {
                while (size == array.length) {
                    this.wait();
                }
                // 把 val 放到队尾位置上即可
                array[tail] = val;
                tail++;
                if (tail >= array.length) {
                    tail = 0;
                }
                // tail = tail % array.length;
                size++;
                // 通知消费者线程过来消费
                notify();
            }
        }

        public int take() throws InterruptedException {
            int ret = 0;
            synchronized (this) {
                while (size == 0) {
                    wait();
                }

                ret = array[head];
                head++;
                if (head >= array.length) {
                    head = 0;
                }
                size--;

                notify();
            }
            return ret;
        }
    }

    public static void main(String[] args) {
        BlockingQueue blockingQueue = new BlockingQueue();
        Thread customer = new Thread() {
            @Override
            public void run() {
                while (true) {
                    try {
                        int ret = blockingQueue.take();
                        System.out.println("消费者: " + ret);
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        customer.start();

        Thread producer = new Thread() {
            @Override
            public void run() {
                for (int i = 0; i < 10000; i++) {
                    try {
                        blockingQueue.put(i);
                        System.out.println("生产者: " + i);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        producer.start();
    }
}
