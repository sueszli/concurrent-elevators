package src;

import java.util.ArrayList;

public class Elevator implements Runnable {

    private static int ID = 0;
    private final ArrayList<Integer> assignedRequests = new ArrayList<Integer>();

    public Elevator() {
        ID++;
    }

    public int getID() {
        return ID;
    }

    public int getNumAssignedRequests() {
        return assignedRequests.size();
    }

    public void assignRequest(int src, int dst) {
        assignedRequests.add(src);
        assignedRequests.add(dst);
    }

    public void shutdown() {
        if (assignedRequests.size() > 0) {
            System.out.println("Elevator " + ID + " still has " + assignedRequests.size() + " assigned requests.");
        }
    }

    @Override
    public void run() {

    }
}
