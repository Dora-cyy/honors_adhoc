import javafx.embed.swing.JFXPanel;
import javafx.scene.text.Text;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class OpenPage {
    private JFrame frame;
    private Button researcherMode;
    private Button userMode;

    private Label welcomeMessage;
    private Component horizontal;
    private Component vertical;

    public static final ScenarioRecord SC = new ScenarioRecord();


    public OpenPage () {
        //this.sc = new ScenarioRecord();
        initialize();
        this.frame.setVisible(true);
    }

    private void initialize() {
        this.frame = new JFrame();
        this.frame.setTitle("Opening Page");
        this.frame.setBounds(200, 200, 400, 170);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[] { 0, 90, 90, 90, 0, 0 };
        gridBagLayout.rowHeights = new int[] { 0, 9, 0, 0 };
        gridBagLayout.columnWeights = new double[] { 0.0, 0.0, 1.0, 0.0, 0.0,
                Double.MIN_VALUE };
        gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, Double.MIN_VALUE };
        this.frame.getContentPane().setLayout(gridBagLayout);

        this.horizontal = Box.createHorizontalStrut(50);
        GridBagConstraints horizontalStrut = new GridBagConstraints();
        horizontalStrut.gridx = 0;
        horizontalStrut.gridy = 1;
        this.frame.getContentPane().add(this.horizontal, horizontalStrut);
        this.vertical = Box.createVerticalStrut(20);
        GridBagConstraints verticalStrut = new GridBagConstraints();
        verticalStrut.gridx = 1;
        verticalStrut.gridy = 0;
        this.frame.getContentPane().add(this.vertical, verticalStrut);
        this.welcomeMessage = new Label();
        Font titleFont = new Font("Arial", Font.BOLD, 20);
        this.welcomeMessage.setFont(titleFont);
        this.welcomeMessage.setText("Are you a user or researcher?");

        GridBagConstraints textFormat = new GridBagConstraints();
        textFormat.gridx = 1;
        textFormat.gridy = 1;
        this.frame.getContentPane().add(welcomeMessage, textFormat);

        this.researcherMode = new Button("Researcher");
        this.researcherMode.setEnabled(true);
        this.userMode = new Button ("User");
        this.userMode.setEnabled(true);
        GridBagConstraints userFormat = new GridBagConstraints();
        userFormat.gridx = 0;
        userFormat.gridy = 0;
        GridBagConstraints right = new GridBagConstraints();
        right.gridx = 1;
        right.gridy = 0;
        Panel buttonPanel = new Panel();
        buttonPanel.setLayout(new GridBagLayout());
        buttonPanel.add(userMode, userFormat);
        buttonPanel.add(researcherMode, right);

        GridBagConstraints button = new GridBagConstraints();
        button.gridx = 1;
        button.gridy = 2;
        button.insets = new Insets(10,5,10,5);
        this.frame.getContentPane().add(buttonPanel, button);
        userMode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                User userPage = new User(OpenPage.SC);
                frame.setVisible(false);
            }
        });

        researcherMode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Researcher userPage = new Researcher(OpenPage.SC);
                frame.setVisible(false);
            }
        });
    }

    public static void main(String[] args) {

        final OpenPage window = new OpenPage();

        JProgressBar pbProgress = new JProgressBar(0, 100);
        pbProgress.setIndeterminate(true); //we'll use an indeterminate progress bar

    }
}
