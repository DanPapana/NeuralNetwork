import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class MainFrame extends JFrame {

    MainFrame() {

        setLocation(550, 200);

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {
        }

        this.setTitle("Neural networks");
        this.setSize(400, 500);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);

        MenuPanel param = new MenuPanel();
        JPanel MainPanel = new JPanel();
        MainPanel.setLayout(new BorderLayout());
        MainPanel.add(param.getMenuPanel(), BorderLayout.SOUTH);
        NeuralNetwork network = new NeuralNetwork(param.getHiddenNeurons(), param.getLearningRate(), param.getErrorTolerance());
        MainPanel.add(new DrawArea(param.getVectorPattern(), network));

        param.getChangeButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainPanel.removeAll();
                MainPanel.add(param.getMenuPanel(), BorderLayout.SOUTH);
                NeuralNetwork network = new NeuralNetwork(param.getHiddenNeurons(), param.getLearningRate(), param.getErrorTolerance());
                MainPanel.add(new DrawArea(param.getVectorPattern(), network));

                revalidate();
            }
        });

        this.add(MainPanel);
        this.setVisible(true);
    }
}