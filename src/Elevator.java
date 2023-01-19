package src;

import java.util.AbstractMap;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import static src.Main.QUEUE_SIZE;

public class Elevator implements Runnable {

    private static int ID = 0;
    private final BlockingQueue<AbstractMap.SimpleEntry<Integer, Integer>> assignedRequests = new ArrayBlockingQueue<>(QUEUE_SIZE);
    private boolean alive = true;

    public Elevator() {
        ID++;
    }

    public int getID() {
        return ID;
    }

    public void assign(AbstractMap.SimpleEntry<Integer, Integer> request) throws InterruptedException {
        System.out.println("Elevator " + this.getID() + ": received request: " + request.getKey() + " -> " + request.getValue());
        assignedRequests.put(request);
    }

    public int getNumAssignedRequests() {
        return assignedRequests.size();
    }

    public void shutdown() {
        System.out.println("Elevator " + this.getID() + ": received shutdown signal");
        alive = false;
    }

    private void simulateMovement(int src, int dst) {
        System.out.println("Elevator " + this.getID() + ": moving from " + src + " to " + dst);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (alive) {
            try {
                var request = assignedRequests.take();
                simulateMovement(request.getKey(), request.getValue());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        if (assignedRequests.size() > 0) {
            System.out.println("Elevator " + ID + ": " + assignedRequests.size() + " requests left unfulfilled");
        }
        assignedRequests.clear();
    }
}
