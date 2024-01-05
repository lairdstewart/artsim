package laird.artsim.HolmbergLights;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

/*
f=ma
f ~ 1/(r^2)
all that force turns into kinetic energy?
ke = 1/2 mv^2.
how to translate force into velocity?
sum gravity into potential energy, translate into kinetic

sum(1/r^2) = v^2
v = root(sum(1/r^2)), but what about direction?

1. Calculate list of forces. Force has a magnitude and direction.
2. Combine forces into a single force
3. move a (proportional) step in the direction of that force. (this is all unit-less)
4. update

 */
public class Simulator {

    private double gravityConstant;
    private int numSteps;
    private double bulbRadius;

    public Simulator(double gravityConstant, int numSteps, double bulbRadius) {
        this.gravityConstant = gravityConstant;
        this.numSteps = numSteps;
        this.bulbRadius = bulbRadius;
    }

    public List<Node> runSimulation(List<Point2D.Double> initialState) {
        List<Point2D.Double> previousState = initialState;
        List<Node> result = new ArrayList<>();
        result.add(stateToNode(initialState));

        List<List<Point2D.Double>> states = new ArrayList<>();
        states.add(initialState);

        for (int i = 0; i < numSteps; i++) {
            List<Point2D.Double> nextState = nextState(previousState);
            states.add(nextState);
            result.add(stateToNode(nextState));
            previousState = nextState;
        }

        return result;
    }

    public Node stateToNode(List<Point2D.Double> states) {
        List<Circle> circles = new ArrayList<>();
        for (Point2D.Double state : states) {
            circles.add(new Circle(state.x, state.y, bulbRadius));
        }
        Pane result = new Pane();
        result.getChildren().addAll(circles);
        return result;
    }

    public List<Point2D.Double> nextState(List<Point2D.Double>bulbs) {
        List<Point2D.Double> result = new ArrayList<>();
        for (int i = 0; i < bulbs.size(); i++) {
            Point2D.Double bulb = bulbs.get(i);

            double xComponent = 0;
            double yComponent = 0;
            for (int j = 0; j < bulbs.size(); j++) {
                if (i == j) {
                    continue;
                }
                Point2D.Double otherBulb = bulbs.get(j);
                Point.Double force = forceFromOtherBulb(bulb, otherBulb);
                xComponent += force.x;
                yComponent += force.y;
            }

            Point2D.Double force = new Point.Double(xComponent, yComponent);
            Point2D.Double updatedBulb = applyForce(bulb, force);
            result.add(updatedBulb);
        }

        return result;
    }

    public Point2D.Double applyForce(Point2D.Double point, Point2D.Double force) {
        double dx = force.x * gravityConstant;
        double dy = force.y * gravityConstant;
        return new Point2D.Double(point.x + dx, point.y + dy);
    }

    public static Point.Double forceFromOtherBulbs(Point.Double lightBulb, List<Point.Double> otherLightBulbs) {
        double xComponent = 0;
        double yComponent = 0;
        for (Point.Double other : otherLightBulbs) {
            Point.Double force = forceFromOtherBulb(lightBulb, other);
            xComponent += force.x;
            yComponent += force.y;
        }
        return new Point.Double(xComponent, yComponent);
    }

    /*
    I'm assuming the proportion of the magnitude to the hypotenuse is the same as the unkown x to dx, not sure if that
    is geometrically accurate.
     */
    public static Point.Double forceFromOtherBulb(Point.Double lightBulb, Point.Double otherBulb) {
        double dx = otherBulb.x - lightBulb.x;
        double dy = otherBulb.y - lightBulb.y;
        double hypotenuse = Math.sqrt(dx*dx + dy*dy);

//        double magnitude;
//        if (Math.sqrt(lightBulb.distance(otherBulb)) < 0.000001) {
//            magnitude = 1 / 0.0001;
//        } else {
//            magnitude = 1 / Math.sqrt(lightBulb.distance(otherBulb));
//        }

        double magnitude = 1 / Math.sqrt(lightBulb.distance(otherBulb));
//        if (magnitude > 100) {
//            magnitude = 0;
//        }


        double forceX = (magnitude/hypotenuse)*dx;
        double forceY = (magnitude/hypotenuse)*dy;
        return new Point2D.Double(forceX, forceY);
    }
}
