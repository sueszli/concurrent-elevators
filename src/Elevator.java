package src;

import static src.Main.PROCESSING_DELAY;
import static src.Main.QUEUE_SIZE;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import src.Scheduler.Request;

public class Elevator implements Runnable {

    private static int ID_COUNTER = 0;
    private final int id;
    private final BlockingQueue<Request> assignedRequests = new ArrayBlockingQueue<>(QUEUE_SIZE);

    public Elevator() {
        this.id = ID_COUNTER++;
    }

    public int getID() {
        return id;
    }

    public void assign(Request request) throws InterruptedException {
        assignedRequests.put(request);
    }

    public int getNumAssignedRequests() {
        return assignedRequests.size();
    }

    private void simulateMovement(Request request) throws InterruptedException {
        Thread.sleep(PROCESSING_DELAY);
        System.out.println("Elevator " + this.getID() + ": resolved [" + request.src() + " -> " + request.dst() + "]");
    }

    public void shutdown() {
        try {
            this.assignedRequests.put(Scheduler.POISON_PILL);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                var request = assignedRequests.take();
                if (request.equals(Scheduler.POISON_PILL)) {
                    break;
                }
                simulateMovement(request);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        System.out.println("Elevator " + this.getID() + ": shut down successfully");
        assignedRequests.clear();
    }
}
