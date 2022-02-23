import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

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
public class OnePizzaGraph {
    private static void saveResult(String result, String outputFileNameWithPath) throws IOException {
        new File(outputFileNameWithPath).createNewFile();

        try (FileWriter myWriter = new FileWriter(outputFileNameWithPath)) {
            myWriter.write(result);
        }
    }

    private static int calculateScore(List<Ingredient> addedIngredients, List<Person> people) {
        int happyClients = 0;

        for (Person person : people) {
            boolean isClientPreferenceMet = true;

            for (Ingredient likeIngredient : person.likeIngredients) {
                if (!addedIngredients.contains(likeIngredient)) {
                    isClientPreferenceMet = false;
                    break;
                }
            }

            for (Ingredient dislikeIngredient : person.dislikeIngredients) {
                if (!isClientPreferenceMet || addedIngredients.contains(dislikeIngredient)) {
                    isClientPreferenceMet = false;
                    break;
                }
            }

            if (isClientPreferenceMet) {
                ++happyClients;
            }
        }

        return happyClients;
    }

    private static float ingredientScore(Ingredient ingredient) {
        int dislikeByPeople = ingredient.dislikeByPeople.size();
        int likeByPeople = ingredient.likeByPeople.size();

        return likeByPeople - dislikeByPeople;
    }

    private static Ingredient nextIngredientToAdd(List<Ingredient> ingredients) {
        Ingredient maxScoreIngredient = null;
        float maxScore = Integer.MIN_VALUE;
        for (Ingredient ingredient : ingredients) {
            if (!ingredient.processed) {
                float currentScore = ingredientScore(ingredient);
                if (maxScore < currentScore) {
                    maxScore = currentScore;
                    maxScoreIngredient = ingredient;
                }
            }
        }
        return maxScoreIngredient;
    }

    public static void main(String[] args) {
        try {
            String[] filesNames = {
                    "a_an_example",
                    "b_basic",
                    "c_coarse",
                    "d_difficult",
                    "e_elaborate"
            };

            for (String fileName : filesNames) {

                // Reading input and making a graph

                String pathToInputFile = "inputs/" + fileName + ".in.txt";
                String pathToOutputFile = "outputs/" + fileName + ".out.txt";

                Scanner myReader = new Scanner(new File(pathToInputFile));

                List<Person> people = new ArrayList<>();
                HashMap<String, Ingredient> ingredientsMap = new HashMap<>();

                int numClients = Integer.parseInt(myReader.nextLine());
                for (int i = 0; i < numClients; ++i) {
                    Person person = new Person();
                    people.add(person);

                    String[] rawLikes = myReader.nextLine().split(" ");
                    int numLikedIngredients = Integer.parseInt(rawLikes[0]);
                    for (int j = 1; j <= numLikedIngredients; j++) {
                        Ingredient ingredient;
                        String ingredientName = rawLikes[j];

                        if (ingredientsMap.containsKey(ingredientName)) {
                            ingredient = ingredientsMap.get(ingredientName);
                        } else {
                            ingredient = new Ingredient(ingredientName);
                            ingredientsMap.put(ingredientName, ingredient);
                        }

                        person.likeIngredients.add(ingredient);
                        ingredient.likeByPeople.add(person);
                    }

                    String[] rawDislikes = myReader.nextLine().split(" ");
                    int numDislikedIngredients = Integer.parseInt(rawDislikes[0]);
                    for (int j = 1; j <= numDislikedIngredients; j++) {
                        Ingredient ingredient;
                        String ingredientName = rawDislikes[j];

                        if (ingredientsMap.containsKey(ingredientName)) {
                            ingredient = ingredientsMap.get(ingredientName);
                        } else {
                            ingredient = new Ingredient(ingredientName);
                            ingredientsMap.put(ingredientName, ingredient);
                        }

                        person.dislikeIngredients.add(ingredient);
                        ingredient.dislikeByPeople.add(person);
                    }
                }

                myReader.close();

                List<Ingredient> ingredients = new ArrayList<>(ingredientsMap.values());

                // Actual logic

                List<Ingredient> addedIngredients = new ArrayList<>();
                int maxScore = Integer.MIN_VALUE;
                StringBuilder result = new StringBuilder();
                for (int i = 0; i < ingredients.size(); ++i) {
                    Ingredient ingredient = nextIngredientToAdd(ingredients);

                    addedIngredients.add(ingredient);
                    int currentScore = calculateScore(addedIngredients, people);
                    if (maxScore <= currentScore) {
                        maxScore = currentScore;
                        result.append(" ");
                        result.append(ingredient.name);
                        ingredient.processed(true);
                    } else {
                        addedIngredients.remove(ingredient);
                        ingredient.processed(false);
                    }
                }

                result.insert(0, addedIngredients.size());

                saveResult(result.toString(), pathToOutputFile);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}