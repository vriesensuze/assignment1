import java.awt.*;

public abstract class Car implements Movable {
    public enum Direction {
        NORTH, EAST, SOUTH, WEST
    }

    private int nrDoors; // Number of doors on the car
    private double enginePower; // Engine power of the car
    private double currentSpeed; // The current speed of the car 
    private Color color; // Color of the car
    private String modelName; // The car model name

    private double x;
    private double y;
    private Direction direction;

    public Car(int nrDoors, double enginePower, Color color, String modelName) {
        this.nrDoors = nrDoors;
        this.enginePower = enginePower;
        this.color = color;
        this.modelName = modelName;

        this.x = 0;
        this.y = 0;
        this.direction = Direction.NORTH;
        stopEngine();
    }

    public int getNrDoors() {
        return nrDoors;
    }

    public double getEnginePower() {
        return enginePower;
    }

    public double getCurrentSpeed() {
        return currentSpeed;
    }

    public Color getColor() {
        return color;
    }

    public String getModelName() {
        return modelName;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setColor(Color clr) {
        color = clr;
    }

    public void startEngine() {
        currentSpeed = 0.1;
    }

    public void stopEngine() {
        currentSpeed = 0;
    }

    @Override
    public void move() {
        switch (direction) {
            case NORTH:
                y = y + currentSpeed;
                break;
            case EAST:
                x = x + currentSpeed;
                break;
            case SOUTH:
                y = y - currentSpeed;
                break;
            case WEST:
                x = x - currentSpeed;
                break;
        }
    }

    @Override
    public void turnLeft() {
        switch (direction) {
            case NORTH:
                direction = Direction.WEST;
                break;
            case WEST:
                direction = Direction.SOUTH;
                break;
            case SOUTH:
                direction = Direction.EAST;
                break;
            case EAST:
                direction = Direction.NORTH;
                break;
        }
    }

    @Override
    public void turnRight() {
        switch (direction) {
            case NORTH:
                direction = Direction.EAST;
                break;
            case EAST:
                direction = Direction.SOUTH;
                break;
            case SOUTH:
                direction = Direction.WEST;
                break;
            case WEST:
                direction = Direction.NORTH;
                break;
        }
    }

    protected abstract double speedFactor();

    private void incrementSpeed(double amount) {
        double newSpeed = getCurrentSpeed() + speedFactor() * amount;
        currentSpeed = Math.min(newSpeed, enginePower);
    }

    private void decrementSpeed(double amount) {
        double newSpeed = getCurrentSpeed() - speedFactor() * amount;
        currentSpeed = Math.max(newSpeed, 0);
    }

    public void gas(double amount) {
        if (amount < 0 || amount > 1) {
            throw new IllegalArgumentException("gas amount must be between 0 and 1");
        }
        incrementSpeed(amount);
    }
    
    public void brake(double amount) {
        if (amount < 0 || amount > 1) {
            throw new IllegalArgumentException("brake amount must be between 0 and 1");
        }
        decrementSpeed(amount);
    }
}
