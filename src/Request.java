package src;

import static src.Config.NUM_FLOORS;

// could be replaced with Apache or JavaFX `Pair<K,V>` class
public record Request(int src, int dst) {

    public Request {
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
            throw new IllegalArgumentException("Source and destination floors must not be both 0");
        }
    }
}