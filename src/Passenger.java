class Passenger {
    private String name;
    private int fromFloor;
    private int toFloor;

    public Passenger(String name, int fromFloor, int toFloor) {
        this.name = name;
        this.fromFloor = fromFloor;
        this.toFloor = toFloor;
    }

    public String getName() {
        return name;
    }

    public int getFromFloor() {
        return fromFloor;
    }

    public int getToFloor() {
        return toFloor;
    }
}