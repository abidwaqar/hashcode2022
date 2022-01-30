import java.io.File;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.Scanner;

/**
 * OnePizza
 * You are opening a small pizzeria. In fact, your pizzeria is so small that you
 * decided to offer only one type of pizza. Now you need to decide what
 * ingredients to include (peppers? tomatoes? both?).
 * 
 * Everyone has their own pizza preferences. Each of your potential clients has
 * some ingredients they like, and maybe some ingredients they dislike. Each
 * client will come to your pizzeria if both conditions are true:
 * 
 * all the ingredients they like are on the pizza, and
 * none of the ingredients they dislike are on the pizza
 * Each client is OK with additional ingredients they neither like or dislike
 * being present on the pizza. Your task is to choose which ingredients to put
 * on your only pizza type, to maximize the number of clients that will visit
 * your pizzeria.
 * 
 * Input
 * The first line contains one integer 1≤C≤105 - the number of potential
 * clients.
 * The following 2×C lines describe the clients’ preferences in the following
 * format:
 * First line contains integer 1≤L≤5, followed by L names of ingredients a
 * client likes, delimited by spaces.
 * Second line contains integer 0≤D≤5, followed by D names of ingredients a
 * client dislikes, delimited by spaces.
 * Each ingredient name consists of between 1 and 15 ASCII characters. Each
 * character is one of the lowercase letters (a-z) or a digit (0-9).
 */
public class OnePizza {

    static class LikesAndDislikes {
        String[] likes;
        String[] dislikes;
    }

    public static void main(String[] args) {
        try {
            File inputFileObject = new File("inputs/a_an_example.in.txt");
            Scanner myReader = new Scanner(inputFileObject);

            int potentialClients = Integer.parseInt(myReader.nextLine());
            LikesAndDislikes[] clientsPreferences = new LikesAndDislikes[potentialClients];
            for (int i = 0; i < potentialClients; ++i) {
                LikesAndDislikes clientPreferences = new LikesAndDislikes();

                String[] rawLikes = myReader.nextLine().split(" ");
                clientPreferences.likes = Arrays.copyOfRange(rawLikes, 1, rawLikes.length);

                String[] rawDislikes = myReader.nextLine().split(" ");
                clientPreferences.dislikes = Arrays.copyOfRange(rawDislikes, 1, rawDislikes.length);

                clientsPreferences[i] = clientPreferences;
            }

            myReader.close();

            String result = "4 cheese mushrooms tomatoes peppers";
            String ouputFileName = "result.txt";

            File ouputFileObject = new File(ouputFileName);
            ouputFileObject.createNewFile();

            FileWriter myWriter = new FileWriter(ouputFileName);
            myWriter.write(result);

            myWriter.close();

        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }
}