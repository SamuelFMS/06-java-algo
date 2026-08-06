import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import java.util.Random;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static int numberOfTry = 6;
    protected static String[] listOFWords = {
            "ACCORD", "ACTEUR", "AGENDA", "AGNEAU", "ALBUM", "ALERTE", "AMANDE", "AMICAL", "ANIMAL", "ANANAS",
            "ARBRE", "ARDOISE", "ARGENT", "ARMOIRE", "ASTUCE", "AVION", "AVOCAT", "BALLE", "BANANE", "BARQUE",
            "BASSIN", "BATEAU", "BICYCLETTE", "BIJOU", "BILLET", "BOCAL", "BOUGIE", "BOUTEILLE", "BOUTON", "BRANCHE",
            "BUREAU", "CABANE", "CADEAU", "CAHIER", "CAILLOU", "CALCUL", "CAMERA", "CAMION", "CANARD", "CANYON",
            "CARNET", "CARTON", "CASQUE", "CERISE", "CHAISE", "CHALEUR", "CHAPEAU", "CHASSE", "CHATON", "CHAUSSURE",
            "CHEMISE", "CHEVAL", "CHEVEU", "CHIEN", "CHIFFRE", "CIMENT", "CINEMA", "CISEAUX", "CITRON", "CLASSE",
            "CLAVIER", "CLOCHARD", "COFFRE", "COLLE", "COLORE", "COMPTE", "COPAIN", "CORDE", "COULOIR", "COUTEAU",
            "CRAIE", "CRAYON", "CUISINE", "CUIVRE", "DANSEUR", "DAUPHIN", "DECOR", "DEFENSE", "DESSIN", "DEVINETTE",
            "DICTEE", "DIGITAL", "DINOSAURE", "DOCTEUR", "DOMINO", "DRAPEAU", "DROITE", "ECLAIR", "ECOLE", "ECRAN",
            "ECUREUIL", "EFFORT", "ENFANT", "ENIGME", "EPAULE", "EPONGE", "EQUIPE", "ESPACE", "ESPRIT", "ETOILE",
            "FACTORIE", "FAMILLE", "FANTOME", "FARINE", "FAUTEUIL", "FENETRE", "FERMIER", "FEUILLE", "FIDELE", "FILLETTE",
            "FLEUVE", "FLOCON", "FORGEUR", "FORMAT", "FRAISE", "FROMAGE", "FUSIBLE", "GARAGE", "GARCON", "GATEAU",
            "GAUCHE", "GAZON", "GIRAFE", "GLACE", "GOMME", "GOUDRON", "GRANDEUR", "GRENOUILLE", "GRIFFE", "GUITARE",
            "HABIT", "HAZARD", "HIBOU", "HISTOIRE", "HORLOGE", "HOTEL", "HUMAIN", "HUMOUR", "IMAGE", "INSECTE",
            "INVITE", "IVOIRE", "JARDIN", "JAUNE", "JOURNAL", "JUNGLE", "KANGOUROU", "KLAXON", "LABORATOIRE", "LAMPE"
    };

    public static char inputLetter(Scanner scanner) {
        char result = '\0'; // Empty char (Unicode 0)

        while (result == '\0') {
            System.out.print("Proposez une lettre : ");
            String nextString = scanner.next().toUpperCase();
            if (nextString.length() == 1) {
                if (nextString.charAt(0) >= 'A' && nextString.charAt(0) <= 'Z') {
                    result = nextString.charAt(0);
                } else {
                    System.out.println("Veuillez saisir une lettre alphabétique entre A et Z");
                }
            } else {
                System.out.println("Veuillez saisir seulement un char");
            }
        }
        return result;
    }

    public static String randomWord(Random random) {
        return listOFWords[random.nextInt(listOFWords.length)];
    }

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
            System.out.println("Dommage ! La lettre '" + a + "' n'est pas dans le mot. Il vous reste " + numberOfTry + " essais.");
        }
        return String.valueOf(newString);
    }
    public static String hideString(String wordToHide){
        return wordToHide.replaceAll(".", "_");
    }
    public static boolean isGameFinished(String wordToFind, String hiddenWord){
        return wordToFind.equalsIgnoreCase(hiddenWord) || numberOfTry <= 0;
    }

    public static void main(String[] args) {
        Random random = new Random();
        Scanner scanner = new Scanner(System.in);

        String wordToFind = randomWord(random);
        String hiddenWord = hideString(wordToFind);
        while(!isGameFinished(wordToFind, hiddenWord)) {
            System.out.println("Mot Mystère :" + hiddenWord);
            char inputChar = inputLetter(scanner);
            hiddenWord = findCharInString(wordToFind, hiddenWord, inputChar);
        }

        if(wordToFind.equalsIgnoreCase(hiddenWord)) {
            System.out.println("Félicitations ! Vous avez gagné ! Vous avez trouvé le mot secret : " + wordToFind);
        }else {
            System.out.println("Dommage, vous avez perdu ! Le mot secret était : " + wordToFind);
        }

        scanner.close();
    }
}