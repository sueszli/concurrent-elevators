package src;

import java.util.function.Supplier;
import java.util.stream.IntStream;

public class Main {

    public static final int NUM_FLOORS = 55;
    public static final int NUM_ELEVATORS = 7;
    public static final int QUEUE_SIZE = 10;
    public static final int PROCESSING_DELAY = 1000;

    public static void main(String[] args) throws InterruptedException {

        // create scheduler
        var scheduler = new Scheduler();

        record Request(int src, int dst) {
        }
        Supplier requestSupplier = () -> new Request((int) (Math.random() * NUM_FLOORS),
                (int) (Math.random() * NUM_FLOORS));

        IntStream.range(0, QUEUE_SIZE).forEach(i -> {
            // retry sending random requests until valid
            var sentRequest = false;
            while (!sentRequest) {
                var src = (int) (Math.random() * NUM_FLOORS);
                var dst = (int) (Math.random() * NUM_FLOORS);
                try {
                    sentRequest = scheduler.receiveRequest(src, dst);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        // start scheduler
        scheduler.start();

        // wait for scheduler to finish
        Thread.sleep(1000);

        // shutdown scheduler
        scheduler.shutdown();
    }
}
