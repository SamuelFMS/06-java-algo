import java.util.Random;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    // Codes de couleur ANSI
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_DARK_GREY = "\u001B[90m";
    public static final String ANSI_BLUE = "\u001B[34m";

    static int numberLine = 6; 
    static int numberRow = 12; 
    static int numberOfBomb = 9;

    static StateCell[][] stateCells = new StateCell[numberLine][numberRow];

    static PlacingMode currentPlacingMode = PlacingMode.REVEAL;
    static int numberOFFlagRemaining = numberOfBomb + 3;

    public static void initGame() {
        for (int x = 0; x < stateCells.length; x++) {
            for (int y = 0; y < stateCells[x].length; y++) {
                stateCells[x][y] = StateCell.EMPTY;
            }
        }
    }



    public static void generateBomb(Random random) {
        int currentNumberOfBomb = 0;
        while (currentNumberOfBomb < numberOfBomb) {
            // pick a number between 0 and number-1
            int randomLine = random.nextInt(numberLine);
            int randomRow = random.nextInt(numberRow);
            if (stateCells[randomLine][randomRow] == StateCell.EMPTY) {
                // Il est impossible de placer une bombe autour du point pour commencer
                boolean besideStart = false;
                for(int neighbourRow = randomRow-1; neighbourRow <= randomRow+1; neighbourRow++) {
                    for (int neighbourLine = randomLine - 1; neighbourLine <= randomLine + 1; neighbourLine++) {
                        if(neighbourRow >= numberRow || neighbourRow < 0 || neighbourLine >= numberLine || neighbourLine < 0){
                            continue; // Outside of grill or same cell as the input not a neighbour
                        }
                        if (stateCells[neighbourLine][neighbourRow] == StateCell.EMPTY_CHECKED) {
                            besideStart = true;
                        }
                    }
                }
                if (!besideStart) {
                    stateCells[randomLine][randomRow] = StateCell.BOMB;
                    currentNumberOfBomb++;
                }
            }
        }

    }

    public static void displayGame() {
        /**
         * Header
         */
        System.out.print("  |");
        for (int a = 0; a < numberRow; a++) {
            System.out.print(" " + (char) ('A' + a) + " ");
            System.out.print("|");
        }
        System.out.println();

        /**
         * Body
         */
        int currentLineIndex = 0;
        for (StateCell[] rowStateCell : stateCells) {
            int currentRowIndex = 0;
            /**
             * Left index
             */
            System.out.print(currentRowIndex + " |");
            /**
             * Content
             */
            for (StateCell stateCell : rowStateCell) {
                System.out.print(" ");
                switch (stateCell) {
                    case EMPTY:
                    case BOMB:
                        System.out.print(ANSI_DARK_GREY +"■"+ANSI_RESET);
                        break;
                    case EMPTY_CHECKED:
                        System.out.print(" ");
                        break;
                    case BOMB_BESIDE:
                        int nombreDeBombe = numberOfBombBeside(currentLineIndex,currentRowIndex);
                        System.out.print(colorByNumberOfBombs(nombreDeBombe) + nombreDeBombe + ANSI_RESET);
                        break;
                    case BOMB_FLAG:
                        System.out.print(ANSI_RED + "⚑" + ANSI_RESET);
                        break;
                    case EMPTY_FLAG:
                        System.out.print(ANSI_RED + "⚐" + ANSI_RESET);
                        break;
                    case BOMB_EXPLODE:
                        System.out.print(ANSI_RED + "✴" + ANSI_RESET);
                        break;
                    default:
                        System.out.print(stateCell);
                        break;
                }
                System.out.print(" |");
                currentRowIndex++;
            }
            System.out.println();
            currentLineIndex++;
        }
    }

    public static int[] inputACell(Scanner scanner) {
        int[] result = new int[2];
        System.out.println("Veuillez entrez une case (ex: B2)");
        boolean inputValid = false;
        while (!inputValid) {
            String cell = scanner.next();
            if (cell.length() == 2) {
                if ((int) ('A') <= (int) cell.charAt(0) && (int) cell.charAt(0) < (int) ('A' + numberRow)) {
                    if((int)('0') <= (int)cell.charAt(1) && (int)cell.charAt(1) < (int)('0'+numberLine)) {
                        result[0] = (int)cell.charAt(1)-(int)('0');
                        result[1] =(int)cell.charAt(0)-(int)('A');
                        inputValid = true;
                    }
                }
            }
        }
        return result;
    }

    public static boolean isABomb(StateCell stateCell) {
        return stateCell == StateCell.BOMB || (stateCell == StateCell.BOMB_EXPLODE || stateCell == StateCell.BOMB_FLAG);
    }
    }

    public static void main(String[] args) {
        Random random = new Random();
        Scanner scanner = new Scanner(System.in);

        initGame();
        generateBomb(random);
        displayGame();
        int[] res = inputACell(scanner);

        scanner.close();
    }
}