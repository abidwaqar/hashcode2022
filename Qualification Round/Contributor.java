import java.util.*;

enum ContributorStatus {
    WORKING,
    AVAILABLE
}

public class Contributor {
    String name;
    List<Skill> skills;

    ContributorStatus status;

    public Contributor(String name) {
        this.name = name;
        skills = new ArrayList<>();

        status = ContributorStatus.AVAILABLE;
    }

    public Skill getSkill(String skillName) {
        for (Skill skill : skills) {
            if (skill.name.equals(skillName)) {
                return skill;
            }
        }

        return null;
    }
}