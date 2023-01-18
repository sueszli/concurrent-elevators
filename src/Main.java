package src;

import java.util.ArrayList;
import java.util.stream.IntStream;

public class Main {

    public static int NUMBER_OF_FLOORS = 55;
    public static int NUMBER_OF_ELEVATORS = 7;
    public static int ELEVATOR_CAPACITY = 10;

    private static ArrayList<Integer>[] initializeFloors(int n) {
        ArrayList<Integer>[] floors = new ArrayList[NUMBER_OF_FLOORS];

        IntStream.range(0, NUMBER_OF_FLOORS).forEach(i -> {
            floors[i] = new ArrayList<>();
            var numRequests = (int) (Math.random() * n);

            IntStream.range(0, numRequests).forEach(j -> {
                boolean notGroundFloor = i != 0;
                var destination = notGroundFloor ? 0 : (int) (Math.random() * (NUMBER_OF_FLOORS - 1) + 1);
                floors[i].add(destination);
            });
        });
        return floors;
    }

    private static void printFloors(ArrayList<Integer>[] floors) {
        IntStream.range(0, NUMBER_OF_FLOORS).forEach(i -> System.out.println("Floor " + i + ": " + floors[i]));
    }

    public static void main(String[] args) {

        // floors = request queue (people waiting for elevator)
        // requests = destination floor numbers
        ArrayList<Integer>[] floors = initializeFloors(5);
        printFloors(floors);
    }

}
