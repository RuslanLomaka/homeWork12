class FizzBuzzSynchronized {   //shared data
    int num = 0;
    int maxNum;
    StringBuilder sb = new StringBuilder();
    String appended ="";
    int waitTime = 1;
    public FizzBuzzSynchronized(int maxNum, int waitTime) {
        this.maxNum = maxNum;
        this.waitTime = waitTime;
    }
    private char nextIs = 'D';//The first Runnable who will get access to shared resource is D which calls the producer method

    public synchronized void number() {//producer method goes to Runnable D

        while (num <= maxNum) {//thread runs unless this loop is over
            try {
                while (nextIs != 'D') {
                    wait(); // Wait until the resource is produced
                }
                System.out.println("                                                Number produced");
                System.out.println("                                                num is: " + num);
                appended =num+", ";
                if (num % 3 != 0 && num % 5 != 0) {
                    sb.append(appended);
                }
                    num++;
                nextIs = 'C';
                System.out.println("                                                D is inviting "+nextIs   );
                notifyAll(); // Notify  waiting consumers
                Thread.sleep(waitTime);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
    public synchronized void fizzBuzz() {//consumer method goes to Runnable C
        while (num <= maxNum) {//thread runs unless this loop is over
            try {
                while (nextIs != 'C') {
                    wait(); // Wait until the resource is produced
                }
                System.out.println("                                fizzBuzz method");
                if (num % 3 == 0 && num % 5 == 0) {
                    System.out.println("                                FIZZBUZZ!!! "+num);
                    System.out.println("                                C is inviting "+ nextIs);
                    nextIs = 'D';
                    appended ="fizBuzz, ";
                    sb.append(appended);
                }
                if(num % 3 != 0 || num % 5 != 0){
                        nextIs = 'A';
                    System.out.println("                                not a fizzbuzz case");
                    System.out.println("                                C is inviting "+ nextIs);
                }
                  notifyAll(); // Notify a waiting producer thread
                Thread.sleep(waitTime);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
    public synchronized void fizz() {//consumer method goes to Runnable A
        while (num <= maxNum) {//thread runs unless this loop is over
            try {
                while (nextIs != 'A') {
                    wait(); // Wait until your turn to manipulate resource data
                }
                System.out.println("Fizz method");
                if (num % 3 == 0) {
                    appended ="fizz, ";
                    sb.append(appended);
                    System.out.println("FIZZ!!! " + num);
                }
                nextIs = 'B';
                System.out.println("A is inviting "+ nextIs);
                notifyAll(); // Notify next consumer
                Thread.sleep(waitTime);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
    public synchronized void buzz() {//consumer method goes to Runnable B
        while (num <= maxNum) {//thread runs unless this loop is over
            try {
                while (nextIs != 'B') {
                    wait(); //  Wait until your turn to manipulate resource data
                }
                System.out.println("                Buzz method");
                if (num % 5 == 0) {
                    appended ="buzz, ";
                    sb.append(appended);
                    System.out.println("                BUZZ!!! " + num);
                }
                nextIs = 'D';
                System.out.println("                B is inviting "+ nextIs);
                notifyAll(); // Notify a waiting producer thread
                Thread.sleep(waitTime);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
                System.out.println("\n"+"                         THE END!");

        System.out.println (sb.replace(0,sb.length(),(sb.reverse().toString().replaceFirst(",","."))).reverse());//All this is needed to replace the last comma with a dot
    }
}
class A implements Runnable {//     fizz
    volatile FizzBuzzSynchronized fbs;
    public A(FizzBuzzSynchronized fbs) {
        this.fbs = fbs;
    }
    @Override
    public void run() {
        System.out.println("A is here");
        fbs.fizz();
    }
}
class B implements Runnable {//     buzz
    volatile FizzBuzzSynchronized fbs;
    public B(FizzBuzzSynchronized fbs) {
          this.fbs = fbs;
    }
    @Override
    public void run() {
        System.out.println("                B is here");
        fbs.buzz();
    }
}
class C implements Runnable {
    volatile FizzBuzzSynchronized fbs;
    public C(FizzBuzzSynchronized fbs) {
        this.fbs = fbs;
    }
    @Override
    public void run() {
        System.out.println("                                C is here");
        fbs.fizzBuzz();
    }
}
class D implements Runnable {
    volatile FizzBuzzSynchronized fbs;
    public D(FizzBuzzSynchronized fbs) {
        this.fbs = fbs;
    }
    @Override
    public void run() {
        System.out.println("                                                D is here");
        fbs.number();
    }
}
class FizBuzzTest {
    public static void main(String[] args) {
        FizzBuzzSynchronized fbs = new FizzBuzzSynchronized(45,100);// Put a desired number, and wait() time in milliseconds to constructor
        new Thread(new A(fbs)).start();
        new Thread(new B(fbs)).start();
        new Thread(new C(fbs)).start();
        new Thread(new D(fbs)).start();
//        FizzBuzzSynchronized fbs2 = new FizzBuzzSynchronized(55,150);// Put a desired number, and wait() time in milliseconds to constructor
//        new Thread(new A(fbs2)).start();
//        new Thread(new B(fbs2)).start();// Uncomment this section if you want to create 2 instances and 8 threads
//        new Thread(new C(fbs2)).start();
//        new Thread(new D(fbs2)).start();
    }
}