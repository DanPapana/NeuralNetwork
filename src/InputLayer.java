import java.awt.*;
import java.util.ArrayList;

class InputLayer {

    private int[] inputArray = new int[4097]; // 64 x 64 + 1
    private ArrayList<Point> training_data;
    private int output;

    InputLayer(ArrayList<Point> new_points, int character) {
        training_data = new_points;
        output = character;

        int k = 1;
        for(int i = 0; i < 64; i++) {
            for (int j = 0; j < 64; j++) {
                inputArray[k] = getValue(i, j);
                k++;
            }
        }
    }

    private int getValue(int x, int y) {
        for (Point training_datum : training_data) {
            if (training_datum.x == x && training_datum.y == y) { //1 for when a point is in a grid, 0 for when it is not
                return 1;
            }
        }
        return 0;
    }

    int getInput(int j) {
        return inputArray[j];
    }

    int getOutput() {
        return output;
    }
}
