package cat.dam.biel.ocaloka;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cat.dam.biel.ocaloka.Box;

public class Board {
    private String name;
    private List<Box> boxes;

    public Board() {
        this.name = name;
        this.boxes = new ArrayList<>();
        for (int i = 0; i < 25; i++) {
            boxes.add(new Box("", "", ""));
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Box> getBoxes() {
        return boxes;
    }

    public Box getBox(int index) {
        return boxes.get(index);
    }

    public void setBoxes(List<Box> boxes) {
        this.boxes = boxes;
    }

    public void setBox(int index, Box box) {
        boxes.set(index, box);
    }

    public int getNumFilledBoxes() {
        int count = 0;
        for (int i = 0; i < boxes.size(); i++) {
            if (boxes.get(i).isFilled()) {
                count++;
            }
        }
        return count;
    }
}