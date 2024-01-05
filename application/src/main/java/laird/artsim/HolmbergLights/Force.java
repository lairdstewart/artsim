package laird.artsim.HolmbergLights;

import java.awt.geom.Point2D;

public class Force {
    double direction; // radians from x axis (like in math)
    double magnitude;

    public Force(double direction, double magnitude) {
        this.direction = direction;
        this.magnitude = magnitude;
    }
}
