package src;

import static src.Main.*;

import java.util.Comparator;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.IntStream;

public class Scheduler implements Runnable {

    public record Request(int src, int dst) {}

    public static Request POISON_PILL = new Request(-1, -1);

    private final BlockingQueue<Request> requestQueue = new ArrayBlockingQueue<>(QUEUE_SIZE);
    private final ConcurrentHashMap<Integer, Elevator> elevators = new ConcurrentHashMap<>();

    public boolean receiveRequest(Request request) throws InterruptedException {
        var negativeFloor = request.src() < 0 || request.dst() < 0;
        var sameFloor = request.src() == request.dst();
        var invalidFloor = request.src() > NUM_FLOORS || request.dst() > NUM_FLOORS;
        var bothGroundFloor = request.src() != 0 && request.dst() != 0;
        var queueFull = requestQueue.size() == QUEUE_SIZE;
        if (negativeFloor || sameFloor || invalidFloor || bothGroundFloor || queueFull) {
            return false;
        }

        System.out.println("Scheduler: received [" + request.src + " -> " + request.dst + "]");
        this.requestQueue.put(request);
        return true;
    }

    public void start() {
        new Thread(this, "scheduler").start();
    }

    public void shutdown() throws InterruptedException {
        this.requestQueue.put(POISON_PILL);
    }

    @Override
    public void run() {
        System.out.println("Scheduler: Initializing elevators...");
        IntStream
            .range(0, NUM_ELEVATORS)
            .forEach(i -> {
                final var elevator = new Elevator();
                var id = elevator.getID();
                new Thread(elevator, String.format("%02d", id)).start();
                this.elevators.put(id, elevator);
            });

        System.out.println("Scheduler: Starting scheduler loop...");
        while (true) {
            try {
                var request = this.requestQueue.take();
                if (request.equals(POISON_PILL)) {
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
        System.out.println("Scheduler: shut down successfully");
    }
}
