import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import java.util.Random;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static String[] listOFWords = {
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
    public static String randomWord(Random random){
        return listOFWords[random.nextInt(listOFWords.length)];
    }

    public static String hideString(String wordToHide){
        return wordToHide.replaceAll(".", "_");
    }
    public static void main(String[] args) {
        Random random = new Random();
        String wordToFind = randomWord(random);
        System.out.println(hideString(wordToFind));
    }
}