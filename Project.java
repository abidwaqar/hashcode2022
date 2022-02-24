import java.util.ArrayList;
import java.util.List;

public class Project {
    String name;
    int days;
    int score;
    int bestBeforeDay;
    List<Role> roles;

    public Project(String name, int days, int score, int bestBeforeDay) {
        this.name = name;
        this.days = days;
        this.score = score;
        this.bestBeforeDay = bestBeforeDay;
        this.roles = new ArrayList<>();
    }
}