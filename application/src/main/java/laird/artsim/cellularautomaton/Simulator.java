package laird.artsim.cellularautomaton;

import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class Simulator {


    public static Node stateToNode(State state)
    {
        Rectangle[] cells = new Rectangle[state.length];
        for (int i = 0; i < state.length; i++)
        {
            Paint fill = state.cellAlive(i) ? Color.WHITE : Color.BLACK;
            cells[i] = new Rectangle(10, 30, fill);
        }
        return new HBox(cells);
    }

    public static ArrayList<Node> runSimulation(State startingState, int numSteps, int rule)
    {
        ArrayList<Node> result = new ArrayList<>();

        result.add(stateToNode(startingState));
        State previousState = startingState;
        for (int i = 0; i < numSteps; i++)
        {
            State nextState = UpdateUtil.update(previousState, rule);
            result.add(stateToNode(nextState));
            previousState = nextState;
        }
        return result;
    }
}
