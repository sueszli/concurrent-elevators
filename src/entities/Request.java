package src.entities;

import java.util.UUID;

import static src.Config.NUMBER_OF_FLOORS;

public class Request {

    private final UUID id;
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

        this.id = UUID.randomUUID();
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
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj.getClass() != getClass()) {
            return false;
        }
        Request rhs = (Request) obj;
        return this.id.equals(rhs.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "{ " + "src=" + srcFloor + ", dst=" + dstFloor + " }";
    }
}
