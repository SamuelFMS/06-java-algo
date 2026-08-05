import java.util.Random;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static int numberLine = 6;
    static int numberRow = 12;
    static int numberOfBomb = 9;

    static StateCell[][] stateCells = new StateCell[numberLine][numberRow];

    public static void initGame() {
        for (int x = 0; x < stateCells.length; x++) {
            for (int y = 0; y < stateCells[x].length; y++) {
                stateCells[x][y] = StateCell.Vide;
            }
        }
    }

    public static void generateBomb(Random random) {
        int currentNumberOfBomb = 0;
        while (currentNumberOfBomb < numberOfBomb) {
            // pick a number between 0 and number-1
            int randomLine = random.nextInt(numberLine);
            int randomRow = random.nextInt(numberRow);
            if (stateCells[randomLine][randomRow] == StateCell.Vide) {
                stateCells[randomLine][randomRow] = StateCell.Bomb;
                currentNumberOfBomb++;
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
        int currentRowIndex = 0;
        for (StateCell[] rowStateCell : stateCells) {
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
                    case Vide:
                        System.out.print(" ");
                        break;
                    case Bomb:
                        System.out.print("b");
                        break;
                    case Checked:
                        System.out.print("■");
                        break;
                    default:
                        System.out.print("default");
                        break;
                }
                System.out.print(" |");
            }
            System.out.println();
            currentRowIndex++;
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
        return stateCell == StateCell.Bomb || (stateCell == StateCell.Explode || stateCell == StateCell.FlagBomb);
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