import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {
    protected static String[] programmes = {"A320", "A400M", "A380", "A300"};
    protected static String[] phases = {"etudeFaisabilite", "conception", "definition", "construction", "enService", "cloture"};
    protected static String[] types = {"fret", "transport", "passager", "militaire", "avionsAffaires"};
    protected static Map<Integer, int[]> planes = new HashMap<>();

    public static String formatString(String text, int len) {
        if (text.length() > len) {
            return text.substring(0,len);
        }
        else if (text.length() < len) {
            String pattern = "%-"+len+"s";
            return String.format(pattern, text);
        }
        else {
            return text;
        }
    }

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

    public static void displayPlane(List<Integer> idPlanes){
        int colId = 15; // Identifiant
        int colProgramme = 10; // Programme
        int colPhase = 20; // Phase
        int colType = 15; // Type

        System.out.println(formatString("Identifiant",colId) + " | " +
                formatString("Programme", colProgramme) + " | " +
                formatString("Phase", colPhase) + " | " +
                formatString("Type",colType));
        for(int idPlane: idPlanes) {
            if (planes.containsKey(idPlane)) {
                int[] plane = planes.get(idPlane);
                String programme = programmes[plane[0]];
                String phase = phases[plane[1]];
                String type = types[plane[2]];
                System.out.println(formatString(String.valueOf(idPlane), colId) + " | " +
                        formatString(programme, colProgramme) + " | " +
                        formatString(phase, colPhase) + " | " +
                        formatString(type, colType));
            } else {
                System.out.println("Impossible de trouver cette avion " + idPlane + "!");
            }
        }
    }

    public static void displayAllPlane() {
        displayPlane((List<Integer>) planes.keySet());
    }

    public static List<Integer> searchPlane(String search){
        ArrayList<Integer> res = new ArrayList<>();
        for(int idPlane: planes.keySet()) {
            int[] plane = planes.get(idPlane);
            String stringIdPlane = String.valueOf(idPlane);
            String programme = programmes[plane[0]];
            String phase = phases[plane[1]];
            String type = types[plane[2]];
            if(stringIdPlane.toUpperCase().contains(search.toUpperCase()) ||
            programme.toUpperCase().contains(search.toUpperCase()) ||
            phase.toUpperCase().contains(search.toUpperCase()) ||
            type.toUpperCase().contains(search.toUpperCase())) {
                res.add(idPlane);
            }
        }
        return res;
    }


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int[] plane1 = {1,1,1};
        int[] plane2 = {2,2,2};

        planes.put(8742, plane1);
        planes.put(8772, plane2);
        System.out.println("Que souhaitez vous effectuez");
        System.out.println("1- Afficher tous les avions");
        System.out.println("2- Afficher une liste d'avion a partir d'une recherce");


        if(scanner.hasNextInt()) {
            int inputInt = scanner.nextInt();
            switch(inputInt) {
                case 1:
                    displayAllPlane();
                    break;
                case 2:
                    System.out.println("Veuillez entrez votre saisie");
                    String search = scanner.next();
                    List<Integer> resSearch = searchPlane(search);
                    displayPlane(resSearch);
                    break;
                default:
                    System.out.println("Votre nombre ne se trouve pas dans la liste");
                    break;
            }
        } else {
            System.out.println("Veuillez saisir un nombre");
        }
        scanner.close();
    }
}