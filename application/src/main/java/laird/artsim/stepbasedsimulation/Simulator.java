package laird.artsim.stepbasedsimulation;

import java.util.ArrayList;

public class Simulator<S extends State> {
    private final Updater<S> updater;

    public Simulator(Updater<S> updater) {
        this.updater = updater;
    }

    public ArrayList<S> runSimulation(S startingState, int numSteps)
    {
        ArrayList<S> stateList = new ArrayList<>();

        stateList.add(startingState);
        for (int i = 0; i < numSteps; i++)
        {
            stateList.add(updater.update(stateList.get(stateList.size()-1)));
        }
        return stateList;
    }
}
