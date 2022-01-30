import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Set;
import java.util.HashSet;
import java.util.List;

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

    static class Client {
        List<String> likes;
        List<String> dislikes;
    }

    static class InputParameters
    {
        Set<String> ingredients;
        Client[] clients;

        public InputParameters(Set<String> ingredients, Client[] clients)
        {
            this.ingredients = ingredients;
            this.clients = clients;
        }
    }
    
    static InputParameters getInputParameters(String pathToInputFile) throws FileNotFoundException
    {
        File inputFileObject = new File("inputs/a_an_example.in.txt");
        Scanner myReader = new Scanner(inputFileObject);

        int numClients = Integer.parseInt(myReader.nextLine());
        HashSet<String> ingredients = new HashSet<>();
        Client[] clients = new Client[numClients];

        for (int i = 0; i < numClients; ++i) {
            Client newClient = new Client();

            String[] rawLikes = myReader.nextLine().split(" ");
            clients[i].likes = Arrays.asList(Arrays.copyOfRange(rawLikes, 1, rawLikes.length));
            
            String[] rawDislikes = myReader.nextLine().split(" ");
            clients[i].dislikes = Arrays.asList(Arrays.copyOfRange(rawDislikes, 1, rawDislikes.length));

            ingredients.addAll(newClient.likes);
            ingredients.addAll(newClient.dislikes);
        }
        myReader.close();
        return new InputParameters(ingredients, clients);
    }

    static String ingredientsToInclude(InputParameters inputParams)
    {
        return "";
    }

    static void saveResult(String result)
    {
        String ouputFileName = "result.txt";

        File ouputFileObject = new File(ouputFileName);
        ouputFileObject.createNewFile();

        FileWriter myWriter = new FileWriter(ouputFileName);
        myWriter.write(result);

        myWriter.close();
    }
    
    public static void main(String[] args) {
        
        InputParameters inputParams;
        try {
            inputParams = getInputParameters("input/a_an_example.in.txt");
            String result = ingredientsToInclude(inputParams);
            saveResult(result);
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}