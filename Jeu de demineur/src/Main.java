//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static int numberLine = 6;
    static int numberRow = 12;


    static StateCell[][] etatsCase = new StateCell[numberLine][numberRow];

    public static void initGame() {
        for (int x = 0; x < etatsCase.length; x++){
            for(int y = 0; y < etatsCase[x].length; y++) {
                etatsCase[x][y] = StateCell.Vide;
            }
        }
    }

    public static void displayGame() {
        /**
         * Header
         */
        System.out.print("  |");
        for(int a = 0; a < numberRow; a++){
            System.out.print(" " + (char)('A'+a) + " ");
            System.out.print("|");
        }
        System.out.println();

        /**
         * Body
         */
        int currentRowIndex = 0;
        for (StateCell[] rowStateCell: etatsCase){
            /**
             * Left index
             */
            System.out.print(currentRowIndex + " |");
            /**
             * Content
             */
            for (StateCell stateCell: rowStateCell) {
                System.out.print(" ");
                switch (stateCell) {
                    case Vide:
                        System.out.print(" ");
                        break;
                    case Bomb:
                        System.out.print(" ");
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
    public static void main(String[] args) {
        initGame();
        displayGame();
    }
}