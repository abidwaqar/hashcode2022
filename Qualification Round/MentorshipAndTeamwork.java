import java.io.File;
import java.io.FileWriter;
import java.util.*;

public class MentorshipAndTeamwork {
    public static void main(String[] args) {
        try {
            String[] filesNames = {
                    "a_an_example",
                    "b_better_start_small",
                    "c_collaboration",
                    "d_dense_schedule",
                    "e_exceptional_skills",
                    "f_find_great_mentors"
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
                HashMap<Integer, List<Project>> projectDueDates = new HashMap<>();

                Collections.sort(projects, (x, y) -> (y.score / y.days) - (x.score / x.days));

                int date = 0;
                boolean projectFound = true;
                while (projectFound || projectDueDates.size() != 0) {
                    projectFound = false;

                    if (projectDueDates.containsKey(date)) {
                        for (Project project : projectDueDates.get(date)) {
                            project.status = ProjectStatus.DONE;

                            for (Contributor contributor : project.contributors) {
                                contributor.status = ContributorStatus.AVAILABLE;
                            }

                            for (int i = 0; i < project.contributors.size(); ++i) {
                                Contributor contributor = project.contributors.get(i);
                                Role role = project.roles.get(i);
                                Skill skill = contributor.getSkill(role.skillName);

                                if (skill.level <= role.requiredLevel) {
                                    ++skill.level;
                                }
                            }
                        }

                        projectDueDates.remove(date);
                    }

                    for (Project project : projects) {

                        if (project.status != ProjectStatus.TODO) {
                            continue;
                        }

                        boolean resourcesFound = true;

                        if (project.score - (date + project.days - project.bestBeforeDay) > 0) {

                            List<Contributor> projectContributors = new ArrayList<>();

                            // TODO improve
                            for (Role role : project.roles) {
                                resourcesFound = false;
                                for (Contributor contributor : contributors) {
                                    if (contributor.status == ContributorStatus.AVAILABLE
                                            && contributorMatchesRole(contributor.skills, role)) {
                                        projectContributors.add(contributor);
                                        resourcesFound = true;
                                        contributor.status = ContributorStatus.WORKING;
                                        break;
                                    }
                                }

                                if (resourcesFound == false) {
                                    for (Contributor contributor : projectContributors) {
                                        contributor.status = ContributorStatus.AVAILABLE;
                                    }
                                    break;
                                }
                            }

                            if (resourcesFound == false) {
                                continue;
                            } else {
                                project.status = ProjectStatus.DOING;
                                project.contributors = projectContributors;
                                for (Contributor contributor : projectContributors) {
                                    contributor.status = ContributorStatus.WORKING;
                                }

                                int key = date + project.days;
                                if (projectDueDates.containsKey(key)) {
                                    projectDueDates.get(key).add(project);
                                } else {
                                    List<Project> tempList = new ArrayList<>();
                                    tempList.add(project);
                                    projectDueDates.put(key, tempList);
                                }

                                projectFound = true;

                                // Adding output
                                Output output = new Output(project.name);
                                for (Contributor contributor : projectContributors) {
                                    output.contributors.add(contributor.name);
                                }

                                outputs.add(output);
                            }
                        }
                    }

                    ++date;
                }

                // Saving output

                new File(pathToOutputFile).createNewFile();

                try (FileWriter myWriter = new FileWriter(pathToOutputFile)) {
                    myWriter.write("" + outputs.size() + "\n");
                    for (Output output : outputs) {
                        myWriter.write(output.projectName + "\n");
                        myWriter.write(String.join(" ", output.contributors) + "\n");
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static boolean contributorMatchesRole(List<Skill> skills, Role role) {
        for (Skill skill : skills) {
            if (skill.name.equals(role.skillName) && skill.level >= role.requiredLevel) {
                return true;
            }
        }

        return false;
    }
}