package laird.artsim.HolmbergLights;

import java.awt.geom.Point2D;
import java.util.List;

public class Bulb {
    Point2D.Double position;
    Point2D.Double velocity;
    double mass;
    double radius;

    public Bulb(Point2D.Double position, Point2D.Double velocity, double mass, double radius) {
        this.position = position;
        this.velocity = velocity;
        this.mass = mass;
        this.radius = radius;
    }

    public Bulb(Bulb other) {
        this.position = (Point2D.Double) other.position.clone();
        this.velocity = (Point2D.Double) other.velocity.clone();
        this.mass = other.mass;
        this.radius = other.radius;
    }

    public static Bulb combine(Bulb bulb1, Bulb bulb2) {
        double mass = bulb1.mass + bulb2.mass;
        double radius = bulb1.radius + bulb1.radius; // Todo wrong

        /*
        area = pi*r^2
        area should be area + area

        new area = pi*r^2 + pi*r^2
        pi*newR^2 = pi*r1^2 + pi*r2^2
        newR^2 = r1^2 + r2^2
         */

        double xPosition = (bulb1.position.x + bulb2.position.x) / 2.0;
        double yPosition = (bulb1.position.y + bulb2.position.y) / 2.0;
        double xVelocity = (bulb1.velocity.x + bulb2.velocity.x) / 2.0;
        double yVelocity = (bulb1.velocity.y + bulb2.velocity.y) / 2.0;
        Point2D.Double position = new Point2D.Double(xPosition, yPosition);
        Point2D.Double velocity = new Point2D.Double(xVelocity, yVelocity);
        return new Bulb(position, velocity, mass, radius);
    }

    public void combineWith(List<Bulb> bulbs) {
        if (bulbs.isEmpty()) {
            return;
        }

        double massSum = this.mass;
        double radiusSum = this.radius;
        double xPositionSum = this.position.x;
        double yPositionSum = this.position.y;
        double xVelocitySum = this.velocity.x;
        double yVelocitySum = this.velocity.y;

        for (Bulb bulb : bulbs) {
            mass += bulb.mass;
            radius += bulb.radius;
            xPositionSum += bulb.position.x;
            yPositionSum += bulb.position.y;
            xVelocitySum += bulb.velocity.x;
            yVelocitySum += bulb.velocity.y;
        }

        double xPositionAvg = xPositionSum / (bulbs.size() + 1);
        double yPositionAvg = yPositionSum / (bulbs.size() + 1);
        double xVelocityAvg = xVelocitySum / (bulbs.size() + 1);
        double yVelocityAvg = yVelocitySum / (bulbs.size() + 1);
        Point2D.Double position = new Point2D.Double(xPositionAvg, yPositionAvg);
        Point2D.Double velocity = new Point2D.Double(xVelocityAvg, yVelocityAvg);

        this.mass = massSum;
        this.radius = radiusSum;
        this.position = position;
        this.velocity = velocity;
    }

    public double distance(Bulb other) {
        return this.position.distance(other.position);
    }

    public void setPosition(Point2D.Double position) {
        this.position = position;
    }

    // as opposed to overlapping just a little
    public boolean overlapsAlot(Bulb otherBulb) {
        return this.distance(otherBulb) < this.radius / 2.0;
    }
}
