import java.awt.*;

public class CarTest {
    private static void reportResult(String testName, boolean success) {
        IO.println((success ? "SUCCESS: " : "FAILURE: ") + testName);
    }

    private static boolean almostEqual(double a, double b) {
        return Math.abs(a - b) < 0.0001;
    }

    private static void testTurbo() {
        Saab95 slowSaab = new Saab95();
        Saab95 fastSaab = new Saab95();
        fastSaab.setTurboOn();
        slowSaab.setTurboOff();
        fastSaab.gas(0.5);
        slowSaab.gas(0.5);
        boolean result = slowSaab.getCurrentSpeed() < fastSaab.getCurrentSpeed();
        reportResult("Turbo yields more speed", result);
    }

    private static void testNewCarIsStill() {
        Volvo240 volvo = new Volvo240();
        boolean result = volvo.getCurrentSpeed() == 0;
        reportResult("New car has speed 0", result);
    }

    private static void testStartEngine() {
        Volvo240 volvo = new Volvo240();
        volvo.startEngine();
        boolean result = almostEqual(volvo.getCurrentSpeed(), 0.1);
        reportResult("startEngine sets speed to 0.1", result);
    }

    private static void testStopEngine() {
        Saab95 saab = new Saab95();
        saab.startEngine();
        saab.stopEngine();
        boolean result = saab.getCurrentSpeed() == 0;
        reportResult("stopEngine sets speed to 0", result);
    }

    private static void testDoors() {
        Volvo240 volvo = new Volvo240();
        Saab95 saab = new Saab95();
        boolean result = volvo.getNrDoors() == 4 && saab.getNrDoors() == 2;
        reportResult("Volvo has 4 doors and Saab has 2", result);
    }

    private static void testSetColor() {
        Volvo240 volvo = new Volvo240();
        volvo.setColor(Color.blue);
        boolean result = volvo.getColor().equals(Color.blue);
        reportResult("setColor changes the color", result);
    }

    private static void testMoveWithoutSpeed() {
        Volvo240 volvo = new Volvo240();
        volvo.move();
        boolean result = volvo.getX() == 0 && volvo.getY() == 0;
        reportResult("Car with speed 0 stays in place", result);
    }

    private static void testMoveNorth() {
        Volvo240 volvo = new Volvo240();
        volvo.startEngine();
        volvo.move();
        boolean result = almostEqual(volvo.getY(), 0.1) && volvo.getX() == 0;
        reportResult("Moving north changes only y, by currentSpeed", result);
    }

    private static void testMoveAfterTurn() {
        Saab95 saab = new Saab95();
        saab.startEngine();
        saab.turnRight();
        saab.move();
        boolean result = almostEqual(saab.getX(), 0.1) && saab.getY() == 0;
        reportResult("After turning right, moving changes only x", result);
    }

    private static void testTurnRight() {
        Volvo240 volvo = new Volvo240();
        volvo.turnRight();
        boolean result = volvo.getDirection() == Car.Direction.EAST;
        reportResult("Turning right from north faces east", result);
    }

    private static void testTurnLeft() {
        Volvo240 volvo = new Volvo240();
        volvo.turnLeft();
        boolean result = volvo.getDirection() == Car.Direction.WEST;
        reportResult("Turning left from north faces west", result);
    }

    private static void testFullCircle() {
        Saab95 saab = new Saab95();
        Car.Direction start = saab.getDirection();
        saab.turnRight();
        saab.turnRight();
        saab.turnRight();
        saab.turnRight();
        boolean result = saab.getDirection() == start;
        reportResult("Four right turns gives the same direction", result);
    }

    private static void testLeftThenRight() {
        Volvo240 volvo = new Volvo240();
        Car.Direction start = volvo.getDirection();
        volvo.turnLeft();
        volvo.turnRight();
        boolean result = volvo.getDirection() == start;
        reportResult("Left then right gives the same direction", result);
    }

    private static void testGasTooHigh() {
        Volvo240 volvo = new Volvo240();
        boolean result = false;
        try {
            volvo.gas(1.5);
        } catch (IllegalArgumentException e) {
            result = true;
        }
        reportResult("gas(1.5) is not accepted", result);
    }

    private static void testGasNegative() {
        Volvo240 volvo = new Volvo240();
        boolean result = false;
        try {
            volvo.gas(-0.5);
        } catch (IllegalArgumentException e) {
            result = true;
        }
        reportResult("gas(-0.5) is not accepted", result);
    }

    private static void testBrakeTooHigh() {
        Saab95 saab = new Saab95();
        boolean result = false;
        try {
            saab.brake(1.5);
        } catch (IllegalArgumentException e) {
            result = true;
        }
        reportResult("brake(1.5) is not accepted", result);
    }

    private static void testBrakeNegative() {
        Saab95 saab = new Saab95();
        boolean result = false;
        try {
            saab.brake(-0.5);
        } catch (IllegalArgumentException e) {
            result = true;
        }
        reportResult("brake(-0.5) is not accepted", result);
    }

    private static void testMaxSpeed() {
        Saab95 saab = new Saab95();
        saab.setTurboOn();
        saab.startEngine();
        for (int i = 0; i < 1000; i++) {
            saab.gas(1);
        }
        boolean result = saab.getCurrentSpeed() <= saab.getEnginePower();
        reportResult("Speed never goes above enginePower", result);
    }

    private static void testMinSpeed() {
        Volvo240 volvo = new Volvo240();
        volvo.startEngine();
        for (int i = 0; i < 1000; i++) {
            volvo.brake(1);
        }
        boolean result = volvo.getCurrentSpeed() >= 0;
        reportResult("Speed never goes below 0", result);
    }

    private static void testGasDoesNotDecrease() {
        Volvo240 volvo = new Volvo240();
        volvo.startEngine();
        double before = volvo.getCurrentSpeed();
        volvo.gas(0.5);
        boolean result = volvo.getCurrentSpeed() >= before;
        reportResult("gas does not decrease speed", result);
    }

    private static void testBrakeDoesNotIncrease() {
        Saab95 saab = new Saab95();
        saab.startEngine();
        saab.gas(1);
        double before = saab.getCurrentSpeed();
        saab.brake(0.5);
        boolean result = saab.getCurrentSpeed() <= before;
        reportResult("brake does not increase speed", result);
    }

    static void main() {
        testTurbo();
        testNewCarIsStill();
        testStartEngine();
        testStopEngine();
        testDoors();
        testSetColor();
        testMoveWithoutSpeed();
        testMoveNorth();
        testMoveAfterTurn();
        testTurnRight();
        testTurnLeft();
        testFullCircle();
        testLeftThenRight();
        testGasTooHigh();
        testGasNegative();
        testBrakeTooHigh();
        testBrakeNegative();
        testMaxSpeed();
        testMinSpeed();
        testGasDoesNotDecrease();
        testBrakeDoesNotIncrease();
    }
}
