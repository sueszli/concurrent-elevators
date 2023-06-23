package src;

import java.util.function.Supplier;
import src.Scheduler.Request;

public class Main {

    public static final int NUM_FLOORS = 55;
    public static final int NUM_ELEVATORS = 7;
    public static final int QUEUE_SIZE = 10;
    public static final int PROCESSING_DELAY = 1000;

    public static void main(String[] args) throws InterruptedException {
        // create scheduler
        var scheduler = new Scheduler();

        // send requests
        Supplier<Request> requestGenerator = () -> new Request((int) (Math.random() * NUM_FLOORS), (int) (Math.random() * NUM_FLOORS));
        for (int i = 0; i < QUEUE_SIZE; i++) {
            while (true) {
                var success = scheduler.receiveRequest(requestGenerator.get());
                if (success) {
                    break;
                }
            }
        }

        // start, wait, and shutdown scheduler
        scheduler.start();
        Thread.sleep(1000);
        scheduler.shutdown();
    }
}
