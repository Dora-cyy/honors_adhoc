
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
    public Set<Allocation> history;

    public Allocation current;

    public ScenarioRecord() {
        this.history = new HashSet<>();
        loadHistory();
    }

    public void addRecord(Allocation a){
        this.history.add(a);
        saveHistory();
    }


    public Allocation chooseScenario() {
        Random rand = new Random();
        int scenarioNum = rand.nextInt(history.size());
        for (int x = 0; x < scenarioNum - 1; x ++) {
            history.iterator().next();
        }
        Allocation currentScenario = history.iterator().next();
        current = currentScenario;
        current.chosen ++;
        return currentScenario;
    }

    public Group chooseGroup() {
        Random rand = new Random();

        int groupNum = rand.nextInt(current.groups.size());
        return current.groups.get(groupNum);
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



    private void loadHistory(){
        try {
            this.previousFile = new FileReader("out/production/honors_adhoc/record.yaml");
            BufferedReader bufferedReader = new BufferedReader(previousFile);
            String line = bufferedReader.readLine();
            line = bufferedReader.readLine();
            while (line != null) {
                if (line.split(":")[0].contains("title")) {
                    String title = line.split(":")[1].trim();
                    line = bufferedReader.readLine();
                    String observer = line.split(":")[1].trim();
                    line = bufferedReader.readLine();
                    Allocation a = new Allocation(observer, title);
                    a.chosen = Integer.parseInt(line.split(":")[1].trim());
                    line = bufferedReader.readLine();
                    if (line.contains("groups")) {
                        line = bufferedReader.readLine();
                        while (line.split(":")[0].contains("name")) {
                            Group g = new Group(line.split(":")[1].trim());
                            a.addGroup(g);
                            line = bufferedReader.readLine();
                        }
                    }
                    if (line.contains("populationSize")) {
                        a.populationSize = Integer.parseInt(line.split(":")[1].trim());
                        line = bufferedReader.readLine();
                    }
                    if (line.contains("demography")) {
                        line = bufferedReader.readLine();
                        while (line.contains("group")) {
                            Group g = a.find(line.split(":")[1].trim());
                            line = bufferedReader.readLine();
                            int proportion = (int) Math.round((Double.parseDouble(line.split(":")[1]) * a.populationSize));
                            a.distributePopulation(g, proportion);
                            line = bufferedReader.readLine();
                        }
                    }
                    if (line.contains("resourceAmount")) {
                        a.resourceAmount = Integer.parseInt(line.split(":")[1].trim());
                        line = bufferedReader.readLine();
                    }
                    if (line.contains("resource")) {
                        line = bufferedReader.readLine();
                        while (line != null && line.contains("group")) {
                            Group g = a.find(line.split(":")[1].trim());
                            line = bufferedReader.readLine();
                            int proportion = (int) Math.round((Double.parseDouble(line.split(":")[1].trim()) * a.resourceAmount));
                            a.distributeResource(g, proportion);
                            line = bufferedReader.readLine();
                        }
                    }
                    if (line != null && line.contains("allocations")) {
                        line = bufferedReader.readLine();
                        while (line != null && line.contains("in-group")) {
                            Group g = a.find(line.split(":")[1].trim());
                            line = bufferedReader.readLine();
                            line = bufferedReader.readLine();
                            while (line != null && line.contains("group") && ! line.contains("in-group")) {
                                Group out = a.find(line.split(":")[1].trim());
                                line = bufferedReader.readLine();
                                int score = Integer.parseInt(line.split(":")[1].trim());
                                g.setAllocation(out, score);
                                line = bufferedReader.readLine();
                            }
                        }
                    }
                    history.add(a);
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    // * how to save the scores that was allocated
    public void saveHistory() {
        try {
            this.file = new FileWriter("out/production/honors_adhoc/record.yaml");
            file.write("Allocation: \n");
            for (Allocation a : history) {
                file.write("  - title: " + a.title + "\n");
                file.write("    observer: " + a.observer + "\n");
                file.write("    chosen: " + a.chosen + "\n");
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
                file.write("    allocations: \n");
                for (Group g : a.groups) {
                    file.write("      - in-group: " + g.name + "\n");
                    file.write("        out-groups: " + "\n");
                    for (Map.Entry<Group, Integer> o : g.allocation.entrySet()) {
                        file.write("          - group: " + o.getKey().name + "\n");
                        file.write("            score: " + o.getValue() + "\n");
                    }
                }
            }
            file.close();
        }
        catch (IOException e) {
            System.out.println(e.toString());
        }

    }

    public void clearHistory () throws IOException {
        this.file = new FileWriter("out/production/honors_adhoc/record.yaml");
        file.write("");
    }
}