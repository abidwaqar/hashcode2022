import java.util.HashSet;

public class Ingredient {
    String name;
    boolean processed;
    HashSet<Person> likeByPeople;
    HashSet<Person> dislikeByPeople;

    public Ingredient(String name) {
        this.name = name;
        processed = false;
        likeByPeople = new HashSet<>();
        dislikeByPeople = new HashSet<>();
    }

    public void processed(boolean added) {
        processed = true;

        if (added) {
            // opinion of people who dislike this ingredient dosen't count now
            for (Person person : dislikeByPeople) {
                for (Ingredient ingredient : person.likeIngredients) {
                    ingredient.likeByPeople.remove(person);
                }

                for (Ingredient ingredient : person.dislikeIngredients) {
                    if (ingredient != this) {
                        ingredient.dislikeByPeople.remove(person);
                    }
                }
            }

            this.dislikeByPeople.clear();
        } else {
            // opinion of people who like this ingredient dosen't count now
            for (Person person : likeByPeople) {
                for (Ingredient ingredient : person.likeIngredients) {
                    if (ingredient != this) {
                        ingredient.likeByPeople.remove(person);
                    }
                }

                for (Ingredient ingredient : person.dislikeIngredients) {
                    ingredient.dislikeByPeople.remove(person);
                }
            }

            this.likeByPeople.clear();
        }
    }
}
