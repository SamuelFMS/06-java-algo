import java.util.ArrayList;
import java.util.Arrays;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    /**
     * List of the names of the guest
     */
    public static String[] guestName = {"Albert", "Bénédicte", "Christophe", "Delphine", "Edouard", "Françoise", "Gaston", "Heloise"};

    /**
     ** List of the friends guests
     */
    public static int[][] guestRelation = {{1, 4, 5}, // Albert
            {2, 4, 5}, // Bénédicte
            {1, 3, 4, 5}, // Christophe
            {0, 1, 4, 5, 7}, // Delphine
            {5}, // Edouard
            {4}, // Françoise
            {1, 2, 4, 5, 7}, // Gaston
            {1, 3, 4, 5, 6} // Heloise
    };

    /**
     * Display who is friend with who
     */
    public static void displayRelation() {
        for (int indexGuest = 0; indexGuest < guestRelation.length; indexGuest++) {
            System.out.print(guestName[indexGuest] + " connais: ");
            for (int indexRelationGuest=0; indexRelationGuest<guestRelation[indexGuest].length; indexRelationGuest++){
                System.out.print(guestName[guestRelation[indexGuest][indexRelationGuest]]);
                if(indexRelationGuest < guestRelation[indexGuest].length -1) {
                    System.out.print(", ");
                }
            }
            System.out.println();
        }
    }

    /**
     * Find all the celebrities
     * @return
     */
    public static ArrayList<Integer> findCelebrities(){
        // Creation de la liste avec tous les invité comme index
        ArrayList<Integer> listGuest = new ArrayList<Integer>();
        for(int x = 0; x < guestRelation.length; x++){
            listGuest.add(x);
        }
        /**
         * First step is to verify that everyone knows the celebrity.
         */
        ArrayList<Integer> listOfCelebrity = (ArrayList<Integer>) listGuest.clone();
        for(int guestIndice: listGuest) {
            for(int indiceChecking : listGuest){
                if(!Arrays.stream(guestRelation[guestIndice]).anyMatch(guest -> (guest == indiceChecking || indiceChecking == guestIndice))){
                    if(listOfCelebrity.contains(indiceChecking)) {
                        System.out.println(guestName[indiceChecking] + " ne peux pas etre car " + guestName[guestIndice] + " ne le connais pas");
                        listOfCelebrity.remove(Integer.valueOf(indiceChecking));
                    }
                }
            }
        }

        /**
         * Second step verify that the celebrities know only each other.
         */
        for(int celebrityIndex: listOfCelebrity) {
            for(int relationIndex: guestRelation[celebrityIndex]) {
                if(!listOfCelebrity.contains(relationIndex)) {
                    System.out.println(guestName[celebrityIndex] + " est amis avec " + guestName[relationIndex] + " il ne peux pas y'avoir de célébrité ducoup");
                    return new ArrayList<>();
                }
            }
        }
        return listOfCelebrity;
    }

    /**
     * Main program
     * @param args
     */
    public static void main(String[] args) {
        displayRelation();
        System.out.println("");
        System.out.println("=========================");
        ArrayList<Integer> celebrities = findCelebrities();
        for(int cel: celebrities){
            System.out.println(guestName[cel] + " est une célébrité.");
        }
    }
}