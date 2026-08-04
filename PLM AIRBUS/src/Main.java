import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {
    /**
     * Liste de programme pour les avions
     */
    protected static String[] programmes = {"A320", "A400M", "A380", "A300"};
    /**
     * Liste des differentes phases des avions
     */
    protected static String[] phases = {"etudeFaisabilite", "conception", "definition", "construction", "enService", "cloture"};
    /**
     * Liste des differentes types d'avions
     */
    protected static String[] types = {"fret", "transport", "passager", "militaire", "avionsAffaires"};
    /**
     * Dictionnaire d'avion avec l'id integer comme clé puis idProgramme idPhase idTypes
     */
    protected static Map<Integer, int[]> planes = new HashMap<>();
    /**
     * Dictionnaire contenant comme clé l'id d'un avion avec une liste Object contenant un String Nom de la piece, un String category et un float price
     */
    protected static Map<Integer, ArrayList<Object[]>> piecesPerPlane = new HashMap<>();

    /**
     * Returns a string of the desired length, truncated or padded with spaces.
     * @param text
     * @param len
     * @return
     */
    public static String formatString(String text, int len) {
        if (text.length() > len) {
            return text.substring(0, len);
        } else if (text.length() < len) {
            String pattern = "%-" + len + "s";
            return String.format(pattern, text);
        } else {
            return text;
        }
    }

    /**
     * Display a plane by is id
     * @param idPlane
     */
    public static void displayPlane(int idPlane) {
        int colId = 15; // Identifiant
        int colProgramme = 10; // Programme
        int colPhase = 20; // Phase
        int colType = 15; // Type

        System.out.println(formatString("Identifiant", colId) + " | " + formatString("Programme", colProgramme) + " | " + formatString("Phase", colPhase) + " | " + formatString("Type", colType));
        if (planes.containsKey(idPlane)) {
            int[] plane = planes.get(idPlane);
            String programme = programmes[plane[0]];
            String phase = phases[plane[1]];
            String type = types[plane[2]];
            System.out.println(formatString(String.valueOf(idPlane), colId) + " | " + formatString(programme, colProgramme) + " | " + formatString(phase, colPhase) + " | " + formatString(type, colType));
        } else {
            System.out.println("Impossible de trouver cette avion " + idPlane + "!");
        }
    }

    /**
     * Displays planes by their id
     * @param idPlanes
     */
    public static void displayPlane(List<Integer> idPlanes) {
        int colId = 15; // Identifiant
        int colProgramme = 10; // Programme
        int colPhase = 20; // Phase
        int colType = 15; // Type

        System.out.println(formatString("Identifiant", colId) + " | " + formatString("Programme", colProgramme) + " | " + formatString("Phase", colPhase) + " | " + formatString("Type", colType));
        for (int idPlane : idPlanes) {
            if (planes.containsKey(idPlane)) {
                int[] plane = planes.get(idPlane);
                String programme = programmes[plane[0]];
                String phase = phases[plane[1]];
                String type = types[plane[2]];
                System.out.println(formatString(String.valueOf(idPlane), colId) + " | " + formatString(programme, colProgramme) + " | " + formatString(phase, colPhase) + " | " + formatString(type, colType));
            } else {
                System.out.println("Impossible de trouver cette avion " + idPlane + "!");
            }
        }
    }

    /**
     * Create a piece with user input
     * @param scanner
     * @param idAvion
     */
    public static void inputPiece(Scanner scanner, int idAvion) {
        System.out.println("Veuillez saisir le nom de la piece");
        String namePiece = scanner.nextLine();
        System.out.println("Veuillez saisir la categorie de la piece");
        String categoryPiece = scanner.nextLine();
        System.out.println("Veuillez saisir le prix de la piece");
        boolean isInputFloat = false;
        float pricePiece = 0;
        while (!isInputFloat) {
            if (scanner.hasNextFloat()) {
                isInputFloat = true;
                pricePiece = scanner.nextFloat();
            } else {
                System.out.println("Un float est attendu ici");
                scanner.next();
            }
        }
        Object[] res = new Object[3];
        res[0] = namePiece;
        res[1] = categoryPiece;
        res[2] = pricePiece;

        if (piecesPerPlane.containsKey(idAvion)) {
            piecesPerPlane.get(idAvion).add(res);
        } else {
            ArrayList<Object[]> listPieces = new ArrayList<>();
            listPieces.add(res);
            piecesPerPlane.put(idAvion, listPieces);
        }


    }

    /**
     * Remove a piece from a plane
     * @param scanner
     * @param idPlane
     */
    public static void removePiece(Scanner scanner, int idPlane) {
        System.out.println(formatString("id", 4) + " | " + "Name");
        for (int i = 0; i < piecesPerPlane.get(idPlane).size(); i++) {
            System.out.println(formatString(String.valueOf(i), 4) + " | " + piecesPerPlane.get(idPlane).get(i)[0]);
        }
        System.out.println("Quel piece souhaitez vous supprimez");
        if (scanner.hasNextInt()) {
            int idPieceToDelete = scanner.nextInt();
            if (idPieceToDelete >= 0 && idPieceToDelete < piecesPerPlane.get(idPlane).size()) {
                piecesPerPlane.get(idPlane).remove(idPieceToDelete);
            }

        }
    }

    /**
     * Display all piece of a plane and ask the user if they want to add or delete a piece
     * @param scanner
     * @param idPlane
     */
    public static void displayAllPiece(Scanner scanner, int idPlane) {
        int colName = 15;
        int colCategory = 15;
        int colPrix = 6;
        if (piecesPerPlane.containsKey(idPlane)) {
            System.out.println("Affichage des pieces");
            System.out.println(formatString("Name", colName) + " | " + formatString("Categorie", colCategory) + " | " + formatString("Prix", colPrix));
            for (Object[] str : piecesPerPlane.get(idPlane)) {
                System.out.print(formatString(String.valueOf(str[0]), colName) + " | ");
                System.out.print(formatString(String.valueOf(str[1]), colName) + " | ");
                System.out.println(formatString(String.valueOf(str[2]), colName));
            }
        } else {
            System.out.println("Aucune piece trouver pour cette avion");
        }
        System.out.println("Que souhaitez vous faire ?");
        System.out.println("1- Ajouter une piece a l'avion");
        if (piecesPerPlane.containsKey(idPlane)) {
            if (!piecesPerPlane.get(idPlane).isEmpty()) {
                System.out.println("2- Retirer une piece a l'avion");
            }
        }
        int saisieUtilisateur = 0;
        while (saisieUtilisateur == 0) {
            if (scanner.hasNextInt()) {
                saisieUtilisateur = scanner.nextInt();
            }
            scanner.nextLine();
        }
        switch (saisieUtilisateur) {
            case 1:
                inputPiece(scanner, idPlane);
                break;
            case 2:
                if (piecesPerPlane.containsKey(idPlane) && !piecesPerPlane.get(idPlane).isEmpty()) {
                    removePiece(scanner, idPlane);
                    break;
                }

                System.out.println("Cet avion ne contient pas de piece a supprimer");
                break;
            default:
                System.out.println("Saisir un nombre du tableau");
                break;
        }
    }

    /**
     * Displays all the plane
     */
    public static void displayAllPlane() {
        displayPlane(new ArrayList<>(planes.keySet()));
    }

    /**
     * Find all the planes that have the search String in their plane
     * @param search
     * @return
     */
    public static List<Integer> searchPlane(String search) {
        ArrayList<Integer> res = new ArrayList<>();
        for (int idPlane : planes.keySet()) {
            int[] plane = planes.get(idPlane);
            String stringIdPlane = String.valueOf(idPlane);
            String programme = programmes[plane[0]];
            String phase = phases[plane[1]];
            String type = types[plane[2]];
            if (stringIdPlane.toUpperCase().contains(search.toUpperCase()) || programme.toUpperCase().contains(search.toUpperCase()) || phase.toUpperCase().contains(search.toUpperCase()) || type.toUpperCase().contains(search.toUpperCase())) {
                res.add(idPlane);
            }
        }
        return res;
    }

    /**
     * Main program ask what the user want to do
     * @param args
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int[] plane1 = {1, 1, 1};
        int[] plane2 = {2, 2, 2};
        Object[] piece1 = {"Aile droite 1425", "Aile droite", 5.05};
        planes.put(8742, plane1);
        planes.put(8772, plane2);
        ArrayList<Object[]> plane1Piece = new ArrayList<>();
        plane1Piece.add(piece1);
        piecesPerPlane.put(8772, plane1Piece);
        boolean quitProgram = false;
        while (!quitProgram) {
            System.out.println("Que souhaitez vous effectuez");
            System.out.println("1- Afficher tous les avions");
            System.out.println("2- Afficher une liste d'avion a partir d'une recherce");
            System.out.println("3- Voir les pieces pour un avion");
            System.out.println("4- Arreter le programme");

            if (scanner.hasNextInt()) {
                int inputInt = scanner.nextInt();
                switch (inputInt) {
                    case 1:
                        displayAllPlane();
                        break;
                    case 2:
                        System.out.println("Veuillez entrez votre saisie");
                        String search = scanner.next();
                        List<Integer> resSearch = searchPlane(search);
                        displayPlane(resSearch);
                        break;
                    case 3:
                        displayAllPlane();
                        System.out.println("Veuillez entrez l'identifiant de l'avion");
                        if (scanner.hasNextInt()) {
                            int planeKey = scanner.nextInt();
                            if (planes.containsKey(planeKey)) {
                                displayAllPiece(scanner, planeKey);
                            } else {
                                System.out.println("Nous n'avons pas trouvé d'avions avec cet identifiant " + planeKey);
                            }
                        } else {
                            System.out.println("Un identifiant d'avion est attendu");
                        }
                        break;
                    case 4:
                        quitProgram = true;
                        break;
                    default:
                        System.out.println("Votre nombre ne se trouve pas dans la liste");
                        break;
                }
            } else {
                System.out.println("Veuillez saisir un nombre");
            }
        }
        scanner.close();
    }
}