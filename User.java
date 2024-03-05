import javafx.scene.text.Text;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.util.ArrayList;
import java.util.Map;

public class User {
    private JFrame userFrame;
    private final ScenarioRecord SC;
    private Label scenarioTitle;
    private Font font;
    private int count;
    private static final int MAX = 5;
    private Component topStrut;
    private Component leftStrut;
    private Allocation current;
    public User (ScenarioRecord sc) {
        this.SC = sc;
        count = 0;
        initialize();
        this.userFrame.setVisible(true);
    }

    private void initialize() {
        this.userFrame = new JFrame();
        this.userFrame.setTitle("Scenarios");
        this.userFrame.setBounds(100, 100, 350, 400);
        this.userFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[] { 0, 90, 90, 90, 0, 0 };
        gridBagLayout.rowHeights = new int[] { 0, 9, 0, 0 };
        gridBagLayout.columnWeights = new double[] { 0.0, 0.0, 1.0, 0.0, 0.0,
                Double.MIN_VALUE };
        gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, Double.MIN_VALUE };

        this.userFrame.getContentPane().setLayout(gridBagLayout);
        leftStrut = Box.createHorizontalStrut(30);
        GridBagConstraints left = new GridBagConstraints();
        left.gridx = 0;
        left.gridy = 1;
        userFrame.getContentPane().add(leftStrut, left);

        topStrut = Box.createVerticalStrut(10);
        GridBagConstraints top = new GridBagConstraints();
        top.gridx = 1;
        top.gridy = 0;
        userFrame.getContentPane().add(topStrut, top);

        generateScenarios();
    }

    private void generateScenarios() {
        this.font = new Font("Times", Font.PLAIN, 25);
        scenarioTitle = new Label("Test");
        scenarioTitle.setFont(font);
        GridBagConstraints title = new GridBagConstraints();
        title.gridx = 1;
        title.gridy = 1;
        this.userFrame.getContentPane().add(scenarioTitle, title);
        if (SC.history.isEmpty()) {
            scenarioTitle.setText("No Scenarios Yet.");
        }
            current = SC.chooseScenario();
            scenarioTitle.setText("Scenario " + (count + 1));
            loadScenarios();
    }

    private void loadScenarios() {
        int population = current.populationSize;
        String intro = "There are " + population + " people living in the same community, ";
        intro += "dividing into " + current.groups.size() + " groups: ";
        for(String g : current.groups.keySet()) {
            intro += g + ", ";
        }
        intro = intro.substring(0, intro.length() - 2) + ".\n";
        for (Map.Entry<Group, Double> c : current.demography.entrySet()) {
            intro += "Group " + c.getKey().name + " makes up " + (int)(c.getValue() * 100) + "% of population.\n";
        }
        JTextArea t = new JTextArea(intro);
        t.setEditable(false);
        t.setBorder(null);
        t.setLineWrap(true);
        t.setWrapStyleWord(true);
        t.setBounds(10,10,300,50);
        t.setBackground(new Color(255,255,255,0));
        font = new Font("Calibri", Font.PLAIN, 12);
        t.setFont(font);
        GridBagConstraints text = new GridBagConstraints();
        text.gridx = 1;
        text.gridy = 2;
        text.insets = new Insets(5,5,5,5);
        t.append("On some days, 2 members in each group went to a local island.\n");
        t.append("You are one of the" + "member.");
        userFrame.getContentPane().add(t, text);
    }
}
