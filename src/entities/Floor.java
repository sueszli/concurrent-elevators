package src.entities;

import java.util.ArrayList;

public class Floor {
    private final ArrayList<Request> REQUEST_QUEUE = new ArrayList<Request>();

    public void addRequest(Request request) {
        REQUEST_QUEUE.add(request);

    }

    public Request getRequest() {
        return (REQUEST_QUEUE.size() > 0) ? REQUEST_QUEUE.remove(0) : null;
    }

    @Override
    public String toString() {
        return REQUEST_QUEUE.toString();
    }
}
