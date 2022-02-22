import java.util.HashSet;

public class Person {
    HashSet<Ingredient> likeIngredients;
    HashSet<Ingredient> dislikeIngredients;

    public Person() {
        likeIngredients = new HashSet<>();
        dislikeIngredients = new HashSet<>();
    }
}
