import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Plane {
    /**
     * Aircraft program list
     */
    protected static String[] programmes = {"A320", "A400M", "A380", "A300"};
    /**
     * List of the different aircraft phases
     */
    protected static String[] phases = {"etudeFaisabilite", "conception", "definition", "construction", "enService", "cloture"};
    /**
     * List of the different types of aircraft
     */
    protected static String[] types = {"fret", "transport", "passager", "militaire", "avionsAffaires"};
    /**
     * Aircraft dictionary with the integer ID as the key, followed by program ID, phase ID, and type IDs.
     */
    protected static Map<Integer, int[]> planes = new HashMap<>();

    /**
     * Display a plane drawing
     */
    public static void displayDrawingPlane(){
        System.out.println("               " + "      __!__");
        System.out.println("               " + "^----o-(_)-o----^");
    }

    /**
     * Display a plane by is id
     *
     * @param idPlane
     */
    public static void displayPlane(int idPlane) {
        int colId = 15; // Identifiant
        int colProgramme = 10; // Programme
        int colPhase = 20; // Phase
        int colType = 15; // Type

        System.out.println(Main.formatString("Identifiant", colId) + " | " + Main.formatString("Programme", colProgramme) + " | " + Main.formatString("Phase", colPhase) + " | " + Main.formatString("Type", colType));
        if (planes.containsKey(idPlane)) {
            int[] plane = planes.get(idPlane);
            String programme = programmes[plane[0]];
            String phase = phases[plane[1]];
            String type = types[plane[2]];
            System.out.println(Main.formatString(String.valueOf(idPlane), colId) + " | " + Main.formatString(programme, colProgramme) + " | " + Main.formatString(phase, colPhase) + " | " + Main.formatString(type, colType));
        } else {
            System.out.println("Impossible de trouver cette avion " + idPlane + "!");
        }
    }

    /**
     * Displays planes by their id
     *
     * @param idPlanes
     */
    public static void displayPlane(List<Integer> idPlanes) {
        int colId = 15; // Identifiant
        int colProgramme = 10; // Programme
        int colPhase = 20; // Phase
        int colType = 15; // Type

        System.out.println(Main.formatString("Identifiant", colId) + " | " + Main.formatString("Programme", colProgramme) + " | " + Main.formatString("Phase", colPhase) + " | " + Main.formatString("Type", colType));
        for (int idPlane : idPlanes) {
            if (planes.containsKey(idPlane)) {
                int[] plane = planes.get(idPlane);
                String programme = programmes[plane[0]];
                String phase = phases[plane[1]];
                String type = types[plane[2]];
                System.out.println(Main.formatString(String.valueOf(idPlane), colId) + " | " + Main.formatString(programme, colProgramme) + " | " + Main.formatString(phase, colPhase) + " | " + Main.formatString(type, colType));
            } else {
                System.out.println("Impossible de trouver cette avion " + idPlane + "!");
            }
        }
    }

    /**
     * Generate x planes
     *
     * @param numberOfPlane
     * @param random
     */
    public static void generateRandomPlane(int numberOfPlane, Random random) {
        int numberOfPlaneGenerated = 0;
        while (numberOfPlaneGenerated < numberOfPlane) {
            int randomId = random.nextInt(10000);
            if (!planes.containsKey(randomId)) {
                int[] paramPlane = new int[3];
                paramPlane[0] = random.nextInt(programmes.length);
                paramPlane[1] = random.nextInt(phases.length);
                paramPlane[2] = random.nextInt(types.length);
                planes.put(randomId, paramPlane);
                numberOfPlaneGenerated++;
            }
            generateRandomPiecePlane(random, randomId, random.nextInt(10));
        }
    }

    /**
     * Generate a list of Pieces for the plane
     *
     * @param random
     * @param idPlane
     * @param numberPiece
     */
    public static void generateRandomPiecePlane(Random random, int idPlane, int numberPiece) {
        ArrayList<Object[]> parts = new ArrayList<>();
        for (int currentPiece = 0; currentPiece < numberPiece; currentPiece++) {
            String[] randomPiece = Part.parts.get(random.nextInt(Part.parts.size()));
            int price = random.nextInt(1000000);
            Object[] piece = new Object[2];
            piece[0] = randomPiece;
            piece[1] = price;
            parts.add(piece);
        }
        Main.partsPerPlane.put(idPlane, parts);
    }
}
