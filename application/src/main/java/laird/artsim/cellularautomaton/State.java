package laird.artsim.cellularautomaton;

public class State {
    final int length;
    private final int[] cellArray;

    public State(int length)
    {
        this.cellArray = new int[length];
        this.length = length;
    }

    public State(int[] cellArray)
    {
        this.cellArray = cellArray;
        this.length = cellArray.length;
    }

    public int getCell(int index)
    {
        return cellArray[index];
    }

    public boolean cellAlive(int index)
    {
        return cellArray[index] == 1;
    }

    public void setCell(int index, int value)
    {
        if (invalid(index))
        {
            throw new IndexOutOfBoundsException();
        }

        cellArray[index] = value;
    }

    public int[] getNeighbors(int index)
    {
        if (invalid(index))
        {
            throw new IndexOutOfBoundsException();
        }

        if (index == 0)
        {
            return new int[]{getCell(length - 1), getCell(0), getCell(1)};
        }

        if (index == length - 1) {
            return new int[]{getCell(length - 2), getCell(length - 1), getCell(0)};
        }

        return new int[]{getCell(index-1), getCell(index), getCell(index + 1)};
    }

    private boolean invalid(int index)
    {
        return index < 0 || index > length - 1;
    }
}
