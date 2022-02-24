import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class MentorshipAndTeamwork {
    public static void main(String[] args) {
        try {
            String[] filesNames = {
                    "a_an_example",
                    // "b_better_start_small",
                    // "c_collaboration",
                    // "d_dense_schedule",
                    // "e_exceptional_skills",
                    // "f_find_great_mentors"
            };

            for (String fileName : filesNames) {

                // Reading input

                String pathToInputFile = "inputs/" + fileName + ".in.txt";
                String pathToOutputFile = "outputs/" + fileName + ".out.txt";

                Scanner myReader = new Scanner(new File(pathToInputFile));

                String[] rawText = myReader.nextLine().split(" ");

                int numberOfContributors = Integer.parseInt(rawText[0]);
                int numberOfProjects = Integer.parseInt(rawText[1]);

                List<Contributor> contributors = new ArrayList<>();
                for (int i = 0; i < numberOfContributors; i++) {
                    rawText = myReader.nextLine().split(" ");

                    String contributorName = rawText[0];
                    int contributorNumberOfSkills = Integer.parseInt(rawText[1]);

                    Contributor currentContributor = new Contributor(contributorName);

                    for (int j = 0; j < contributorNumberOfSkills; j++) {
                        rawText = myReader.nextLine().split(" ");

                        String skillName = rawText[0];
                        int skillLevel = Integer.parseInt(rawText[1]);

                        Skill currentSkill = new Skill(skillName, skillLevel);

                        currentContributor.skills.add(currentSkill);
                    }

                    contributors.add(currentContributor);
                }

                List<Project> projects = new ArrayList<>();
                for (int i = 0; i < numberOfProjects; i++) {
                    rawText = myReader.nextLine().split(" ");

                    String projectName = rawText[0];
                    int projectDays = Integer.parseInt(rawText[1]);
                    int projectScore = Integer.parseInt(rawText[2]);
                    int projectBestBeforeDay = Integer.parseInt(rawText[3]);
                    int projectNumberOfRoles = Integer.parseInt(rawText[4]);

                    Project currentProject = new Project(projectName, projectDays, projectScore, projectBestBeforeDay);

                    for (int j = 0; j < projectNumberOfRoles; j++) {
                        rawText = myReader.nextLine().split(" ");

                        String roleSkillName = rawText[0];
                        int roleRequiredLevel = Integer.parseInt(rawText[1]);

                        Role currentRole = new Role(roleSkillName, roleRequiredLevel);

                        currentProject.roles.add(currentRole);
                    }

                    projects.add(currentProject);
                }

                // Logic

                List<Output> outputs = new ArrayList<>();

                // TODO implement logic

                // Saving output

                new File(pathToOutputFile).createNewFile();

                try (FileWriter myWriter = new FileWriter(pathToOutputFile)) {
                    myWriter.write(outputs.size());
                    for (Output output : outputs) {
                        myWriter.write(output.projectName);
                        myWriter.write(String.join(" ", output.contributors));
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void saveResult(String result, String outputFileNameWithPath) throws IOException {
        new File(outputFileNameWithPath).createNewFile();

        try (FileWriter myWriter = new FileWriter(outputFileNameWithPath)) {
            myWriter.write(result);
        }
    }
}