import javafx.embed.swing.JFXPanel;
import javax.swing.*;
import java.awt.*;


public class Researcher {
    private final ScenarioRecord SC;
    public Researcher(ScenarioRecord sc) {
        this.SC = sc;
        initialize();
    }
    private JFrame frame;

    private void initialize() {
        this.frame = new JFrame();
        this.frame.setTitle("Set Scenario");
        this.frame.setVisible(true);
        this.frame.setBounds(100, 100, 283, 300);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.getContentPane().setLayout(new BorderLayout(0, 0));
    }

}
