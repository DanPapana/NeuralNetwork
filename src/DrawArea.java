import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

public class DrawArea extends JComponent {

    private int vector_number;
    private int size = 64;                                                               //size of the vectorized image
    private Image image;                                                          // Image in which we're going to draw
    private Graphics2D g2;                                                         // Graphics2D object used to draw on
    private ArrayList<Point> points = new ArrayList<>();                                           // Mouse coordinates

    DrawArea(int vector_number, NeuralNetwork network) {

        this.vector_number = vector_number;

        setDoubleBuffered(false);
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                clear();
                points.add(new Point(e.getX(), e.getY()));
            }

            public void mouseReleased(MouseEvent e) {

                if (points.size() > 5) {                                     //So that we can still click on the canvas

                    points = vectorize();
                    LearningFrame learning = new LearningFrame();

                    learning.getLearnButton().addActionListener(e12 -> {

                        if (!learning.getTrainingCharacter().equals("") && Integer.parseInt(learning.getTrainingCharacter()) < 10) {

                            network.addSample(new InputLayer(points, Integer.parseInt(learning.getTrainingCharacter())));
                            learning.dispose();
                        }
                    });

                    learning.getGuessButton().addActionListener(e1 -> {
                        learning.setGuessedNumber(network.guessNumber(new InputLayer(points, 0)));
                    });

                    g2.setPaint(Color.orange);
                    g2.fillRect(0, 0, getSize().width, getSize().height);
                    g2.setPaint(Color.black);

                    drawPoints(points);
                    repaint();
                }
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {

            public void mouseDragged(MouseEvent e) {
                points.add(new Point(e.getX(), e.getY()));

                if (g2 != null) {
                    g2.drawLine(points.get(points.size() - 2).x , points.get(points.size() - 2).y,
                            points.get(points.size() - 1).x , points.get(points.size() - 1).y);
                    repaint();
                }
            }
        });
    }

    private void drawPoints(ArrayList<Point> new_points) {
        if (g2 != null) {
            for (int i = 0; i < new_points.size() - 2; i++) {
                g2.drawLine(new_points.get(i).x, new_points.get(i).y,
                        new_points.get(i + 1).x, new_points.get(i + 1).y);
            }
            repaint();
        }
    }

    private DrawArea getDrawArea() {
        return this;
    }

    protected void paintComponent(Graphics g) {
        if (image == null) {
            image = createImage(getSize().width, getSize().height);
            g2 = (Graphics2D) image.getGraphics();
            clear();
        }
        g.drawImage(image, 0, 0, null);
    }

    private ArrayList<Point> vectorize() {

        int max_X = points.get(0).x;
        int max_Y = points.get(0).y;
        int min_X = points.get(0).x;
        int min_Y = points.get(0).y;

        for (Point point : points) {
            if (point.x > max_X) {
                max_X = point.x;
            }
            if (point.y > max_Y) {
                max_Y = point.y;
            }
            if (point.x < min_X) {
                min_X = point.x;
            }
            if (point.y < min_Y) {
                min_Y = point.y;
            }
        }

        for (Point point : points) {
            point.x -= min_X;
            point.y -= min_Y;
            point.x = (point.x * size) / (max_X - min_X);
            point.y = (point.y * size) / (max_Y - min_Y);
        }

        while (points.size() > vector_number) {
            for (int i = 1; i <= points.size() - 2; i++) {

                points.set(i, new Point((points.get(i).x + points.get(i + 1).x) / 2,
                        (points.get(i).y + points.get(i + 1).y) / 2));
            }
            points.remove(points.size() - 2);
        }
        return points;
    }

    private void clear() {                                                                       // The clearing method
        points.clear();
        g2.setPaint(Color.orange);
        g2.fillRect(0, 0, getSize().width, getSize().height);
        g2.setPaint(Color.black);
        g2.setStroke(new BasicStroke(3));
        repaint();
    }
}