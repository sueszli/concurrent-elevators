package src;

import java.util.stream.IntStream;

public class Main {

    public static final int NUM_FLOORS = 55;
    public static final int NUM_ELEVATORS = 7;
    public static final int QUEUE_SIZE = 10;
    public static final int PROCESSING_DELAY = 1000;

    public static void main(String[] args) throws InterruptedException {

        // create scheduler
        var scheduler = new Scheduler();

        IntStream.range(0, QUEUE_SIZE).forEach(i -> {
            var sentRequest = false;
            while (!sentRequest) {
                var src = (int) (Math.random() * NUM_FLOORS);
                var dst = (int) (Math.random() * NUM_FLOORS);
                try {
                    // retry sending random requests until valid
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
