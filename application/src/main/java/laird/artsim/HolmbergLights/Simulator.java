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

    public List<Node> runSimulation(List<Bulb> initialState) {
        List<Bulb> previousState = initialState;
        List<Node> result = new ArrayList<>();
        result.add(bulbsToNode(initialState));

        List<List<Bulb>> states = new ArrayList<>();
        states.add(initialState);

        for (int i = 0; i < numSteps; i++) {
            List<Bulb> nextState = nextState(previousState);
            states.add(nextState);
            result.add(bulbsToNode(nextState));
            previousState = nextState;
        }

        return result;
    }

    public Node bulbsToNode(List<Bulb> bulbs) {
        List<Circle> circles = new ArrayList<>();
        for (Bulb bulb : bulbs) {
            circles.add(new Circle(bulb.position.x, bulb.position.y, bulb.radius));
        }
        Pane result = new Pane();
        result.getChildren().addAll(circles);
        return result;
    }

    public List<Bulb> nextState(List<Bulb> bulbs) {
        List<Bulb> result = new ArrayList<>();
        for (int i = 0; i < bulbs.size(); i++) {
            Bulb bulb = bulbs.get(i);

            // combine bulbs if close together
            List<Bulb> overlappingBulbs = new ArrayList<>();
            for (int j = 0; j < bulbs.size(); j++) {
                if (i == j) {
                    continue;
                }
                Bulb otherBulb = bulbs.get(j);
                if (bulb.overlapsAlot(otherBulb)) {
                    overlappingBulbs.add(otherBulb);
                }
            }

            if (overlappingBulbs.size() > 0) {
                bulb.combineWith(overlappingBulbs);
                bulbs.removeAll(overlappingBulbs);
                result.add(bulb);
                continue;
            }

            // otherwise, update this bulb (pos, vel) based on force from others
            double xComponent = 0;
            double yComponent = 0;
            for (int j = 0; j < bulbs.size(); j++) {
                if (i == j) {
                    continue;
                }
                Bulb otherBulb = bulbs.get(j);
                Point.Double force = forceFromOtherBulb(bulb, otherBulb);
                xComponent += force.x;
                yComponent += force.y;
            }

            Point2D.Double force = new Point.Double(xComponent, yComponent);
            Bulb updatedBulb = applyForce(bulb, force);
            result.add(updatedBulb);
        }

        return result;
    }

    public Bulb applyForce(Bulb bulb, Point2D.Double force) {
        double dx = force.x * gravityConstant;
        double dy = force.y * gravityConstant;
        Bulb newBulb = new Bulb(bulb);
        newBulb.setPosition(new Point2D.Double(bulb.position.x + dx, bulb.position.y + dy));
        return newBulb;
    }

    public static Point.Double forceFromOtherBulbs(Bulb lightBulb, List<Bulb> otherBulbs) {
        double xComponent = 0;
        double yComponent = 0;
        for (Bulb other : otherBulbs) {
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
    public static Point.Double forceFromOtherBulb(Bulb lightBulb, Bulb otherBulb) {
        double dx = otherBulb.position.x - lightBulb.position.x;
        double dy = otherBulb.position.y - lightBulb.position.y;
        double distance = Math.sqrt(dx*dx + dy*dy);

//        double magnitude;
//        if (Math.sqrt(lightBulb.distance(otherBulb)) < 0.000001) {
//            magnitude = 1 / 0.0001;
//        } else {
//            magnitude = 1 / Math.sqrt(lightBulb.distance(otherBulb));
//        }

        double magnitude = 1 / Math.sqrt(distance);
//        if (magnitude > 100) {
//            magnitude = 0;
//        }


        double forceX = (magnitude/distance)*dx;
        double forceY = (magnitude/distance)*dy;
        return new Point2D.Double(forceX, forceY);
    }
}
