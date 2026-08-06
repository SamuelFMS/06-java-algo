import java.util.Random;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static int sizeGrid = 5;
    public static int numberOfBoat = 3;
    public static int sizeOfBoat = 2;
    public static Object[][][] grid = new Object[sizeGrid][sizeGrid][];
    public static Object[][][] ships = new Object[numberOfBoat][sizeOfBoat][];

    public static void displayCell(Object[] cell) {
        if (cell[0] == StateCell.EMPTY) {
            if (cell[1] != null) {
                System.out.print(cell[1]);// See the all the boats
                //System.out.print(" ");
            } else {
                System.out.print(" ");
            }
        } else if (cell[0] == StateCell.HIT) {
            if (cell[1] != null) {
                if (isShipSinked(ships[(int) cell[1]])) {
                    System.out.print("~");
                } else {
                    System.out.print("T");
                }
            } else {
                System.out.print("X");
            }
        }
    }

    public static boolean isShipSinked(Object[][] ships) {
        for (Object[] cell : ships) {
            if (cell[0] == StateCell.EMPTY) {
                return false;
            }
        }
        return true;
    }

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
            boolean isVertical = random.nextBoolean();
            int line = random.nextInt(sizeGrid - (isVertical ? (sizeOfBoat - 1) : 0));
            int row = random.nextInt(sizeGrid - (isVertical ? 0 : (sizeOfBoat - 1)));
            // Verify if the boat doesn't overlaps
            boolean boatOverlap = false;
            for (int currentSize = 0; currentSize < sizeOfBoat; currentSize++) {
                if (grid[line + (isVertical ? currentSize : 0)][row + (isVertical ? 0 : currentSize)][1] != null) {
                    boatOverlap = true;
                    break;
                }
            }
            if (!boatOverlap) {
                for (int currentSize = 0; currentSize < sizeOfBoat; currentSize++) {
                    grid[line + (isVertical ? currentSize : 0)][row + (isVertical ? 0 : currentSize)][1] = numberOfBoatGenerated;
                    ships[numberOfBoatGenerated][currentSize] = grid[line + (isVertical ? currentSize : 0)][row + (isVertical ? 0 : currentSize)];
                }
                numberOfBoatGenerated++;
            }
        }
    }

    private static boolean isInputValidCell(String cell, int[] result) {
        boolean inputValid = false;
        cell = cell.toUpperCase();
        if (cell.length() == 2 && ('A') <= cell.charAt(0) && cell.charAt(0) < ('A' + sizeGrid)) {
            if (('1') <= cell.charAt(1) && cell.charAt(1) < ('1' + sizeGrid)) {
                result[0] = cell.charAt(1) - ('1');
                result[1] = cell.charAt(0) - ('A');
                inputValid = true;
            }
        }
        return inputValid;
    }

    public static int[] inputACell(Scanner scanner) {
        int[] result = new int[2];
        System.out.println("Veuillez entrez une case (ex: B2)");
        boolean inputValid = false;
        while (!inputValid) {
            String cell = scanner.next();
            inputValid = isInputValidCell(cell, result);
        }
        return result;
    }

    public static void shoot(int[] pos) {
        if (grid[pos[0]][pos[1]][0] == StateCell.EMPTY) {
            grid[pos[0]][pos[1]][0] = StateCell.HIT;
            if (grid[pos[0]][pos[1]][1] != null) {
                System.out.println("Vous avez touché un bateau");
            } else {
                System.out.println("Dans l'eau");
            }
        } else {
            System.out.println("Vous avez deja tiré sur cette case");
        }
    }

    public static boolean isGameFinished() {
        boolean finished = true;
        for (Object[][] line : grid) {
            for (Object[] cell : line) {
                if (cell[1] != null && cell[0] == StateCell.EMPTY) {
                    finished = false;
                    break;
                }
            }
        }
        return finished;
    }

    public static void main(String[] args) {
        Random random = new Random();
        Scanner scanner = new Scanner(System.in);

        initGrid();
        generateBoat(random);
        while (!isGameFinished()) {
            displayGrid();
            shoot(inputACell(scanner));
        }
        displayGrid();
        System.out.println("Felicitation vous avez touchés tous les bateaux");

        scanner.close();
    }
}