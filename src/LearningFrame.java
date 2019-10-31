import javax.swing.*;
import java.awt.*;

class LearningFrame extends JDialog {

    private JTextPane trainingCharacter = new JTextPane();
    private JButton learnButton = new JButton("LEARN");
    private JButton guessButton = new JButton("GUESS");

    LearningFrame()
    {
        setLocation(800, 280);

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {
        }

        this.setTitle("WHAT DOES IT MEAN?");
        this.setSize(210, 80);
        this.setResizable(false);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new FlowLayout());

        trainingCharacter.setPreferredSize(new Dimension(30, 20));
        learnButton.setPreferredSize(new Dimension(70, 30));
        guessButton.setPreferredSize(new Dimension(70, 30));

        mainPanel.add(trainingCharacter);
        mainPanel.add(learnButton);
        mainPanel.add(guessButton);

        this.add(mainPanel);
        this.setVisible(true);
    }

    JButton getLearnButton() {
        return learnButton;
    }

    JButton getGuessButton() {
        return guessButton;
    }

    void setGuessedNumber(int number) {
        trainingCharacter.setText(String.valueOf(number));
    }

    String getTrainingCharacter() {
        return trainingCharacter.getText();
    }
}
