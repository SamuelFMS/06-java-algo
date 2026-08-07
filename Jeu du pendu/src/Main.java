import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static final String LIGHT_GRAY = "\u001B[38;5;250m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String RESET = "\u001B[0m";

    /**
     * Number of try left
     */
    protected static int numberOfTry = 6;
    /**
     * List of word to guess
     */
    protected static String[] listOFWords = {"ACCORD", "ACTEUR", "AGENDA", "AGNEAU", "ALBUM", "ALERTE", "AMANDE", "AMICAL", "ANIMAL", "ANANAS", "ARBRE", "ARDOISE", "ARGENT", "ARMOIRE", "ASTUCE", "AVION", "AVOCAT", "BALLE", "BANANE", "BARQUE", "BASSIN", "BATEAU", "BICYCLETTE", "BIJOU", "BILLET", "BOCAL", "BOUGIE", "BOUTEILLE", "BOUTON", "BRANCHE", "BUREAU", "CABANE", "CADEAU", "CAHIER", "CAILLOU", "CALCUL", "CAMERA", "CAMION", "CANARD", "CANYON", "CARNET", "CARTON", "CASQUE", "CERISE", "CHAISE", "CHALEUR", "CHAPEAU", "CHASSE", "CHATON", "CHAUSSURE", "CHEMISE", "CHEVAL", "CHEVEU", "CHIEN", "CHIFFRE", "CIMENT", "CINEMA", "CISEAUX", "CITRON", "CLASSE", "CLAVIER", "CLOCHARD", "COFFRE", "COLLE", "COLORE", "COMPTE", "COPAIN", "CORDE", "COULOIR", "COUTEAU", "CRAIE", "CRAYON", "CUISINE", "CUIVRE", "DANSEUR", "DAUPHIN", "DECOR", "DEFENSE", "DESSIN", "DEVINETTE", "DICTEE", "DIGITAL", "DINOSAURE", "DOCTEUR", "DOMINO", "DRAPEAU", "DROITE", "ECLAIR", "ECOLE", "ECRAN", "ECUREUIL", "EFFORT", "ENFANT", "ENIGME", "EPAULE", "EPONGE", "EQUIPE", "ESPACE", "ESPRIT", "ETOILE", "FACTORIE", "FAMILLE", "FANTOME", "FARINE", "FAUTEUIL", "FENETRE", "FERMIER", "FEUILLE", "FIDELE", "FILLETTE", "FLEUVE", "FLOCON", "FORGEUR", "FORMAT", "FRAISE", "FROMAGE", "FUSIBLE", "GARAGE", "GARCON", "GATEAU", "GAUCHE", "GAZON", "GIRAFE", "GLACE", "GOMME", "GOUDRON", "GRANDEUR", "GRENOUILLE", "GRIFFE", "GUITARE", "HABIT", "HAZARD", "HIBOU", "HISTOIRE", "HORLOGE", "HOTEL", "HUMAIN", "HUMOUR", "IMAGE", "INSECTE", "INVITE", "IVOIRE", "JARDIN", "JAUNE", "JOURNAL", "JUNGLE", "KANGOUROU", "KLAXON", "LABORATOIRE", "LAMPE"};
    /**
     * List of already attempted char
     */
    protected static ArrayList<Character> listAttemptedChar = new ArrayList<Character>();

    /**
     * Return a char from a to z that the user didnt already input
     * @param scanner
     * @return
     */
    public static char inputLetter(Scanner scanner) {
        char result = '\0'; // Empty char (Unicode 0)

        while (result == '\0') {
            System.out.print("Proposez une lettre : ");
            String nextString = scanner.next().toUpperCase();

            if (nextString.length() == 1) {
                if (nextString.charAt(0) >= 'A' && nextString.charAt(0) <= 'Z') {
                    if (!listAttemptedChar.contains(nextString.charAt(0))) {
                        result = nextString.charAt(0);
                    } else {
                        System.out.println("Vous avez déjà essayer " + nextString.charAt(0) + "!");
                    }
                } else {
                    System.out.println("Veuillez saisir une lettre alphabétique entre A et Z");
                }
            } else {
                System.out.println("Veuillez saisir seulement un char");
            }
        }
        return result;
    }

    /**
     * Return a random word from the list
     * @param random
     * @return
     */
    public static String randomWord(Random random) {
        return listOFWords[random.nextInt(listOFWords.length)];
    }

    /**
     * Return the String with the char if the word contains otherwise reduce the number of try
     * @param wordToFind
     * @param hideString
     * @param a
     * @return
     */
    public static String findCharInString(String wordToFind, String hideString, char a) {
        StringBuilder newString = new StringBuilder(hideString);
        if (wordToFind.contains(String.valueOf(a))) {
            System.out.println("Bien joué ! La lettre '" + a + "' est dans le mot.");
            for (int currentIndex = 0; currentIndex < wordToFind.length(); currentIndex++) {
                if (wordToFind.charAt(currentIndex) == a) {
                    newString.setCharAt(currentIndex, a);
                }
            }
        } else {
            numberOfTry--;
            System.out.println("Dommage ! La lettre '" + a + "' n'est pas dans le mot. Il vous reste "+ ANSI_RED + numberOfTry + RESET + " essais.");
        }
        return String.valueOf(newString);
    }

    /**
     * Function that return the string but with _
     * @param wordToHide
     * @return
     */
    public static String hideString(String wordToHide) {
        return wordToHide.replaceAll(".", "_");
    }

    /**
     * Verify if the game is finish
     * @param wordToFind
     * @param hiddenWord
     * @return
     */
    public static boolean isGameFinished(String wordToFind, String hiddenWord) {
        return wordToFind.equalsIgnoreCase(hiddenWord) || numberOfTry <= 0;
    }

    /**
     * Display all the character that the user didn't input and put it in black for the char already used
     */
    public static void displayRemainingLetter() {
        for (int charCode = 'A'; charCode <= 'Z'; charCode++) {
            if (!listAttemptedChar.contains((char) charCode)) {
                System.out.print(LIGHT_GRAY + (char) charCode + " " + RESET);
            } else {
                System.out.print((char) charCode + " ");
            }

        }
        System.out.println();
    }

    /**
     * Display the hangman
     */
    public static void displayHangman() {
        int step = 6-numberOfTry;
        System.out.println(step);

        // First Line
        System.out.println(" +---+");
        // Second Line
        System.out.println(" |   |");
        // Third Line
        System.out.print(" ");
        System.out.print(step>=1?"O":" "); // step 1: the head
        System.out.println("   |");
        // Fourth Line
        System.out.print(step>=3?"/":" "); // Step 3: Left Arm
        System.out.print(step >= 2 ? "|": " "); // step 2: body trunc
        System.out.print(step >= 4 ? "\\": " "); // step 4: right arm
        System.out.println("  |");
        // Fifth Line
        System.out.print(step>=5?"/":" "); // step 5 : left leg
        System.out.print(" ");
        System.out.print(step >=6?"\\":" "); // step 6 : right leg
        System.out.println("  |");
        // Sixth Line
        System.out.println("     |");
        // Seven Line
        System.out.println("=========");
    }

    /**
     * Main method
     * @param args
     */
    public static void main(String[] args) {
        Random random = new Random();
        Scanner scanner = new Scanner(System.in);
        String wordToFind = randomWord(random);
        String hiddenWord = hideString(wordToFind);
        while (!isGameFinished(wordToFind, hiddenWord)) {
            displayHangman();
            System.out.println("Mot Mystère :" + hiddenWord);
            displayRemainingLetter();
            char inputChar = inputLetter(scanner);
            listAttemptedChar.add(inputChar);
            hiddenWord = findCharInString(wordToFind, hiddenWord, inputChar);
        }

        if (wordToFind.equalsIgnoreCase(hiddenWord)) {
            System.out.println("Félicitations ! Vous avez gagné ! Vous avez trouvé le mot secret : " + wordToFind);
        } else {
            displayHangman();
            System.out.println("Dommage, vous avez perdu ! Le mot secret était : " + wordToFind);
        }

        scanner.close();
    }
}