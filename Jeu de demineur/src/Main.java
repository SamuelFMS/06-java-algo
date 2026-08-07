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

    /**
     * Number of line on the game grid
     */
    static int numberLine;
    /**
     * Number of rows on the game grid
     */
    static int numberRow;
    /**
     * Number of bombs on the game grid
     */
    static int numberOfBomb;

    /**
     * Game grid represented by StateCell
     */
    static StateCell[][] stateCells;

    /**
     * Placing mode place a flag or just reveal
     */
    static PlacingMode currentPlacingMode = PlacingMode.REVEAL;
    /**
     * Number of flag available during the game
     */
    static int numberOFFlagRemaining = numberOfBomb + 3;


    /**
     * Create a blank game grid
     */
    public static void initGame() {
        stateCells = new StateCell[numberLine][numberRow];
        for (int x = 0; x < stateCells.length; x++) {
            for (int y = 0; y < stateCells[x].length; y++) {
                stateCells[x][y] = StateCell.EMPTY;
            }
        }
    }

    /**
     * Generate all the bombs on the game grid
     * It cannot generate a bomb beside the player start point
     *
     * @param random
     */
    public static void generateBomb(Random random) {
        int currentNumberOfBomb = 0;
        while (currentNumberOfBomb < numberOfBomb) {
            // pick a number between 0 and number-1
            int randomLine = random.nextInt(numberLine);
            int randomRow = random.nextInt(numberRow);
            if (stateCells[randomLine][randomRow] == StateCell.EMPTY) {
                // Il est impossible de placer une bombe autour du point pour commencer
                boolean besideStart = false;
                for (int neighbourRow = randomRow - 1; neighbourRow <= randomRow + 1; neighbourRow++) {
                    for (int neighbourLine = randomLine - 1; neighbourLine <= randomLine + 1; neighbourLine++) {
                        if (neighbourRow >= numberRow || neighbourRow < 0 || neighbourLine >= numberLine || neighbourLine < 0) {
                            continue; // Outside of grill or same cell as the input not a neighbour
                        }
                        if (stateCells[neighbourLine][neighbourRow] == StateCell.EMPTY_CHECKED) {
                            besideStart = true;
                            break;
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

    /**
     * Return blue if there is only 1, green if there is 2 and red if there is more
     *
     * @param numberOfBomb
     * @return
     */
    public static String colorByNumberOfBombs(int numberOfBomb) {
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

    public static void displayEndGame() {
        /*
         * Header
         */
        System.out.print((stateCells.length >= 10 ? " " : "") + "  |");
        for (int a = 0; a < numberRow; a++) {
            System.out.print(" " + (char) ('A' + a) + " ");
            System.out.print("|");
        }
        System.out.println();

        /*
         * Body
         */
        int currentLineIndex = 0;
        for (StateCell[] rowStateCell : stateCells) {
            int currentRowIndex = 0;
            /*
             * Left index
             */
            System.out.print((stateCells.length >= 10 ? (currentLineIndex < 10 ? currentLineIndex + " " : currentLineIndex) : currentLineIndex) + " |");
            /*
             * Content
             */
            for (StateCell stateCell : rowStateCell) {
                System.out.print(" ");
                switch (stateCell) {
                    case EMPTY:
                        System.out.print(ANSI_DARK_GREY + "■" + ANSI_RESET);
                        break;
                    case BOMB:
                        System.out.print(ANSI_DARK_GREY + "*" + ANSI_RESET);
                        break;
                    case EMPTY_CHECKED:
                        System.out.print(" ");
                        break;
                    case BOMB_BESIDE:
                        int numberOfBomb = numberOfBombBeside(currentLineIndex, currentRowIndex);
                        System.out.print(colorByNumberOfBombs(numberOfBomb) + numberOfBomb + ANSI_RESET);
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

    /**
     * Display the game board
     */
    public static void displayGame() {
        /*
         * Header
         */
        System.out.print((stateCells.length >= 10 ? " " : "") + "  |");
        for (int a = 0; a < numberRow; a++) {
            System.out.print(" " + (char) ('A' + a) + " ");
            System.out.print("|");
        }
        System.out.println();

        /*
         * Body
         */
        int currentLineIndex = 0;
        for (StateCell[] rowStateCell : stateCells) {
            int currentRowIndex = 0;
            /*
             * Left index
             */
            System.out.print((stateCells.length >= 10 ? (currentLineIndex < 10 ? currentLineIndex + " " : currentLineIndex) : currentLineIndex) + " |");
            /*
             * Content
             */
            for (StateCell stateCell : rowStateCell) {
                System.out.print(" ");
                switch (stateCell) {
                    case EMPTY:
                        System.out.print(ANSI_DARK_GREY + "■" + ANSI_RESET);
                        break;
                    case BOMB:
                        System.out.print(ANSI_DARK_GREY + "■" + ANSI_RESET);
                        break;
                    case EMPTY_CHECKED:
                        System.out.print(" ");
                        break;
                    case BOMB_BESIDE:
                        int numberOfBomb = numberOfBombBeside(currentLineIndex, currentRowIndex);
                        System.out.print(colorByNumberOfBombs(numberOfBomb) + numberOfBomb + ANSI_RESET);
                        break;
                    case BOMB_FLAG:
                        System.out.print(ANSI_RED + "⚑" + ANSI_RESET);
                        break;
                    case EMPTY_FLAG:
                        System.out.print(ANSI_RED + "⚑" + ANSI_RESET);
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

    /**
     * Input a cell or change mode
     *
     * @param scanner
     * @param canSwitchMode
     * @return
     */
    public static int[] inputACell(Scanner scanner, boolean canSwitchMode) {
        int[] result = new int[2];
        System.out.println("Veuillez entrez une case (ex: B2)");
        if (canSwitchMode) {
            displayCurrentPlacingMode();
        }
        boolean inputValid = false;
        while (!inputValid) {
            String cell = scanner.next();
            if (cell.equalsIgnoreCase("s") && canSwitchMode) {
                currentPlacingMode = currentPlacingMode == PlacingMode.FLAGGING ? PlacingMode.REVEAL : PlacingMode.FLAGGING;
                displayCurrentPlacingMode();
            } else {
                inputValid = isInputValidCell(cell, result);
            }
        }
        return result;
    }

    /**
     * Display the current placing Mode
     */
    private static void displayCurrentPlacingMode() {
        System.out.print("Vous êtes en mode " + (currentPlacingMode == PlacingMode.FLAGGING ? "\uD83D\uDEA9" : "\uD83D\uDD0D"));
        if (currentPlacingMode == PlacingMode.FLAGGING) {
            System.out.print(" (" + numberOFFlagRemaining + " Restants)");
        }
        System.out.println(" Pour changer de mode entrez (s)");
    }

    public static boolean isANumber(char inputChar) {
        return inputChar >= '0' && inputChar <= '9';
    }

    /**
     * Verify the cell if it's correct it return true and store the value in an array result 0 corresponding the line and 1 the row
     *
     * @param cell
     * @param result
     * @return
     */
    private static boolean isInputValidCell(String cell, int[] result) {
        boolean inputValid = true;
        cell = cell.toUpperCase();
        if (cell.length() >= 2) {
            Integer res = null;
            if (cell.charAt(0) < ('A') || cell.charAt(0) >= ('A' + numberRow)) {
                System.out.println("Un caractere était attendu entre A et " + (char) ('A' + numberRow));
                inputValid = false;
            } else {
                result[1] = cell.charAt(0) - ('A');
            }

            if (cell.length() == 2) {
                if (isANumber(cell.charAt(1))) {
                    res = cell.charAt(1) - '0';
                }
            } else if (cell.length() == 3) {
                if (isANumber(cell.charAt(1)) && isANumber(cell.charAt(2))) {
                    res = (cell.charAt(1) - '0') * 10 + cell.charAt(2) - '0';
                }
            }
            if (res == null) {
                inputValid = false;
                System.out.println("Un numéro est attendus");
            } else if (res >= numberLine) {
                inputValid = false;
                System.out.println("Un numéro entre 0 et " + (numberLine - 1));
            } else {
                result[0] = res;
            }

        }
        return inputValid;
    }

    /**
     * Return true if its a bomb
     *
     * @param stateCell
     * @return
     */
    public static boolean isABomb(StateCell stateCell) {
        return stateCell == StateCell.BOMB || (stateCell == StateCell.BOMB_EXPLODE || stateCell == StateCell.BOMB_FLAG);
    }

    /**
     * Count how many bombs there is in the 8 cells beside the input cell
     *
     * @param line
     * @param row
     * @return
     */
    public static int numberOfBombBeside(int line, int row) {
        int numberOfBomb = 0;
        for (int neighbourRow = row - 1; neighbourRow <= row + 1; neighbourRow++) {
            for (int neighbourLine = line - 1; neighbourLine <= line + 1; neighbourLine++) {
                if (neighbourRow >= numberRow || neighbourRow < 0 || neighbourLine >= numberLine || neighbourLine < 0 || (neighbourLine == line && neighbourRow == row)) {
                    continue; // Outside of grill or same cell as the input not a neighbour
                }
                if (isABomb(stateCells[neighbourLine][neighbourRow])) {
                    numberOfBomb++;
                }
            }
        }
        return numberOfBomb;
    }

    /**
     * Return the string of the cell ex: A1
     *
     * @param line
     * @param row
     * @return
     */
    public static String returnNameCell(int line, int row) {
        return ((char) ('A' + row) + "" + line);
    }

    /**
     * Reveal or flag a cell, if its empty it will reveal all the other beside if not flagged and will do the same for their neighbour
     *
     * @param line
     * @param row
     */
    public static void checkCell(int line, int row) {
        if (stateCells[line][row] == StateCell.EMPTY && currentPlacingMode == PlacingMode.REVEAL) {
            int bombeBeside = numberOfBombBeside(line, row);
            if (bombeBeside == 0) {
                stateCells[line][row] = StateCell.EMPTY_CHECKED;
            } else {
                stateCells[line][row] = StateCell.BOMB_BESIDE;
                return;
            }
            for (int neighbourRow = row - 1; neighbourRow <= row + 1; neighbourRow++) {
                for (int neighbourLine = line - 1; neighbourLine <= line + 1; neighbourLine++) {
                    if (neighbourRow >= numberRow || neighbourRow < 0 || neighbourLine >= numberLine || neighbourLine < 0 || (neighbourLine == line && neighbourRow == row)) {
                        continue;
                    }
                    checkCell(neighbourLine, neighbourRow);
                }
            }
        } else if (currentPlacingMode == PlacingMode.FLAGGING && numberOFFlagRemaining <= 0) {
            System.out.println("Impossible de placer un drapeau! Il vous en reste 0");
        } else if (stateCells[line][row] == StateCell.EMPTY && currentPlacingMode == PlacingMode.FLAGGING) {
            stateCells[line][row] = StateCell.EMPTY_FLAG;
            numberOFFlagRemaining--;
        } else if (stateCells[line][row] == StateCell.BOMB) {
            if (currentPlacingMode == PlacingMode.FLAGGING) {
                stateCells[line][row] = StateCell.BOMB_FLAG;
                numberOFFlagRemaining--;
            } else {
                stateCells[line][row] = StateCell.BOMB_EXPLODE;
            }
        }
    }

    /**
     * Return true if the game is finished with a display if won or loose
     *
     * @return
     */
    public static boolean isGameFinished() {
        boolean boardExplode = false;
        boolean allBombHaveNotBeenRevealed = false;
        for (StateCell[] listStateCell : stateCells) {
            for (StateCell stateCell : listStateCell) {
                if (stateCell == StateCell.BOMB_EXPLODE) {
                    boardExplode = true;
                } else if (stateCell == StateCell.BOMB) {
                    allBombHaveNotBeenRevealed = true;
                }
            }
        }
        if (boardExplode) {
            System.out.println("\uD83D\uDCA5 " + ANSI_RED + "Vous avez perdu, vous avez provoquer une explosion" + ANSI_RESET + "\uD83D\uDCA5");
        } else if (!allBombHaveNotBeenRevealed) {
            System.out.println(ANSI_GREEN + "🎉 VICTOIRE ! Félicitations, vous avez déminé le terrain avec succès ! 🎉" + ANSI_RESET);
        }
        return boardExplode || !allBombHaveNotBeenRevealed;
    }

    /**
     * Function that return a number between min and max that have been input by user
     *
     * @param scanner
     * @param message
     * @param min
     * @param max
     * @return
     */
    public static int inputIntegerBetween(Scanner scanner, String message, int min, int max) {
        Integer res = null;
        while (res == null) {
            System.out.println(message);
            String stringInput = scanner.next();
            try {
                res = Integer.valueOf(stringInput);
            } catch (Exception e) {
                System.out.println("Veuillez entre un nombre valide");
            }
            if (res != null) {
                if (res < min || res > max) {
                    res = null;
                }
            }
        }
        return res;
    }

    /**
     * Main method to play the game
     *
     * @param args
     */
    public static void main(String[] args) {
        Random random = new Random();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Quelle difficulté souhaitez vous: ");
        System.out.println("1- Facile (5x5 - 5 drapeau)");
        System.out.println("2- Moyen (10x10 - 10 drapeau)");
        System.out.println("3- Difficile (15x15 - 40 drapeau)");
        int difficulty = inputIntegerBetween(scanner, "Veuillez entrez un nombre entre 1 et 3", 1, 3);
        switch (difficulty) {
            case 1:
                numberOFFlagRemaining = 5;
                numberOfBomb = 5;
                numberRow = 5;
                numberLine = 5;
                break;
            case 2:
                numberOFFlagRemaining = 10;
                numberOfBomb = 10;
                numberRow = 10;
                numberLine = 10;
                break;
            case 3:
                numberOFFlagRemaining = 40;
                numberOfBomb = 40;
                numberRow = 15;
                numberLine = 15;
                break;
            default:
                break;
        }
        initGame();

        /*
         * First round is Special
         */
        displayGame();
        int[] res = inputACell(scanner, false);
        System.out.println(returnNameCell(res[0], res[1]));
        stateCells[res[0]][res[1]] = StateCell.EMPTY_CHECKED;
        generateBomb(random);
        stateCells[res[0]][res[1]] = StateCell.EMPTY;
        currentPlacingMode = PlacingMode.REVEAL;
        checkCell(res[0], res[1]);
        // End first Round

        while (!isGameFinished()) {
            displayGame();
            int[] position = inputACell(scanner, true);
            checkCell(position[0], position[1]);

        }
        displayEndGame();

        scanner.close();
    }
}