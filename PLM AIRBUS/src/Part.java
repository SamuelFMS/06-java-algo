import java.util.ArrayList;
import java.util.Scanner;

public class Part {
    /**
     * List of all the part that have been make
     * part[0] = partName;
     * part[1] = Category;
     */
    protected static ArrayList<String[]> parts = new ArrayList<>();

    /**
     * Create a "object" part
     *
     * @param partName
     * @param Category
     * @return
     */
    public static String[] createAObjectPiece(String partName, String Category) {
        String[] part = new String[2];
        part[0] = partName;
        part[1] = Category;
        return part;
    }

    /**
     * Init list of parts
     */
    public static void initListOfPiece() {
        parts.add(createAObjectPiece("Turbofan Engine", "Propulsion"));
        parts.add(createAObjectPiece("Landing Gear", "Structure"));
        parts.add(createAObjectPiece("Weather Radar", "Avionics"));
        parts.add(createAObjectPiece("Flight Control Computer", "Avionics"));
        parts.add(createAObjectPiece("Passenger Seat", "Cabin Interior"));
        parts.add(createAObjectPiece("LED Cabin Light", "Electrical"));
        parts.add(createAObjectPiece("Hydraulic Pump", "Hydraulics"));
        parts.add(createAObjectPiece("Wing Flap", "Aerodynamics"));
        parts.add(createAObjectPiece("Fuel Flow Sensor", "Fuel System"));
        parts.add(createAObjectPiece("Oxygen Mask", "Safety"));
    }

    public static void displayAllPieces() {
        int numero = 0;
        int colNumero = 6;
        int colNamePart = 20;
        int colCategory = 20;
        /*
            Header Table
         */
        System.out.print(Main.formatString("Numero", colNumero) + " | ");
        System.out.print(Main.formatString("Name Part", colNamePart) + " | ");
        System.out.println(Main.formatString("Category", colCategory));

        /*
            Body Table
         */
        for (String[] part : parts) {
            numero++;
            System.out.print(Main.formatString(String.valueOf(numero), colNumero) + " | ");
            System.out.print(Main.formatString(part[0], colNamePart) + " | ");
            System.out.println(Main.formatString(part[1], colCategory) + " | ");
        }
    }

    /**
     * Delete a part from parts
     * @param scanner
     */
    public static void deletePart(Scanner scanner){
        displayAllPieces();
        System.out.println("");
        Integer pieceIdToDelete = Main.inputIntegerBetween(scanner,
                "Quelle pièce souhaitez-vous supprimer ? (Tapez 'q' pour annuler)"
                ,1,
                parts.size());
        if(pieceIdToDelete != null){
            parts.remove(pieceIdToDelete-1);
        }
    }

    /**
     * @param scanner
     * Create a part with user input
     */
    public static void inputCreatePart(Scanner scanner) {
        System.out.println("Veuillez saisir le nom de la piece");
        String namePiece = scanner.nextLine();
        while (namePiece.isEmpty()) {
            namePiece = scanner.nextLine();
        }

        if(!namePiece.equalsIgnoreCase("q")) {
            System.out.println("Veuillez saisir la categorie de la piece");
            String categoryPiece = scanner.nextLine();
            if(!categoryPiece.equalsIgnoreCase("q")) {
                parts.add(createAObjectPiece(namePiece, categoryPiece));
            }
        }
    }
}
