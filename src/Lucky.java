public class Lucky {
    static int x = 0;
    static int count = 0;
    static final Object lock = new Object();

    static class LuckyThread extends Thread {
        @Override
        public void run() {
            while (true) {
                int current;
                synchronized (lock) {
                    if (x >= 999999) break;
                    current = ++x;
                }
                // Проверяем условие для "счастливого числа" вне блока synchronized
                if ((current % 10) + (current / 10) % 10 + (current / 100) % 10 ==
                        (current / 1000) % 10 + (current / 10000) % 10 + (current / 100000) % 10) {
                    System.out.println(current);
                    synchronized (lock) {
                        count++;
                    }
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new LuckyThread();
        Thread t2 = new LuckyThread();
        Thread t3 = new LuckyThread();
        t1.start();
        t2.start();
        t3.start();
        t1.join();
        t2.join();
        t3.join();
        System.out.println("Total: " + count);
    }
}
