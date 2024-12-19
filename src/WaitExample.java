class SharedResource {
    private boolean available = false;

    public synchronized void produce() {
        try {
            while (available) {
                wait(); // Wait until the resource is consumed
            }
            System.out.println("Producing resource...");
            available = true;
            notify(); // Notify a waiting consumer thread
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public synchronized void consume() {
        try {
            while (!available) {
                wait(); // Wait until the resource is produced
            }
            System.out.println("Consuming resource...");
            available = false;
            notify(); // Notify a waiting producer thread
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

public class WaitExample {
    public static void main(String[] args) {
        SharedResource resource = new SharedResource();

        Thread producer = new Thread(resource::produce);
        Thread consumer = new Thread(resource::consume);

        producer.start();
        consumer.start();
    }
}
