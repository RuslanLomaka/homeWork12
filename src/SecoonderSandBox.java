class SecoonderOne extends Thread {// I extended Thread here
    @Override
    public void run() {
        int i = 0;
        while (true) {
            System.out.println("Thread 1: one second has passed  >>> " + i + " <<<  times");
            i++;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }
}

class SecoonderFive implements Runnable {// and implemented Runnable here
    @Override
    public void run() {
        int i = 0;
        while (true) {
            System.out.println("                                                   Thread 2: 5 seconds have passed >>> " + i + " <<< times");
            i++;
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }
}

public class SecoonderSandBox {
    public static void main(String[] args) {
        SecoonderOne thread1 = new SecoonderOne();// when extend Thread we can instantiate, and use it right away

        SecoonderFive s5 = new SecoonderFive();// when implement Runnable we have to
        Thread thread2 = new Thread(s5);//Put the instance into the constructor of Thread as an argument

        thread1.start();
        thread2.start();
    }
}
