import java.util.*;

public class Group {
    /* the name of current Group */
    public String name;
    /* a map that represents the current relationships the group has with another group in the
     form of scores allocated. In the map, the key should be an out-group, and the value should
     be the scores that current in-group allocate toward the out-group
     */
    private Map<Group, Integer> allocation;
    /* a map that represents the implicit relationship, or relevance current in-group
    has with each out-group*/
    private Map<Group, Integer> relevance;
    /* a map that represents the group's attitude toward the other groups */
    private Map<Group, Integer> outwardAttitude;

    /* a map that represents the other groups' attitude toward the current group */
    private Map<Group, Integer> inwardAttitude;

    public Group (String name) {
        this.name = name;
        this.allocation = new HashMap<>();
        this.outwardAttitude = new HashMap<>();
        this.inwardAttitude = new HashMap<>();
    }

    public void setOutwardAttitude (Group g, Integer attitude) {

    }

    public void setAllocation(Group g, int score) {
        if (allocation.containsKey(g)) {
            score += allocation.get(g);
        }
        allocation.put(g,score);
    }

}
