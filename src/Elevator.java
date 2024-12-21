import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

class Elevator implements Runnable {
    private int currentFloor;
    private LinkedBlockingQueue<Passenger> passengers;
    private boolean running;
    private ElevatorSystemGUI gui;

    public Elevator(ElevatorSystemGUI gui) {
        this.currentFloor = 1; // Начальный этаж
        this.passengers = new LinkedBlockingQueue<>();
        this.running = true;
        this.gui = gui;
    }

    public void callElevator(int floor) {
        moveToFloor(floor);
    }

    public void moveToFloor(int floor) {
        while (currentFloor != floor) {
            if (currentFloor < floor) {
                currentFloor++;
            } else {
                currentFloor--;
            }
            gui.updateElevatorPosition(this);
            try {
                Thread.sleep(1000); // Задержка для имитации движения
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        unloadPassengers();
    }

    public void unloadPassengers() {
        List<Passenger> toUnload = new ArrayList<>();
        for (Passenger passenger : passengers) {
            if (passenger.getToFloor() == currentFloor) {
                System.out.println("Пассажир " + passenger.getName() + " вышел на этаже " + currentFloor);
                toUnload.add(passenger);
            }
        }
        passengers.removeAll(toUnload);
    }

    public void loadPassenger(Passenger passenger) {
        if (!passengers.contains(passenger)) {
            passengers.add(passenger);
            System.out.println("Пассажир " + passenger.getName() + " зашёл на этаже " + currentFloor);
        }
    }


    @Override
    public void run() {
        while (running) {
            try {
                Passenger passenger = passengers.take(); // Ожидание пассажира
                moveToFloor(passenger.getFromFloor());
                loadPassenger(passenger);
                moveToFloor(passenger.getToFloor());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public int getCurrentFloor() {
        return currentFloor;
    }
}
