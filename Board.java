package cat.dam.biel.ocaloka;

import cat.dam.biel.ocaloka.Box;

public class Board {
    private String name;
    private Box[] boxes;

    public Board() {
        this.name = name;
        this.boxes = new Box[25];
        initializeBoxes();
    }

    private void initializeBoxes() {
        for (int i = 0; i < 25; i++) {
            boxes[i] = new Box("", "", "");
        }
    }

    // Getter and setter methods for the name and boxes properties
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Box[] getBoxes() {
        return boxes;
    }

    public void setBoxes(Box[] boxes) {
        this.boxes = boxes;
    }
    public void fillBox(int index, Box box) {
        if (!boxes[index].isFilled()) {
            boxes[index] = box;
            boxes[index].setFilled(true);
            if (isBoardFilled()) {
                // Do something with the filled board, such as saving it to a database
            }
        }
    }

    public boolean isBoxFilled(int index) {
        return boxes[index].isFilled();
    }
    public boolean isBoardFilled() {
        for (int i = 0; i < boxes.length; i++) {
            if (!boxes[i].isFilled()) {
                return false;
            }
        }
        return true;
    }
    public Box getBox(int index) {
        return boxes[index];
    }
}