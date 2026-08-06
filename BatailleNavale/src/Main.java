import java.util.Random;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static int sizeGrid = 3;
    public static int numberOfBoat = 3;
    public static int sizeOfBoat = 1;
    public static Object[][][] grid = new Object[sizeGrid][sizeGrid][];

    public static void displayCell(Object[] cell) {
        if (cell[0] == StateCell.EMPTY) {
            System.out.print(" ");
        } else if (cell[0] == StateCell.HIT) {
            if (cell[1] != null) {
                System.out.print("T");
            } else {
                System.out.print("X");
            }
        }
    }

    /**
     * +---+---+---+  // line1
     * | A | B | C |  // line2
     * +---+---+---+---+  // delimiter
     * | 1 |   |   |   |
     * +---+---+---+---+  // delimiter
     * | 2 |   |   |   |
     * +---+---+---+---+  // delimiter
     * | 3 |   |   |   |
     * +---+---+---+---+  // delimiter
     */
    public static void displayGrid() {
        /*
            Header
         */
        StringBuilder line1 = new StringBuilder();
        StringBuilder line2 = new StringBuilder();
        StringBuilder delimiter = new StringBuilder();
        line1.append("    ");
        line2.append("    ");
        delimiter.append("+---");
        for (int RowCount = 0; RowCount < sizeGrid; RowCount++) {
            line1.append("+---");
            delimiter.append("+---");
            line2.append("| ").append((char) ('A' + RowCount)).append(" ");
        }
        line1.append("+");
        delimiter.append("+");
        line2.append("|");
        System.out.println(line1);
        System.out.println(line2);
        System.out.println(delimiter);

        /*
            Body
         */
        int currentLine = 1;
        for (Object[][] lineCell : grid) {
            System.out.print("| ");
            System.out.print(currentLine);
            System.out.print(" |");
            for (Object[] cell : lineCell) {
                System.out.print(" ");
                displayCell(cell);
                System.out.print(" |");
            }
            System.out.println();
            System.out.println(delimiter);
            currentLine++;
        }
    }

    public static void initGrid() {
        for (int x = 0; x < sizeGrid; x++) {
            for (int y = 0; y < sizeGrid; y++) {
                Object[] newCell = new Object[2];
                newCell[0] = StateCell.EMPTY; // State of the cell EMPTY for not being touched
                newCell[1] = null; // belong to witch boat
                grid[x][y] = newCell;
            }
        }
    }

    public static void generateBoat(Random random) {
        int numberOfBoatGenerated = 0;
        while (numberOfBoatGenerated < numberOfBoat) {
            // Generation aleatoire de la position
            int line = random.nextInt(sizeGrid);
            int row = random.nextInt(sizeGrid);
            if (grid[line][row][1] == null) {
                grid[line][row][1] = numberOfBoatGenerated;
                numberOfBoatGenerated++;
            }
        }
    }

    public static void main(String[] args) {
        Random random = new Random();

        initGrid();
        generateBoat(random);
        displayGrid();
    }
}