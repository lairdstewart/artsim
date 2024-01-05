package laird.artsim.HolmbergLights;

import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import laird.artsim.Animator;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // create initial state
        List<Point2D.Double> lightBulbs = new ArrayList<>();
        lightBulbs.add(new Point2D.Double(10, 10));
        lightBulbs.add(new Point2D.Double(20, 20));
        lightBulbs.add(new Point2D.Double(100, 100));
        lightBulbs.add(new Point2D.Double(10, 400));
        lightBulbs.add(new Point2D.Double(250, 250));
        lightBulbs.add(new Point2D.Double(300, 400));
        lightBulbs.add(new Point2D.Double(200, 450));
        lightBulbs.add(new Point2D.Double(45, 300));
        lightBulbs.add(new Point2D.Double(450, 450));
        lightBulbs.add(new Point2D.Double(300, 200));

        // simulate sequence of nodes
        Simulator simulator = new Simulator(1, 1000, 5);
        List<Node> nodes = simulator.runSimulation(lightBulbs);

        // animate nodes
        Animator animator = new Animator(nodes);
        animator.setFramesPerSecond(100);
        animator.play();

        // begin application
        primaryStage.setScene(new Scene(animator.getPane(), 500, 500));
        primaryStage.show();
    }
}
