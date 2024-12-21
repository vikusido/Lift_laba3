import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class Building {
    List<Elevator> elevators;
    private ExecutorService executorService;

    public Building(int numberOfElevators, ElevatorSystemGUI gui) {
        elevators = new ArrayList<>();
        executorService = Executors.newFixedThreadPool(numberOfElevators);
        for (int i = 0; i < numberOfElevators; i++) {
            Elevator elevator = new Elevator(gui);
            elevators.add(elevator);
            executorService.submit(elevator);
        }
    }


    private Elevator findNearestElevator(int floor) {
        Elevator nearestElevator = elevators.get(0);
        int minDistance = Math.abs(nearestElevator.getCurrentFloor() - floor);

        for (Elevator elevator : elevators) {
            int distance = Math.abs(elevator.getCurrentFloor() - floor);
            if (distance < minDistance) {
                minDistance = distance;
                nearestElevator = elevator;
            }
        }

        return nearestElevator;
    }

    public void addPassenger(Passenger passenger) {
        // Добавление пассажира в лифт
        Elevator elevator = findNearestElevator(passenger.getFromFloor());
        elevator.callElevator(passenger.getFromFloor());
        elevator.loadPassenger(passenger);
    }

}
