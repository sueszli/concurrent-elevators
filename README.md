# Assignment

- There are 7 elevators in a building with 55 floors.
- Each floor has exactly one button to request an elevator.


- Elevators have the capacity of carrying an arbitrary number of people.
- Elevators don't change direction mid-trip but can stop to pick up people.
- People can only choose their destination from the ground-floor:
    - up: ground-floor [0] − can only travel to ⟶ non-ground-floor [1;54]
    - down: non-ground-floor [1;54] − can only travel to ⟶ ground-floor [0]


- The requests are handled globally.
- The elevator-scheduler assigns a random elevator to the most recent request.
- If there are no elevators available, the request is queued up.

# Design decisions

- I decided to use vanilla Java without any non-default libraries for the sake of simplicity.
- To create as few entities as possible and avoid boilerplate OOP code with getters and setters, I used existing data
  structures like "Pair" instead of creating my own classes.
- I got rid of the suggested "up"/"down" directions since they can be inferred from the current floor.
- I chose a FCFS (First-Come-First-Served) algorithm for the elevator-scheduler.