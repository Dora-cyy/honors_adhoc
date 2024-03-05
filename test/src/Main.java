import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        ScenarioRecord r = new ScenarioRecord();
        Allocation x = originalStudy1();
        r.addRecord(x);
        Allocation y = originalStudy2();
        r.addRecord(y);
    }



    //Come up with something for the researchers to create new scenarios
    // instead of hard coding the allocation, make this to be written into a file, and
    //write a function in ScenarioRecord.
    public static Allocation originalStudy1() {
        Group dusek = new Group("dusek");
        Group tausig = new Group("tausig");

        Allocation scenario1 = new Allocation("in-group member", "A1");
        //observer present
        scenario1.addGroup(dusek);
        scenario1.addGroup(tausig);
        //no demographic modification
        scenario1.distributePopulation(dusek, 10);
        scenario1.distributePopulation(tausig, 10);
        scenario1.distributeResource(dusek, 10);
        scenario1.distributeResource(tausig,10);

        System.out.println("There are 50 people living in the same community, " +
                "divided into 2 groups: Dusek and Tausig.");
        System.out.println("Both groups have roughly the same number of " +
                "members.");
        System.out.println("On some days, 2 members in each group went to a local island.");
        System.out.println(" You are one of the “Dusek” member.");
        System.out.println("There are plenty of resources available on the " +
                "island, and YOU are in charge of allocating resources to the group with your group member, " +
                "by allocating points to the people.");
        int option = allocationMethod();
        scenario1.allocateScores(dusek, tausig, option);
        return scenario1;
    }

    public static Allocation originalStudy2() {
        Group dusek = new Group("dusek");
        Group tausig = new Group("tausig");

        Allocation scenario2 = new Allocation("researcher", "A2");
        //observer present
        scenario2.addGroup(dusek);
        scenario2.addGroup(tausig);
        //no demographic modification
        scenario2.distributePopulation(dusek, 10);
        scenario2.distributePopulation(tausig, 10);
        scenario2.distributeResource(dusek, 10);
        scenario2.distributeResource(tausig,10);

        System.out.println("There are 50 people living in the same community, " +
                "divided into 2 groups: Dusek and Tausig.");
        System.out.println("Both groups have roughly the same number of " +
                "members.");
        System.out.println("On some days, 2 members in each group went to a local island.");
        System.out.println(" You are one of the “Dusek” member.");
        System.out.println("There are plenty of resources available on the " +
                "island, and YOU are in charge of allocating resources to the group and reporting it to researcher, " +
                "by allocating points to the people.");
        int option = allocationMethod();
        scenario2.allocateScores(dusek, tausig, option);
        return scenario2;
    }
    public static int allocationMethod() {
        System.out.println("You have 2 options:");
        System.out.println("1. 3 days of food to Dusek, 1 day of food to Tausig");
        System.out.println("2. 1 day of food to Dusek, 1 day of food to Tausig");
        System.out.println("Type the option # you chose:");
        Scanner s = new Scanner(System.in);
        return s.nextInt();
    }
}