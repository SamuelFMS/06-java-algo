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
            Fist line special
         */
        System.out.print(Main.formatString("0", colNumero) + " | ");
        System.out.println("Creer une toute nouvelle piece");

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
     * @param scanner
     * @param idAvion
     * @deprecated This method is obsolete and must be remade.
     * Create a part with user input
     */
    @Deprecated
    public static void inputCreatePiece(Scanner scanner, int idAvion) {
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

        if (Main.partsPerPlane.containsKey(idAvion)) {
            Main.partsPerPlane.get(idAvion).add(res);
        } else {
            ArrayList<Object[]> listPieces = new ArrayList<>();
            listPieces.add(res);
            Main.partsPerPlane.put(idAvion, listPieces);
        }


    }
}
