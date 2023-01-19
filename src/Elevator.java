package src;

import java.util.AbstractMap;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import static src.Main.PROCESSING_DELAY;
import static src.Main.QUEUE_SIZE;

public class Elevator implements Runnable {

    private static int ID_COUNTER = 0;
    private final int id;
    private final BlockingQueue<AbstractMap.SimpleEntry<Integer, Integer>> assignedRequests = new ArrayBlockingQueue<>(QUEUE_SIZE);

    public Elevator() {
        this.id = ID_COUNTER++;
    }

    public int getID() {
        return id;
    }

    public void assign(AbstractMap.SimpleEntry<Integer, Integer> request) throws InterruptedException {
        assignedRequests.put(request);
    }

    public int getNumAssignedRequests() {
        return assignedRequests.size();
    }

    private void simulateMovement(int src, int dst) throws InterruptedException {
        Thread.sleep(PROCESSING_DELAY);
        System.out.println("Elevator " + this.getID() + ": resolved [" + src + " -> " + dst + "]");
    }

    public void shutdown() {
        var poison_pill = new AbstractMap.SimpleEntry<>(-1, -1);
        try {
            this.assignedRequests.put(poison_pill);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                var request = assignedRequests.take();
                boolean isPoisonPill = request.getKey() == -1 && request.getValue() == -1;
                if (isPoisonPill) {
                    break;
                }
                simulateMovement(request.getKey(), request.getValue());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        System.out.println("Elevator " + this.getID() + ": shut down successfully");
        assignedRequests.clear();
    }
}
