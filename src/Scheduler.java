package src;

import java.util.AbstractMap;
import java.util.Comparator;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.IntStream;

import static src.Main.*;

public class Scheduler implements Runnable {

    private final BlockingQueue<AbstractMap.SimpleEntry<Integer, Integer>> requestQueue = new ArrayBlockingQueue<>(QUEUE_SIZE);
    private final ConcurrentHashMap<Integer, Elevator> elevators = new ConcurrentHashMap<>();

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

        System.out.println("Scheduler: received [" + src + " -> " + dst + "]");
        var request = new AbstractMap.SimpleEntry<>(src, dst);
        this.requestQueue.put(request);
        return true;
    }

    public void start() {
        new Thread(this, "scheduler").start();
    }

    public void shutdown() throws InterruptedException {
        var poison_pill = new AbstractMap.SimpleEntry<>(-1, -1);
        this.requestQueue.put(poison_pill);
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
        while (true) {
            try {
                var request = this.requestQueue.take();
                boolean isPoisonPill = request.getKey() == -1 && request.getValue() == -1;
                if (isPoisonPill) {
                    break;
                }
                var selectedElevator = this.elevators.values().stream().min(Comparator.comparingInt(Elevator::getNumAssignedRequests)).orElseThrow();
                selectedElevator.assign(request);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Scheduler: shutting down...");
        elevators.values().forEach(Elevator::shutdown);
        elevators.clear();
        requestQueue.clear();
        System.out.println("Scheduler: Shut down successfully");
    }
}
