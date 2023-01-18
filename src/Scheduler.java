package src;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.IntStream;

import static src.Config.NUM_ELEVATORS;
import static src.Config.QUEUE_SIZE;

public class Scheduler implements Runnable {

    private final BlockingQueue<Request> requestQueue = new ArrayBlockingQueue<>(QUEUE_SIZE);
    private final ConcurrentHashMap<Integer, Elevator> elevators = new ConcurrentHashMap<>();
    private boolean alive = true;

    public Scheduler() {
        new Thread(this, "scheduler").start();
    }

    public boolean sendRequest(int src, int dst) {
        return this.requestQueue.offer(new Request(src, dst));
    }

    public void shutdown() {
        alive = false;
    }

    private void handleRequest(Request r) {

    }

    @Override
    public void run() {
        // initialize elevators
        IntStream.range(0, NUM_ELEVATORS).forEach(i -> {
            final var elevator = new Elevator();
            var id = elevator.getID();
            new Thread(elevator, String.format("%02d", id)).start();
            this.elevators.put(id, elevator);
        });

        // assign requests to elevators
        while (alive) {
            try {
                var r = requestQueue.take();
                handleRequest(r);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // shutdown elevators
        elevators.forEach((id, elevator) -> elevator.shutdown());
        elevators.clear();
    }
}
