
import java.io.*;
import java.util.*;
/*
* in this class, create a method that reads everything from yaml file and populate the history.
* from the scratch, recreate the objects stored every time the object is created. (perhaps in my constructor)
*
* */
public class ScenarioRecord {
    private FileWriter file;
    private FileReader previousFile;
    private Scanner input;
    private Set<Allocation> history;

    public ScenarioRecord() {
        this.history = new HashSet<>();
        this.input = new Scanner(System.in);
    }

    public void addRecord(Allocation a) {
        this.history.add(a);
    }

    public void displayResult() {
        for (Allocation a : history) {
            System.out.println("Groups: ");
            for (Group g : a.getGroups()) {
                System.out.print(g + ": ");
                Map<Group, Integer> outGroups = a.find(g.name).getAllocation();
                for (Group outGroup : outGroups.keySet()) {
                    if (!outGroup.name.equals(g.name)) {
                        System.out.print(outGroup.name + "=");
                        if (outGroups.get(outGroup) == null) {
                            System.out.print(0 + " ");
                        } else {
                            System.out.print(outGroups.get(outGroup) + " ");
                        }
                    }
                }
                System.out.println();
            }
            System.out.println();
        }
    }

    public void setScenario() {
        //choose the group names
        System.out.println("Input your group names (separated by spaces). Maximum 4 groups.");
        String[] groupList = input.next().split(" ");
        while (groupList.length > 4) {
            System.out.println("The maximum is 4 groups. Please re-enter the group names.");
            groupList = input.next().split(" ");
        }
        System.out.println("Should the external observer be: 1. in-group member. 2. Researcher. 3. No observer");
        int observerChoice = input.nextInt();
        Allocation setting;
        // set the observer condition
        if (observerChoice == 1) {
            setting = new Allocation("in-group member", "A1");
        } else if (observerChoice == 2) {
            setting = new Allocation("Researcher", "A1");
        } else {
            setting = new Allocation("No observer", "A1");
        }
        // set other conditions for each group
        for (String g : groupList) {
            Group newGroup = new Group(g);
            setting.addGroup(newGroup);
            setGroupCondition(newGroup, setting);
        }
    }

    private void setGroupCondition(Group g, Allocation current) {
        // set population
        System.out.println("Population for " + g.name + ": ");
        current.distributePopulation(g, input.nextInt());
        // set resource
        System.out.println("Amount of resource for " + g.name + ": ");
    }

    private void loadHistory() throws IOException {
        this.previousFile = new FileReader("out/production/honors_adhoc/record.yaml");
        BufferedReader bufferedReader = new BufferedReader(previousFile);
        String line = bufferedReader.readLine();
        while (line != null) {


        }
    }

    public void saveHistory() throws IOException {
        this.file = new FileWriter("out/production/honors_adhoc/record.yaml");
        file.write("Allocation: \n");
        for (Allocation a : history) {
            file.write("  - title: " + a.title);
            file.write("    observer: " + a.observer + "\n");
            file.write("    groups: " + "\n");
            for (Group g : a.getGroups()) {
                file.write("      - name: " + g.name + "\n");
            }
            file.write("    populationSize: " + a.populationSize + "\n");
            file.write("    demography: " + "\n");
            for (Map.Entry<Group, Double> x : a.demography.entrySet()) {
                file.write("      - group: " + x.getKey().name + "\n");
                file.write("        proportion: " + x.getValue() + "\n");
            }
            file.write("    resourceAmount: " + a.resourceAmount + "\n");
            file.write("    resource: " + "\n");
            for (Map.Entry<Group, Double> x : a.resource.entrySet()) {
                file.write("      - group: " + x.getKey().name + "\n");
                file.write("        proportion: " + x.getValue() + "\n");
            }
        }
        file.close();
    }
}