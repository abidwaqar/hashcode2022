import java.util.ArrayList;
import java.util.List;

enum ProjectStatus {
    TODO,
    DOING,
    DONE
}

public class Project {
    String name;
    int days;
    int score;
    int bestBeforeDay;
    List<Role> roles;

    ProjectStatus status;

    List<Contributor> contributors;

    public Project(String name, int days, int score, int bestBeforeDay) {
        this.name = name;
        this.days = days;
        this.score = score;
        this.bestBeforeDay = bestBeforeDay;
        this.roles = new ArrayList<>();
        this.status = ProjectStatus.TODO;
    }
}