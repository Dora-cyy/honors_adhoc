import javafx.embed.swing.JFXPanel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class Researcher {
    private final ScenarioRecord SC;
    public Researcher(ScenarioRecord sc) {
        this.SC = sc;
        initialize();
        this.researcherFrame.setVisible(true);
    }
    private JFrame researcherFrame;
    private Component leftStrut;

    private Component topStrut;

    private Font font;

    private JLabel title;

    private Button save;

    private GridBagLayout gridBagLayout;

    private String scTitle;
    private String groupList;
    private String populationList;
    private String resourceList;
    private String observerName;

    private boolean hasTitle = false;
    private boolean hasGroup = false;
    private boolean hasPopulation = false;
    private boolean hasResource = false;
    private boolean hasObserver = false;

    private void initialize() {
        this.researcherFrame = new JFrame();
        this.researcherFrame.setTitle("Set up Scenarios");
        this.researcherFrame.setBounds(100, 100, 700, 500);
        this.researcherFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[] {150, 250, 50, 50};
        gridBagLayout.rowHeights = new int[] {50, 50, 50, 50};

        this.researcherFrame.getContentPane().setLayout(gridBagLayout);
        researcherFrame.getContentPane().setMaximumSize(new Dimension(1000, 1000));

        this.font = new Font("Times", Font.PLAIN, 25);
        title = new JLabel("Set up your scenarios: ");
        title.setFont(font);
        title.setMinimumSize(new Dimension(600, 50));
        GridBagConstraints titleLayout = new GridBagConstraints();
        titleLayout.gridx = 0;
        titleLayout.gridy = 0;
        titleLayout.ipadx = 30;
        titleLayout.ipady = 20;
        titleLayout.weightx = 1.0;
        titleLayout.gridwidth = GridBagConstraints.REMAINDER;
        titleLayout.insets = new Insets(5,5,5,5);
        titleLayout.anchor = GridBagConstraints.NORTHWEST;

        this.researcherFrame.getContentPane().add(this.title, titleLayout);

        JPanel labelPane = new JPanel(new GridLayout(0, 1));
        JPanel fieldPane = new JPanel (new GridLayout(0, 1));
        GridBagConstraints labelLayout = new GridBagConstraints();
        labelLayout.gridx = 0;
        labelLayout.gridy = 1;
        labelLayout.ipadx = 10;
        labelLayout.ipady = 5;
        labelLayout.insets = new Insets(5, 30, 5, 5);
        labelLayout.weightx = 0.25;
        labelLayout.anchor = GridBagConstraints.LINE_START;
        GridBagConstraints fieldLayout = new GridBagConstraints();
        fieldLayout.gridx = 1;
        fieldLayout.gridy = 1;
        fieldLayout.ipadx = 5;
        fieldLayout.ipady = 5;
        fieldLayout.insets = new Insets(5, 20, 5, 5);
        fieldLayout.weightx = 0.75;
        fieldLayout.gridwidth = 2;
        fieldLayout.fill = GridBagConstraints.HORIZONTAL;
        researcherFrame.getContentPane().add(labelPane, labelLayout);
        researcherFrame.getContentPane().add(fieldPane, fieldLayout);

        JLabel titleLabel = new JLabel("Title: ");
        titleLabel.setFont(new Font("Calibri", Font.PLAIN, 15));
        JTextField scenarioTitle = new JTextField();
        scenarioTitle.setEditable(true);
        scenarioTitle.setColumns(10);
        titleLabel.setLabelFor(scenarioTitle);
        labelPane.add(titleLabel);
        fieldPane.add(scenarioTitle);
        font = new Font("Calibri", Font.PLAIN, 10);
        scenarioTitle.setFont(font);

        scenarioTitle.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                scenarioTitle.setForeground(Color.BLACK);
                hasTitle = true;
                updateSave();
            }

            @Override
            public void focusLost(FocusEvent e) {
                scTitle = scenarioTitle.getText();
            }
        });

        JLabel groupLabel = new JLabel("Groups:");
        groupLabel.setFont(new Font("Calibri", Font.PLAIN, 15));
        groupLabel.setMinimumSize(new Dimension(20, 20));
        groupLabel.setMaximumSize(new Dimension(100, 100));
        JTextField groups = new JTextField("maximum 4, specify in comma-separated list");
        groups.setForeground(Color.GRAY);
        groups.setEditable(true);
        groups.setColumns(10);
        groupLabel.setLabelFor(groups);
        labelPane.add(groupLabel);
        fieldPane.add(groups);
        groups.setFont(font);

        groups.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent e) {
                if (groups.getText().equals("maximum 4, specify in comma-separated list")) {
                    groups.setText("");
                }
                groups.setForeground(Color.BLACK);
                hasGroup = true;
                updateSave();
            }

            public void focusLost(FocusEvent e) {
                groupList = groups.getText();
                if (groups.getText().isEmpty()) {
                    groups.setText("maximum 4, specify in comma-separated list");
                    groups.setForeground(Color.GRAY);
                }
            }
        });

        JLabel populationLabel = new JLabel("Population distribution:");
        populationLabel.setFont(new Font("Calibri", Font.PLAIN, 15));
        JTextField population = new JTextField("specify in comma-separated list with same order as the groups");
        population.setForeground(Color.GRAY);
        population.setEditable(true);
        population.setColumns(10);
        populationLabel.setLabelFor(population);
        labelPane.add(populationLabel);
        fieldPane.add(population);
        population.setFont(font);
        population.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent e) {
                if (population.getText().equals("specify in comma-separated list with same order as the groups")) {
                    population.setText("");
                }
                population.setForeground(Color.BLACK);
                hasPopulation = true;
                updateSave();
            }

            public void focusLost(FocusEvent e) {
                populationList = population.getText();
                if (population.getText().isEmpty()) {
                    population.setText("specify in comma-separated list with same order as the groups");
                    population.setForeground(Color.GRAY);
                }
            }
        });

        JLabel resourceLabel = new JLabel("Resource distribution:");
        resourceLabel.setFont(new Font("Calibri", Font.PLAIN, 15));
        JTextField resource = new JTextField("specify in comma-separated list with same order as the groups");
        resource.setForeground(Color.GRAY);
        resource.setEditable(true);
        resource.setColumns(10);
        resource.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent e) {
                if (resource.getText().equals("specify in comma-separated list with same order as the groups")) {
                    resource.setText("");
                }
                resource.setForeground(Color.BLACK);
                hasResource = true;
                updateSave();
            }

            public void focusLost(FocusEvent e) {
                resourceList = resource.getText();
                if (resource.getText().isEmpty()) {
                    resource.setText("specify in comma-separated list with same order as the groups");
                    resource.setForeground(Color.GRAY);
                }
            }
        });
        resourceLabel.setLabelFor(resource);
        labelPane.add(resourceLabel);
        fieldPane.add(resource);
        resource.setFont(font);

        JLabel observerLabel = new JLabel("Observer:");
        observerLabel.setFont(new Font("Calibri", Font.PLAIN, 15));
        JTextField observer = new JTextField("Enter in-group/out-group/researcher/no observer");
        observer.setForeground(Color.GRAY);
        observer.setEditable(true);
        observer.setColumns(10);

        observer.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent e) {
                if (observer.getText().equals("Enter in-group/out-group/researcher/no observer")) {
                    observer.setText("");
                }
                observer.setForeground(Color.BLACK);
                hasObserver = true;
                updateSave();
            }

            public void focusLost(FocusEvent e) {
                observerName = observer.getText();
                if (observer.getText().isEmpty()) {
                    observer.setText("Enter in-group/out-group/researcher/no observer");
                    observer.setForeground(Color.GRAY);
                    observerName = "";
                }
            }
        });

        observerLabel.setLabelFor(observer);
        labelPane.add(observerLabel);
        fieldPane.add(observer);
        observer.setFont(font);

        save = new Button("save");
        save.setEnabled(false);
        save.setSize(20,20);
        GridBagConstraints saveLayout = new GridBagConstraints();
        saveLayout.gridx = 3;
        saveLayout.gridy = 2;
        saveLayout.anchor = GridBagConstraints.LAST_LINE_END;
        this.researcherFrame.getContentPane().add(save, saveLayout);

        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String row = checkEmpty();
                String invalid = checkValid();
                if (!row.isEmpty()) {
                    JOptionPane.showMessageDialog(researcherFrame, row + " cannot be empty!");
                }
                else if (!invalid.isEmpty()) {
                    JOptionPane.showMessageDialog(researcherFrame, invalid);
                }
                else {
                    save();
                }
            }
        });


    }

    private void updateSave() {
        if (hasGroup && hasPopulation && hasResource && hasTitle && hasObserver) {
            save.setEnabled(true);
        }
    }

    private String checkEmpty () {
        if (groupList.isEmpty()) {
            return "Group";
        }
        if (scTitle.isEmpty()) {
            return "Title";
        }
        if (resourceList.isEmpty()) {
            return "Resource distribution";
        }
        if (populationList.isEmpty()) {
            return "Population distribution";
        }
        if (observerName.isEmpty()) {
            return "Observer";
        }
        return "";
    }

    private String checkValid () {
        String[] g = groupList.toLowerCase().split(",");
        if (g.length > 4) {
            return "Cannot have more than 4 groups!";
        }
        for (int x = 0; x < g.length; x++) {
            for (int y = x + 1; y < g.length; y++) {
                if (g[x].trim().equals(g[y].trim())) {
                    return "Cannot have duplicate groups!";
                }
            }
            if (g[x].isEmpty()) {
                return "Cannot have empty group name!";
            }
        }
        String[] p = populationList.split(",");
        for (int x = 0; x < p.length; x++) {
            try {
                int d = Integer.parseInt(p[x].trim());
            } catch (NumberFormatException nfe) {
                return "Population must be an integer list!";
            }
        }
        if (g.length != p.length) {
            return "Population distribution must match the groups!";
        }
        String[] r = resourceList.split(",");
        for (int x = 0; x < r.length; x++) {
            try {
                int d = Integer.parseInt(r[x].trim());
            } catch (NumberFormatException nfe) {
                return "Resource must be an integer list!";
            }
        }
        if (r.length != p.length) {
            return "Resource distribution must match the groups!";
        }
        if (!(observerName.equalsIgnoreCase("in-group") || observerName.equalsIgnoreCase("out-group")
        || observerName.equalsIgnoreCase("researcher") || observerName.equalsIgnoreCase("no observer"))) {
            return "Observer must be one of the four choices!";
        }
        return "";
    }

    private void save() {
        Allocation new_al = new Allocation(observerName, scTitle);
        String[] g = groupList.toLowerCase().split(",");
        String[] p = populationList.split(",");
        String [] r = resourceList.split(",");
        int [] pNum = new int [p.length];
        int [] rNum = new int [r.length];
        int populationSize = 0;
        int resourceAmt = 0;
        for (int i = 0; i < p.length; i ++) {
            pNum[i] = Integer.parseInt(p[i].trim());
            rNum[i] = Integer.parseInt(r[i].trim());
            populationSize += pNum[i];
            resourceAmt += rNum[i];
        }
        new_al.populationSize = populationSize;
        new_al.resourceAmount = resourceAmt;
        for (int i = 0; i < g.length; i ++) {
            Group gp = new Group(g[i]);
            new_al.groups.add(gp);
            new_al.distributeResource(gp, rNum[i]);
            new_al.distributePopulation(gp, pNum[i]);
        }
        SC.addRecord(new_al);
        update();
    }

    private void update() {
        researcherFrame.getContentPane().removeAll();
        researcherFrame.getContentPane().setLayout(new BorderLayout());
        title.setText("You have successfully created your scenario!");
        researcherFrame.getContentPane().add(title, BorderLayout.CENTER);
        researcherFrame.revalidate();
    }
}
