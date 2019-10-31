import javax.swing.*;
import java.awt.*;

class MenuPanel extends JPanel {

    private JTextPane vectors_per_pattern = new JTextPane();
    private JTextPane hidden_neurons = new JTextPane();
    private JTextPane learning_rate = new JTextPane();
    private JTextPane error_tolerance = new JTextPane();
    private JPanel mainMenuPanel = new JPanel(new FlowLayout());
    private JButton changeButton = new JButton("RESTART");

    MenuPanel() {

        changeButton.setPreferredSize(new Dimension(80, 30));
        JPanel menuPanel = new JPanel();
        menuPanel.setPreferredSize(new Dimension(290, 30));
        menuPanel.setLayout(new GridLayout(2, 4));
        menuPanel.add(new JLabel("Vectors"));
        menuPanel.add(new JLabel("Neurons"));
        menuPanel.add(new JLabel("LearningRate"));
        menuPanel.add(new JLabel("ErrorTolerance"));

        menuPanel.add(vectors_per_pattern);
        menuPanel.add(hidden_neurons);
        menuPanel.add(learning_rate);
        menuPanel.add(error_tolerance);

        mainMenuPanel.add(menuPanel);
        mainMenuPanel.add(changeButton);

        vectors_per_pattern.setText("200");
        hidden_neurons.setText("16");
        learning_rate.setText("0.25");
        error_tolerance.setText("0.005");

        vectors_per_pattern.setBorder(BorderFactory.createLineBorder(Color.ORANGE));
        hidden_neurons.setBorder(BorderFactory.createLineBorder(Color.ORANGE));
        learning_rate.setBorder(BorderFactory.createLineBorder(Color.ORANGE));
        error_tolerance.setBorder(BorderFactory.createLineBorder(Color.ORANGE));

        vectors_per_pattern.setPreferredSize(new Dimension(50, 20));
        hidden_neurons.setPreferredSize(new Dimension(50, 20));
        learning_rate.setPreferredSize(new Dimension(50, 20));
        error_tolerance.setPreferredSize(new Dimension(50, 20));
    }

    int getVectorPattern() {
        return Integer.parseInt(vectors_per_pattern.getText());
    }

    int getHiddenNeurons() {
        return Integer.parseInt(hidden_neurons.getText());
    }

    double getLearningRate() {
        return Double.parseDouble(learning_rate.getText());
    }

    double getErrorTolerance() {
        return Double.parseDouble(error_tolerance.getText());
    }

    JPanel getMenuPanel() {
        return mainMenuPanel;
    }

    JButton getChangeButton() {
        return changeButton;
    }
}
