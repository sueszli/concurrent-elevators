package src;

import java.util.AbstractMap;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.IntStream;

import static src.Main.*;

// performance could be improved by using a thread pool and increasing the granularity of the locks
public class Scheduler implements Runnable {

    private final BlockingQueue<AbstractMap.SimpleEntry<Integer, Integer>> requestQueue = new ArrayBlockingQueue<>(QUEUE_SIZE);
    private final ConcurrentHashMap<Integer, Elevator> elevators = new ConcurrentHashMap<>();
    private boolean alive = true;

    public Scheduler() {
        new Thread(this, "scheduler").start();
    }

    public boolean assignRequest(int src, int dst) {
        if (src < 0 || dst < 0) {
            throw new IllegalArgumentException("Floor numbers must be positive");
        }
        if (src == dst) {
            throw new IllegalArgumentException("Source and destination floors must be different");
        }
        if (src > NUM_FLOORS || dst > NUM_FLOORS) {
            throw new IllegalArgumentException("Floor numbers must be less than " + NUM_FLOORS);
        }
        if (src != 0 && dst != 0) {
            throw new IllegalArgumentException("Only the ground-floor can choose any arbitrary floor as destination");
        }
        var request = new AbstractMap.SimpleEntry<>(src, dst);
        return this.requestQueue.offer(request);
    }

    public void shutdown() {
        alive = false;
    }

    @Override
    public void run() {
        System.out.println("Initializing elevators...");
        IntStream.range(0, NUM_ELEVATORS).forEach(i -> {
            final var elevator = new Elevator();
            var id = elevator.getID();
            new Thread(elevator, String.format("%02d", id)).start();
            this.elevators.put(id, elevator);
        });

        System.out.println("Starting scheduler loop...");
        while (alive) {
            try {
                var r = this.requestQueue.take();
                // find min queue size
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Cleaning up elevators...");
        elevators.values().forEach(Elevator::shutdown);
        elevators.clear();
    }
}
