package src;

import java.util.stream.IntStream;

public class Main {

    public static final int NUM_FLOORS = 55;
    public static final int NUM_ELEVATORS = 7;
    public static final int QUEUE_SIZE = 10;

    public static void main(String[] args) {
        Scheduler scheduler = new Scheduler();

        IntStream.range(0, QUEUE_SIZE).forEach(i -> {
            var src = (int) (Math.random() * NUM_FLOORS);
            var dst = (int) (Math.random() * NUM_FLOORS);
            var success = false;
            while (!success) {
                try {
                    success = scheduler.receiveRequest(src, dst);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        scheduler.start();
    }
}
