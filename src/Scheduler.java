package src;

import java.util.AbstractMap;
import java.util.Comparator;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.IntStream;

import static src.Main.*;

// performance could be improved by using a thread pool and executor as a scheduler, but then we couldn't implement our own custom scheduling algorithm
public class Scheduler implements Runnable {

    private final BlockingQueue<AbstractMap.SimpleEntry<Integer, Integer>> requestQueue = new ArrayBlockingQueue<>(QUEUE_SIZE);
    private final ConcurrentHashMap<Integer, Elevator> elevators = new ConcurrentHashMap<>();
    private boolean alive = false;

    public Scheduler() {
        new Thread(this, "scheduler").start();
    }

    public boolean receiveRequest(int src, int dst) throws InterruptedException {
        if (src < 0 || dst < 0) {
            return false;
        }
        if (src == dst) {
            return false;
        }
        if (src > NUM_FLOORS || dst > NUM_FLOORS) {
            return false;
        }
        if (src != 0 && dst != 0) {
            return false;
        }
        if (requestQueue.size() == QUEUE_SIZE) {
            return false;
        }

        System.out.println("Scheduler: received legal request: " + src + " -> " + dst);
        var request = new AbstractMap.SimpleEntry<>(src, dst);
        this.requestQueue.put(request);
        return true;
    }

    public void start() {
        System.out.println("Scheduler: received start signal");
        alive = true;
    }

    public void shutdown() {
        System.out.println("Received shutdown signal");
        alive = false;
    }

    @Override
    public void run() {
        System.out.println("Scheduler: Initializing elevators...");
        IntStream.range(0, NUM_ELEVATORS).forEach(i -> {
            final var elevator = new Elevator();
            var id = elevator.getID();
            new Thread(elevator, String.format("%02d", id)).start();
            this.elevators.put(id, elevator);
        });

        System.out.println("Scheduler: Starting scheduler loop...");
        while (alive) {
            try {
                var request = this.requestQueue.take();
                var selectedElevator = this.elevators.values().stream().min(Comparator.comparingInt(Elevator::getNumAssignedRequests)).orElseThrow();
                selectedElevator.assign(request);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Scheduler: Shutting down...");
        elevators.values().forEach(Elevator::shutdown);
        elevators.clear();
    }
}
