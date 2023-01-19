A super minimal implementation of a thread-pool-executor service / scheduler in Java. The goal is to provide a simple,
easy to understand implementation. The implementation is not intended to be used in production, but rather to be used as
a learning tool.

<br><br>

## Assignment

- There are 7 elevators in a building with 55 floors.
- Each floor has exactly one button to request an elevator.


- Elevators have the capacity of carrying an arbitrary, given number of people.
- Elevators don't change direction and don't stop mid-trip.


- People can only choose their destination freely, when they are on the ground-floor:
    - up: ground-floor [0] − can only travel to ⟶ non-ground-floor [1;54]
    - down: non-ground-floor [1;54] − can only travel to ⟶ ground-floor [0]


- The requests are handled globally.
- The elevator-scheduler assigns a chosen request to an elevator based on some arbitrary algorithm.
- If there are no elevators available, the request is queued up.

<br><br>

## Design decisions

- Decided to use vanilla Java without any non-default libraries for the sake of simplicity.
- Got rid of the suggested "up"/"down" directions since they can be inferred from the current floor.
- Chose a poison-pill approach to terminate the scheduler (also
  see: [this similar implementation](https://gitlab.com/niklaswimmer/dc-tower-elevator-challange/-/blob/main/app/src/main/java/me/nikx/dctower/TowerController.java))
