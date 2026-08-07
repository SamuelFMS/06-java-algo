import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    /**
     * Dictionary using an aircraft ID as the key, with a list of Objects containing a String (part name), a String (category), and a float (price) \n
     * Object[0] -> Part details -> [0]: part name; [1]: "part category"
     * Object[1] -> price (float)
     */
    protected static Map<Integer, ArrayList<Object[]>> partsPerPlane = new HashMap<>();

    /**
     * Returns a string of the desired length, truncated or padded with spaces.
     *
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
     * Add a part to a plane
     *
     */
    public static void addAPieceToPlane(Scanner scanner, int idAvion) {
        Part.displayAllPieces();
        Integer idPieceToAddToPlace = Main.inputIntegerBetween(scanner, "Quel pièce souhaitez vous ajoutez? ", 0, Part.parts.size());
        if (idPieceToAddToPlace != null) {
            if (idPieceToAddToPlace == 0) {
                System.out.println("Creation dune toute nouvelle piece");
            } else {
                Integer price = Main.inputIntegerBetween(scanner, "Quel est le prix de cette piece ? ", 0, Integer.MAX_VALUE);
                if (price != null) {
                    Object[] part = new Object[2];
                    part[0] = Part.parts.get(idPieceToAddToPlace - 1);
                    part[1] = price;
                    if (partsPerPlane.containsKey(idAvion)) {
                        partsPerPlane.get(idAvion).add(part);
                    } else {
                        ArrayList<Object[]> listPiece = new ArrayList<>();
                        listPiece.add(part);
                        partsPerPlane.put(idAvion, listPiece);
                    }
                }
            }
        }
    }

    /**
     * Remove a part from a plane
     *
     * @param scanner
     * @param idPlane
     */
    public static void removePieceFromPlane(Scanner scanner, int idPlane) {
        System.out.println(formatString("id", 4) + " | " + "Name");
        for (int i = 0; i < partsPerPlane.get(idPlane).size(); i++) {
            String[] part = (String[]) partsPerPlane.get(idPlane).get(i)[0];
            System.out.println(formatString(String.valueOf(i), 4) + " | " + part[0]);
        }
        System.out.println("Quel piece souhaitez vous supprimez");
        if (scanner.hasNextInt()) {
            int idPieceToDelete = scanner.nextInt();
            if (idPieceToDelete >= 0 && idPieceToDelete < partsPerPlane.get(idPlane).size()) {
                String[] part = (String[]) partsPerPlane.get(idPlane).get(idPieceToDelete)[0];
                String nomPiece = part[0];
                partsPerPlane.get(idPlane).remove(idPieceToDelete);
                System.out.println("La piece " + nomPiece + " a bien été supprimer a l'avion " + idPlane);
            }

        }
    }

    /**
     * Repeat a symbol by times
     *
     * @param symbol
     * @param times
     * @return
     */
    public static String repeat(String symbol, int times) {
        return Stream.generate(() -> symbol).limit(times).collect(Collectors.joining());
    }

    /**
     * Display all part of a plane and ask the user if they want to add or delete a part
     *
     * @param scanner
     * @param idPlane
     */
    public static void displayPieceForPlane(Scanner scanner, int idPlane) {
        int colName = 15;
        int colCategory = 15;
        int colPrice = 9;
        boolean displayingPieceForPlane = true;
        while (displayingPieceForPlane) {
            if (partsPerPlane.containsKey(idPlane)) {
                System.out.println("============================================================");
                System.out.println("\uD83D\uDCE6 PIÈCES DÉTACHÉES – AVION #" + idPlane);
                System.out.println("============================================================");
                System.out.println(formatString("Nom de la pièce", colName) + " | " + formatString("Catégorie", colCategory) + " | " + formatString("Prix", colPrice));
                System.out.println(repeat("-", colName) + "-+-" + repeat("-", colCategory) + "-+-" + repeat("-", colPrice));
                for (Object[] str : partsPerPlane.get(idPlane)) {
                    String[] part = (String[]) str[0];
                    int price = (int) str[1];
                    System.out.print(formatString(String.valueOf(part[0]), colName) + " | ");
                    System.out.print(formatString(String.valueOf(part[1]), colName) + " | ");
                    System.out.println(formatString(String.valueOf(price), colName));
                }
            } else {
                System.out.println("Aucune piece trouver pour cette avion");
            }
            System.out.println();
            System.out.println("Que souhaitez vous faire ?");
            System.out.println("1- Ajouter une piece a l'avion");
            if (partsPerPlane.containsKey(idPlane)) {
                if (!partsPerPlane.get(idPlane).isEmpty()) {
                    System.out.println("2- Retirer une piece a l'avion");
                }
            }
            Integer inputUser = inputIntegerBetween(scanner, "Choix:", 1, 2);
            if (inputUser == null) {
                displayingPieceForPlane = false;
            } else {
                switch (inputUser) {
                    case 1:
                        addAPieceToPlane(scanner, idPlane);
                        break;
                    case 2:
                        if (partsPerPlane.containsKey(idPlane) && !partsPerPlane.get(idPlane).isEmpty()) {
                            removePieceFromPlane(scanner, idPlane);
                            break;
                        }
                        System.out.println("Cet avion ne contient pas de piece a supprimer");
                        break;
                    default:
                        System.out.println("Saisir un nombre du tableau");
                        break;
                }
            }
        }
    }

    /**
     * Displays all the plane
     */
    public static void displayAllPlane() {
        Plane.displayPlane(new ArrayList<>(Plane.planes.keySet()));
    }

    /**
     * Find all the planes that have the search String in their plane
     *
     * @param search
     * @return
     */
    public static List<Integer> searchPlane(String search) {
        ArrayList<Integer> res = new ArrayList<>();
        for (int idPlane : Plane.planes.keySet()) {
            int[] plane = Plane.planes.get(idPlane);
            String stringIdPlane = String.valueOf(idPlane);
            String programme = Plane.programmes[plane[0]];
            String phase = Plane.phases[plane[1]];
            String type = Plane.types[plane[2]];
            if (stringIdPlane.toUpperCase().contains(search.toUpperCase()) || programme.toUpperCase().contains(search.toUpperCase()) || phase.toUpperCase().contains(search.toUpperCase()) || type.toUpperCase().contains(search.toUpperCase())) {
                res.add(idPlane);
            }
        }
        return res;
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
    public static Integer inputIntegerBetween(Scanner scanner, String message, int min, int max) {
        Integer res = null;
        while (res == null) {
            System.out.println(message);
            String stringInput = scanner.next();
            if (stringInput.equals("q")) {
                return null;
            }
            try {
                res = Integer.valueOf(stringInput);
            } catch (Exception e) {
                System.out.println("Veuillez entrez un nombre valide");
            }
            if (res != null) {
                if (res < min || res > max) {
                    res = null;
                    System.out.println("Veuillez entrez un nombre entre " + min + " et " + max);
                }
            }
        }
        return res;
    }

    /**
     * Main program ask what the user want to do
     *
     * @param args
     */
    public static void main(String[] args) {
        Random random = new Random();
        Scanner scanner = new Scanner(System.in);

        System.out.println("==================================================");
        System.out.println("  BIENVENUE DANS VOTRE GESTIONNAIRE D'AVIONS");
        Plane.displayDrawingPlane();
        System.out.println("==================================================");
        System.out.println();
        Part.initListOfPiece();
        Plane.generateRandomPlane(10, random);

        boolean quitProgram = false;
        while (!quitProgram) {
            System.out.println("Que souhaitez vous effectuez");
            System.out.println("1- Afficher tous les avions");
            System.out.println("2- Afficher une liste d'avion a partir d'une recherce");
            System.out.println("3- Voir les pieces pour un avion");
            System.out.println("q- Arreter le programme");

            Integer inputInt = inputIntegerBetween(scanner, "Veuillez entrez votre choix: ", 1, 3);
            if (inputInt == null) {
                quitProgram = true;
                break;
            } else {
                switch (inputInt) {
                    case 1:
                        displayAllPlane();
                        break;
                    case 2:
                        System.out.println("Veuillez entrez votre saisie");
                        String search = scanner.next();
                        List<Integer> resSearch = searchPlane(search);
                        Plane.displayPlane(resSearch);
                        break;
                    case 3:
                        displayAllPlane();
                        Integer planeKey = inputIntegerBetween(scanner, "Veuillez entrez l'identifiant de l'avion", 0, Integer.MAX_VALUE);
                        if (planeKey != null) {
                            if (Plane.planes.containsKey(planeKey)) {
                                displayPieceForPlane(scanner, planeKey);
                            } else {
                                System.out.println("Nous n'avons pas trouvé d'avions avec cet identifiant " + planeKey);
                            }
                        }
                        break;
                    default:
                        System.out.println("Votre nombre ne se trouve pas dans la liste");
                        break;
                }
            }
        }
        scanner.close();
    }
}