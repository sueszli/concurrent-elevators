package src;

import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.stream.IntStream;

public class Main {

    public static int NUMBER_OF_FLOORS = 55;
    public static int NUMBER_OF_ELEVATORS = 7;
    public static int ELEVATOR_CAPACITY = 10;

    public static int QUEUE_CAPACITY = 10;

    private static ArrayBlockingQueue<Integer>[] initializeFloors() {
        ArrayBlockingQueue<Integer>[] floors = new ArrayBlockingQueue[NUMBER_OF_FLOORS];

        IntStream.range(0, NUMBER_OF_FLOORS).forEach(i -> {
            floors[i] = new ArrayBlockingQueue<>(QUEUE_CAPACITY);
            var numRequests = (int) (Math.random() * QUEUE_CAPACITY);

            IntStream.range(0, numRequests).forEach(j -> {
                boolean notGroundFloor = i != 0;
                var destination = notGroundFloor ? 0 : (int) (Math.random() * (NUMBER_OF_FLOORS - 1) + 1);
                floors[i].offer(destination);
            });
        });
        return floors;
    }

    private static void printFloors(ArrayBlockingQueue<Integer>[] floors) {
        IntStream.range(0, floors.length).forEach(i -> System.out.println("Floor " + i + ": " + floors[i]));
    }

    public static void main(String[] args) {

        // floor = request queu
        // request = destination floor number of the person waiting
        // ArrayBlockingQueue<Integer>[] floors = initializeFloors();
        // printFloors(floors);


    }

}
