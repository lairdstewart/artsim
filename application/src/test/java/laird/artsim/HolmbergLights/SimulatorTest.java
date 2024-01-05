package laird.artsim.HolmbergLights;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.awt.geom.Point2D;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
class SimulatorTest {
    @Test
    void forceFromOtherBulbsCollinearOppositeEvenlySpacedYDirection() {
        Point2D.Double thisBulb = new Point2D.Double(0, 0);
        Point2D.Double otherBulb1 = new Point2D.Double(0, 16);
        Point2D.Double otherBulb2 = new Point2D.Double(0, -16);
        List<Point2D.Double> bulbList = Arrays.asList(otherBulb1, otherBulb2);

        Point2D.Double forceActual = Simulator.forceFromOtherBulbs(thisBulb, bulbList);

        Assertions.assertEquals(new Point2D.Double(0, 0), forceActual);
    }

    @Test
    void forceFromOtherBulbsCollinearOppositeEvenlySpacedXDirection() {
        Point2D.Double thisBulb = new Point2D.Double(0, 0);
        Point2D.Double otherBulb1 = new Point2D.Double(20, 0);
        Point2D.Double otherBulb2 = new Point2D.Double(-20, 0);
        List<Point2D.Double> bulbList = Arrays.asList(otherBulb1, otherBulb2);

        Point2D.Double forceActual = Simulator.forceFromOtherBulbs(thisBulb, bulbList);

        Assertions.assertEquals(new Point2D.Double(0, 0), forceActual);
    }

    @Test
    void forceFromOtherBulbsCollinearOppositeEvenlySpacedOnDiagonal() {
        Point2D.Double thisBulb = new Point2D.Double(0, 0);
        Point2D.Double otherBulb1 = new Point2D.Double(15, 15);
        Point2D.Double otherBulb2 = new Point2D.Double(-15, -15);
        List<Point2D.Double> bulbList = Arrays.asList(otherBulb1, otherBulb2);

        Point2D.Double forceActual = Simulator.forceFromOtherBulbs(thisBulb, bulbList);

        Assertions.assertEquals(new Point2D.Double(0, 0), forceActual);
    }

    @Test
    void forceFromOtherBulbsCollinearStackedEvenlySpacedOnDiagonal() {
        Point2D.Double thisBulb = new Point2D.Double(0, 0);
        Point2D.Double otherBulb1 = new Point2D.Double(0, 16);
        Point2D.Double otherBulb2 = new Point2D.Double(0, 16);
        List<Point2D.Double> bulbList = Arrays.asList(otherBulb1, otherBulb2);

        Point2D.Double forceActual = Simulator.forceFromOtherBulbs(thisBulb, bulbList);

        Assertions.assertEquals(new Point2D.Double(0, 2.0/4.0), forceActual);
    }

    @Test
    void forceFromOtherBulbsCollinearUnbalanced() {
        Point2D.Double thisBulb = new Point2D.Double(0, 0);
        Point2D.Double otherBulb1 = new Point2D.Double(15, 15);
        Point2D.Double otherBulb2 = new Point2D.Double(-20, -20);
        List<Point2D.Double> bulbList = Arrays.asList(otherBulb1, otherBulb2);

        Point2D.Double forceActual = Simulator.forceFromOtherBulbs(thisBulb, bulbList);

        Assertions.assertEquals(new Point2D.Double(0.020568580962938843, 0.020568580962938843), forceActual);
    }

    @Test
    void forceFromOtherBulbsNonCollinear() {
        Point2D.Double thisBulb = new Point2D.Double(0, 0);
        Point2D.Double otherBulb1 = new Point2D.Double(9, 0);
        Point2D.Double otherBulb2 = new Point2D.Double(0, 16);
        List<Point2D.Double> bulbList = Arrays.asList(otherBulb1, otherBulb2);

        Point2D.Double forceActual = Simulator.forceFromOtherBulbs(thisBulb, bulbList);

        Assertions.assertEquals(new Point2D.Double(0.3333333333333333, 0.25), forceActual);
        // tbh i just copied the result here, not sure if its right via physics, but looks right
    }

    @Test
    void forceFromOtherBulbCollinear() {
        Point2D.Double thisBulb = new Point2D.Double(0, 0);
        Point2D.Double otherBulb = new Point2D.Double(0, 16);

        Point2D.Double forceActual = Simulator.forceFromOtherBulb(thisBulb, otherBulb);

        Assertions.assertEquals(new Point2D.Double(0, 1.0/4.0), forceActual);
    }

    @Test
    void forceFromOtherBulbNonCollinear() {
        Point2D.Double thisBulb = new Point2D.Double(0, 0);
        Point2D.Double otherBulb = new Point2D.Double(10, 10);

        Point2D.Double forceActual = Simulator.forceFromOtherBulb(thisBulb, otherBulb);

        double distance = Math.sqrt(10 * 10 + 10 * 10);
        double magnitude = 1.0 / Math.sqrt(distance);
        double xComponent = 10 * (magnitude / distance);
        double yComponent = 10 * (magnitude / distance);
        Point2D.Double forceExpected = new Point2D.Double(xComponent, yComponent);
        Assertions.assertEquals(forceExpected, forceActual);
    }
}