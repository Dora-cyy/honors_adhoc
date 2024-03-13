import javafx.scene.control.RadioButton;
import javafx.scene.text.Text;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class User {
    private JFrame userFrame;
    private final ScenarioRecord SC;
    private Label scenarioTitle;
    private Font font;
    private int count;
    private static final int MAX = 5;
    private Allocation current;
    private Group ingroup;
    private Group outgroup;
    private Button next;
    private JTextArea t;

    private int userChoice;
    public User (ScenarioRecord sc) {
        this.SC = sc;
        count = 0;
        initialize();
        this.userFrame.setVisible(true);
    }

    private void initialize() {
        this.userFrame = new JFrame();
        this.userFrame.setTitle("Scenarios");
        this.userFrame.setBounds(100, 100, 500, 400);
        this.userFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[] {50, 100, 300, 100, 50 };
        gridBagLayout.rowHeights = new int[] { 20, 200, 100, 50};
        this.userFrame.getContentPane().setLayout(gridBagLayout);

        this.font = new Font("Times", Font.PLAIN, 25);
        scenarioTitle = new Label("Test");
        scenarioTitle.setFont(font);
        scenarioTitle.setMinimumSize(new Dimension(100, 30));
        GridBagConstraints title = new GridBagConstraints();
        title.gridx = 0;
        title.gridy = 0;
        title.gridwidth = 4;
        title.insets = new Insets(10,10,10,10);
        this.userFrame.getContentPane().add(scenarioTitle, title);

        t = new JTextArea("");
        t.setEditable(false);
        t.setBorder(null);
        t.setLineWrap(true);
        t.setWrapStyleWord(true);
        t.setBackground(new Color(255,255,255,0));
        font = new Font("Calibri", Font.PLAIN, 12);
        t.setFont(font);

        GridBagConstraints text = new GridBagConstraints();
        text.gridx = 1;
        text.gridy = 1;
        text.weightx = 1.0;
        text.weighty = 1.0;
        text.gridwidth = 3;
        text.gridheight = 2;
        text.insets = new Insets(5,5,5,5);
        text.gridwidth = GridBagConstraints.REMAINDER;
        text.fill = GridBagConstraints.BOTH;

        userFrame.getContentPane().add(t, text);

        next = new Button("next");
        GridBagConstraints nextButton = new GridBagConstraints();
        nextButton.gridx = 3;
        nextButton.gridy = 3;
        next.setEnabled(false);
        this.userFrame.getContentPane().add(next, nextButton);

        generateScenarios();
         next.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (userChoice == 1) {
                    current.allocateScores(ingroup, outgroup, 3);
                    current.allocateScores(ingroup, ingroup, 8);
                }
                if (userChoice == 2) {
                    current.allocateScores(ingroup, outgroup, 12);
                    current.allocateScores(ingroup, ingroup, 11);
                }
                else {
                    current.allocateScores(ingroup, outgroup, 21);
                    current.allocateScores(ingroup, ingroup, 17);
                }
                SC.addRecord(current);
                generateScenarios();
            }

        });
    }

    private void generateScenarios() {
        if (SC.history.isEmpty()) {
            scenarioTitle.setText("No Scenarios Yet.");
        }
        if (count >= SC.history.size()) {
            userFrame.getContentPane().removeAll();
            scenarioTitle.setText("No more scenarios, thank you!");
            userFrame.getContentPane().add(scenarioTitle);
            userFrame.revalidate();
        }
        current = SC.chooseScenario();
        scenarioTitle.setText("Scenario " + (count + 1));
        loadScenarios();
    }

    private void loadScenarios() {
        count ++;
        int population = current.populationSize;
        String intro = "There are " + population + " people living in the same community, ";
        intro += "dividing into " + current.groups.size() + " groups: ";
        for(Group g : current.groups) {
            intro += g.name + ", ";
        }
        intro = intro.substring(0, intro.length() - 2) + ".\n";
        for (Map.Entry<Group, Double> c : current.demography.entrySet()) {
            intro += "Group " + c.getKey().name + " makes up " + (int)(c.getValue() * 100) + "% of population.\n";
        }
        t.setText(intro);
        font = new Font("Calibri", Font.PLAIN, 12);
        t.setFont(font);
        t.append("On some days, 2 members in each group went to a local island.");
        this.ingroup = SC.chooseGroup();
        this.outgroup = ingroup;
        while (outgroup.name.equals(ingroup.name)) {
            this.outgroup = SC.chooseGroup();
        }
        t.append("You are one of the " + ingroup.name + " member.");
        t.append("There are plenty of resources available on the island, and YOU are in charge " +
                        "of allocating resources to the groups and reporting it to " +
                        current.observer + ", by allocating points to the people.");
        userFrame.getContentPane().revalidate();

        JPanel optionPane = new JPanel(new GridLayout(1, 0));
        GridBagConstraints option = new GridBagConstraints();
        option.gridx = 2;
        option.gridy = 3;
        option.fill = GridBagConstraints.REMAINDER;
        userFrame.getContentPane().add(optionPane, option);
        ButtonGroup choices = new ButtonGroup();
        JRadioButton option1 = new JRadioButton(ingroup.name + ": 8, " + outgroup.name + ": 3", false);
        font = new Font ("calibri", Font.PLAIN, 10);
        option1.setFont(font);
        option1.setEnabled(true);
        option1.setSize(20,20);
        choices.add(option1);
        optionPane.add(option1);

        JRadioButton option2 = new JRadioButton(ingroup.name + ": 12, " + outgroup.name + ": 11", false);
        option2.setFont(font);
        option2.setEnabled(true);
        GridBagConstraints middle = new GridBagConstraints();
        option2.setSize(20,20);
        choices.add(option2);
        optionPane.add(option2);

        JRadioButton option3 = new JRadioButton(ingroup.name + ": 17, " + outgroup.name + ": 21", false);
        option3.setFont(font);
        option3.setEnabled(true);
        option3.setSize(20,20);
        choices.add(option3);
        optionPane.add(option3);

        option1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                userChoice = 1;
                next.setEnabled(true);
            }
        });

        option2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                userChoice = 2;
                next.setEnabled(true);
            }
        });

        option3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                userChoice = 3;
                next.setEnabled(true);
            }
        });
    }
}
