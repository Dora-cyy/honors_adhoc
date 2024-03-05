import java.util.*;
public class Allocation {
    // title and count: how many times the situation is chosen
    //private final String
    public final String title;
    public final String observer;
    public final Map<String, Group> groups; //vertices
    public int populationSize; //total number of people
    public final Map<Group, Double> demography; //the distribution of population in proportion to total size
    public int resourceAmount;
    public final Map<Group, Double> resource; //the distribution of resource in proportion to total amount

    public Allocation (String observer, String title) {
        this.title = title;
        this.groups = new HashMap<>();
        this.populationSize = 0;
        this.resourceAmount = 0;
        this.demography = new HashMap<>();
        this.resource = new HashMap<>();
        this.observer = observer;
    }

    //precondition: groups does not contain g and size of groups is less than 4
    public void addGroup (Group g) {
        if (this.groups.size() >= 4) {
            throw new IllegalStateException("the max size for groups is 4");
        }
        if (!this.groups.containsKey(g.name)) {
            this.groups.put(g.name, g);
        }
    }

    public Collection<Group> getGroups() {
        return this.groups.values();
    }

    public Group find (String name) {
        return this.groups.get(name);
    }
    // precondition:
    // groups must contain g
    // size must be at least 1
    public void distributePopulation (Group g, int size) {
        if (!this.groups.containsKey(g.name)) {
            throw new IllegalStateException("input group does not exist");
        }
        if (size < 1) {
            throw new IllegalStateException("population must be at least 1");
        }
        if (this.demography.containsKey(g)) {
            int originalSize = (int) (this.demography.get(g) * populationSize);
            populationSize += (size - originalSize);
        }
        else {
            populationSize += size;
        }
        this.demography.put(g, (double)size / this.populationSize);
    }

    public void distributeResource (Group g, int amount) {
        if (!this.groups.containsKey(g.name)) {
            throw new IllegalStateException("input group does not exist");
        }
        if (amount < 0) {
            throw new IllegalStateException("amount of resource cannot be negative");
        }
        if (this.resource.containsKey(g)) {
            int originalSize = (int) (this.resource.get(g) * resourceAmount);
            resourceAmount += (amount - originalSize);
        }
        else {
            resourceAmount += amount;
        }
        this.demography.put(g, (double)amount / this.resourceAmount);
    }


    public void allocateScores(Group a, Group b, int score) {
        groups.get(a.name).setAllocation(b, score);
    }


}
