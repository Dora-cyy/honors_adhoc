import java.util.*;
public class Allocation {
    // title and count: how many times the situation is chosen
    //private final String
    public final String title;
    public final String observer;
    public final ArrayList<Group> groups; //vertices
    public int populationSize; //total number of people
    public final Map<Group, Double> demography; //the distribution of population in proportion to total size
    public int resourceAmount;
    public final Map<Group, Double> resource; //the distribution of resource in proportion to total amount

    public Allocation (String observer, String title) {
        this.title = title;
        this.groups = new ArrayList<>();
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
        if (!this.groups.contains(g)) {
            this.groups.add(g);
        }
        for (Group i : groups) {
            i.setAllocation(g, 0);
        }
    }

    public Collection<Group> getGroups() {
        return this.groups;
    }

    public Group find (String name) {
        for (Group i : groups) {
            if (i.name.equals(name)) {
                return i;
            }
        }
        return null;
    }
    // precondition:
    // groups must contain g
    // size must be at least 1
    public void distributePopulation (Group g, int size) {
        int originalPopulation = populationSize;
        if (!this.groups.contains(g)) {
            throw new IllegalStateException("input group does not exist");
        }
        if (size < 1) {
            throw new IllegalStateException("population must be at least 1");
        }
        this.demography.put(g, (double)size / this.populationSize);
        /* Set<Map.Entry<Group,Double>> list = this.demography.entrySet();
        for (Map.Entry<Group, Double> i: list) {
            Double previous = i.getValue() * originalPopulation;
            demography.put(i.getKey(), previous / populationSize);
        } */
    }

    public void distributeResource (Group g, int amount) {
        if (!this.groups.contains(g)) {
            throw new IllegalStateException("input group does not exist");
        }
        if (amount < 0) {
            throw new IllegalStateException("amount of resource cannot be negative");
        }
        this.resource.put(g, (double)amount / this.resourceAmount);
    }


    public void allocateScores(Group a, Group b, int score) {
        find(a.name).setAllocation(b, score);
    }


}
