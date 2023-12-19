package laird.artsim.cellularautomaton;

import laird.artsim.stepbasedsimulation.Updater;

public class AutomatonUpdate extends Updater<AutomatonState> {
    private static final int[] RULE_TABLE = createRuleTable();

    @Override
    public AutomatonState update(AutomatonState state) {
        AutomatonState result = new AutomatonState(state.length);
        for (int i = 1; i < state.length - 1; i++)
        {
            result.setCell(i, cellUpdate(state.getNeighbors(i)));
        }
        return result;
    }

    private int cellUpdate(int[] neighbors)
    {
        int index = 0;
        index += neighbors[0] * 4;
        index += neighbors[1] * 2;
        index += neighbors[2];
        return RULE_TABLE[index];
    }

    private static int[] createRuleTable()
    {
        int[] updateTable = new int[8];
        updateTable[0] = 1; // _ _ _
        updateTable[1] = 0; // _ _ X
        updateTable[2] = 0; // _ X _
        updateTable[3] = 0; // _ X X
        updateTable[5] = 0; // X _ X
        updateTable[4] = 0; // X _ _
        updateTable[6] = 0; // X X _
        updateTable[7] = 0; // X X X
        return updateTable;
    }



}
