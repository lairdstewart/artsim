package laird.artsim.cellularautomaton;

import laird.artsim.stepbasedsimulation.State;

import java.util.logging.Logger;

public class AutomatonState extends State {

    private static final Logger LOGGER = Logger.getLogger("AutomatonState");
    final int length;
    private final int[] cellArray;

    public AutomatonState(int length)
    {
        this.cellArray = new int[length];
        this.length = length;
    }

    public AutomatonState(int[] cellArray)
    {
        this.cellArray = cellArray;
        this.length = cellArray.length;
    }

    public int getCell(int index)
    {
        return cellArray[index];
    }

    public void setCell(int index, int value)
    {
        if (!isValid(index))
        {
            throw new IndexOutOfBoundsException();
        }

        cellArray[index] = value;
    }

    public int[] getNeighbors(int index)
    {
        if (!isValid(index))
        {
            throw new IndexOutOfBoundsException();
        }

        return new int[]{getCell(index-1), getCell(index), getCell(index + 1)};
    }

    private boolean isValid(int index)
    {
        return index > 0 && index < length - 1;
    }

    @Override
    public String toString() {
        String result = "";
        result += "[";
        for (int i = 0; i < length; i++)
        {
            result += (cellArray[i] + ",");
        }
        result += "]\n";
        return result;
    }
}
