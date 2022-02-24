import java.util.*;

public class Contributor {
    String name;
    List<Skill> skills;

    public Contributor(String name) {
        this.name = name;
        skills = new ArrayList<>();
    }
}