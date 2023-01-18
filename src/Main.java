package src;

import src.entities.Floor;
import src.entities.Request;

import java.util.stream.IntStream;

import static src.Config.NUMBER_OF_FLOORS;

public class Main {

    private static void printFloors(Floor[] floors) {
        IntStream.range(0, floors.length).forEach(i -> System.out.println(i + ": " + floors[i]));
    }

    private static Request generateRequest(int src) {
        if (src == 0) {
            return new Request(0, (int) (Math.random() * (NUMBER_OF_FLOORS - 1) + 1));
        } else {
            return new Request(src, 0);
        }
    }

    public static void main(String[] args) {

        // initialize floors with random requests
        var floors = new Floor[NUMBER_OF_FLOORS];
        var maxRequests = 5;
        IntStream.range(0, NUMBER_OF_FLOORS).forEach(i -> {
            floors[i] = new Floor();
            var numRequests = (int) (Math.random() * maxRequests);
            IntStream.range(0, numRequests).forEach(j -> floors[i].addRequest(generateRequest(i)));
        });
        printFloors(floors);

    }
}
