import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class MentorshipAndTeamwork {
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