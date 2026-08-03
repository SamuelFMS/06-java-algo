import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Logger;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.

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
    public static void createAPlane(Scanner scanner) {

    }

    public static void displayPlane(int idPlane){
        int colId = 15; // Identifiant
        int colProgramme = 10; // Programme
        int colPhase = 20; // Phase
        int colType = 15; // Type

        System.out.println(formatString("Identifiant",colId) + " | " +
                formatString("Programme", colProgramme) + " | " +
                formatString("Phase", colPhase) + " | " +
                formatString("Type",colType));
        if(planes.containsKey(idPlane)) {
            int[] plane = planes.get(idPlane);
            String programme = programmes[plane[0]];
            String phase = phases[plane[1]];
            String type = types[plane[2]];
            System.out.println(formatString(String.valueOf(idPlane) , colId) + " | " +
                    formatString(programme, colProgramme) + " | " +
                    formatString(phase, colPhase) + " | " +
                    formatString(type,colType));
        }
        else {
            System.out.println("Impossible de trouver cette avion " + idPlane + "!");
        }
    }

    public static void main(String[] args) {
        int[] plane1 = {1,1,1};
        int[] plane2 = {2,2,2};

        planes.put(8742, plane1);
        planes.put(8772, plane2);
        displayPlane(8742);
    }
}