package src.entities;

import static src.Config.NUMBER_OF_FLOORS;

/**
 * A request is a person waiting for an elevator to take them to a destination floor.
 * Elevators resolve Requests and remove them from the Floor queue.
 * <p>
 * Remark: could be replaced with the apache common `Pair<Integer, Integer>`.
 */
public class Request {

    private final int srcFloor;
    private final int dstFloor;

    public Request(int srcFloor, int dstFloor) {
        if (srcFloor < 0 || srcFloor >= NUMBER_OF_FLOORS) {
            throw new IllegalArgumentException("Invalid source floor: " + srcFloor);
        }
        if (dstFloor < 0 || dstFloor >= NUMBER_OF_FLOORS) {
            throw new IllegalArgumentException("Invalid destination floor: " + dstFloor);
        }
        if (srcFloor == dstFloor) {
            throw new IllegalArgumentException("Source and destination floors are the same: " + srcFloor);
        }
        if (srcFloor != 0 && dstFloor != 0) {
            throw new IllegalArgumentException("Illegal destination for non-ground-floor (not ground-floor): " + dstFloor);
        }

        this.srcFloor = srcFloor;
        this.dstFloor = dstFloor;
    }

    public int getSrcFloor() {
        return srcFloor;
    }

    public int getDstFloor() {
        return dstFloor;
    }

    @Override
    public String toString() {
        return "{ " + "src=" + srcFloor + ", dst=" + dstFloor + " }";
    }
}
