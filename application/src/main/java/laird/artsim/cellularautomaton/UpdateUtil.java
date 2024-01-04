package laird.artsim.cellularautomaton;

public class UpdateUtil {
    private static final int[][] RULE_TABLE = ruleTable();

    public static State update(State state, int rule) {
        State result = new State(state.length);
        for (int i = 0; i < state.length; i++)
        {
            result.setCell(i, cellUpdate(state.getNeighbors(i), rule));
        }
        return result;
    }

    private static int cellUpdate(int[] neighbors, int rule)
    {
        int index = 0;
        index += neighbors[0] * 4;
        index += neighbors[1] * 2;
        index += neighbors[2];
        return RULE_TABLE[rule][index];
    }

    public static int[][] ruleTable()
    {
        int[][] table = new int[256][8];
        for (int i = 0; i < 256; i++) {
            table[i] = decimalToBinary(i);
        }
        return table;
    }

    private static int[] decimalToBinary(int decimal) {
        int[] binaryArray = new int[8];
        for (int i = 7; i >= 0; i--) {
            binaryArray[i] = decimal & 1; // Extract the least significant bit
            decimal >>= 1; // Right shift the bits
        }
        return binaryArray;
    }



}
