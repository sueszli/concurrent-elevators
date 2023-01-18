package src;

public class Elevator implements Runnable {

    private static int ID = 0;

    public Elevator() {
        ID++;
    }

    public int getID() {
        return ID;
    }

    @Override
    public void run() {

    }

    public void shutdown() {

    }
}
