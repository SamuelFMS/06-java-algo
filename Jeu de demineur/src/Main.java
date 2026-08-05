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

    public static String colorByNumberOfBombs(int numberOfBomb){
        String couleur;

        if (numberOfBomb == 1) {
            couleur = ANSI_BLUE;
        } else if (numberOfBomb == 2) {
            couleur = ANSI_GREEN;
        } else {
            couleur = ANSI_RED; // Gère 3, 4, 5, etc.
        }
        return couleur;
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
            System.out.print(currentLineIndex + " |");
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

    public static int[] inputACell(Scanner scanner, boolean canSwitchMode) {
        int[] result = new int[2];
        System.out.println("Veuillez entrez une case (ex: B2)");
        if(canSwitchMode){
            displayCurrentPlacingMode();
        }
        boolean inputValid = false;
        while (!inputValid) {
            String cell = scanner.next();
            if(cell.toLowerCase().equals("s") && canSwitchMode){
                currentPlacingMode = currentPlacingMode == PlacingMode.FLAGGING? PlacingMode.REVEAL:PlacingMode.FLAGGING;
                displayCurrentPlacingMode();
            }
            else {
                inputValid = isInputValidCell(cell, result, inputValid);
            }
        }
        return result;
    }

    private static void displayCurrentPlacingMode() {
        System.out.print("Vous êtes en mode " + (currentPlacingMode == PlacingMode.FLAGGING ? "\uD83D\uDEA9":"\uD83D\uDD0D"));
        if(currentPlacingMode == PlacingMode.FLAGGING) {
            System.out.print(" ("+numberOFFlagRemaining+ " Restants)");
        }
        System.out.println(" Pour changer de mode entrez (s)");
    }

    private static boolean isInputValidCell(String cell, int[] result, boolean inputValid) {
        cell = cell.toUpperCase();
        if (cell.length() == 2 && ('A') <= cell.charAt(0) && cell.charAt(0) < ('A' + numberRow)) {
                if (('0') <= cell.charAt(1) && cell.charAt(1) < ('0'+numberLine)) {
                    result[0] = cell.charAt(1)-('0');
                    result[1] =cell.charAt(0)-('A');
                    inputValid = true;
                }
            }

        return inputValid;
    }

    public static boolean isABomb(StateCell stateCell) {
        return stateCell == StateCell.BOMB || (stateCell == StateCell.BOMB_EXPLODE || stateCell == StateCell.BOMB_FLAG);
    }

    public static int numberOfBombBeside(int line, int row){
        int numberOfBomb = 0;
        for(int neighbourRow = row-1; neighbourRow <= row+1; neighbourRow++){
            for(int neighbourLine = line-1; neighbourLine <= line+1; neighbourLine++){
                if(neighbourRow >= numberRow || neighbourRow < 0 || neighbourLine >= numberLine || neighbourLine < 0 || (neighbourLine==line && neighbourRow==row)){
                    continue; // Outside of grill or same cell as the input not a neighbour
                }
                if(isABomb(stateCells[neighbourLine][neighbourRow])){
                    numberOfBomb++;
                }
            }
        }
        return numberOfBomb;
    }

    public static String returnNameCell(int line, int row) {
        return ((char)('A'+row) + "" + line);
    }

    public static void checkCell(int line, int row){
        if (stateCells[line][row] == StateCell.EMPTY && currentPlacingMode == PlacingMode.REVEAL) {
            int bombeBeside = numberOfBombBeside(line, row);
            if(bombeBeside == 0) {
                stateCells[line][row] = StateCell.EMPTY_CHECKED;
            }
            else {
                stateCells[line][row] = StateCell.BOMB_BESIDE;
                return;
            }
            for(int neighbourRow = row-1; neighbourRow <= row+1; neighbourRow++) {
                for (int neighbourLine = line - 1; neighbourLine <= line + 1; neighbourLine++) {
                    if(neighbourRow >= numberRow || neighbourRow < 0 || neighbourLine >= numberLine || neighbourLine < 0 || (neighbourLine==line && neighbourRow==row)) {
                        continue;
                    }
                    checkCell(neighbourLine, neighbourRow);
                }
            }
        }
        else if(currentPlacingMode == PlacingMode.FLAGGING && numberOFFlagRemaining <= 0) {
            System.out.println("Impossible de placer un drapeau! Il vous en reste 0");
        }
        else if (stateCells[line][row] == StateCell.EMPTY && currentPlacingMode == PlacingMode.FLAGGING){
            stateCells[line][row] = StateCell.EMPTY_FLAG;
            numberOFFlagRemaining--;
        }
        else if(stateCells[line][row] == StateCell.BOMB){
            if(currentPlacingMode == PlacingMode.FLAGGING) {
                stateCells[line][row] = StateCell.BOMB_FLAG;
                numberOFFlagRemaining--;
            }
            else {
                stateCells[line][row] = StateCell.BOMB_EXPLODE;
            }
        }
    }

    public static boolean isGameFinished(){
        boolean boardExplode = false;
        boolean allHaveNotBeenRevealed = false;
        for (StateCell[] listStateCell: stateCells) {
            for (StateCell stateCell : listStateCell) {
                if(stateCell == StateCell.BOMB_EXPLODE) {
                    boardExplode = true;
                }
                else if (stateCell == StateCell.EMPTY ||stateCell == StateCell.BOMB) {
                    allHaveNotBeenRevealed = true;
                }
            }
        }
        if( boardExplode){
            System.out.println("\uD83D\uDCA5 " + ANSI_RED + "Vous avez perdu, vous avez provoquer une explosion" + ANSI_RESET + "\uD83D\uDCA5");
        }
        else if(!allHaveNotBeenRevealed) {
            System.out.println(ANSI_GREEN + "🎉 VICTOIRE ! Félicitations, vous avez déminé le terrain avec succès ! 🎉" + ANSI_RESET);
        }
        return boardExplode || !allHaveNotBeenRevealed;
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