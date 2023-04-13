package cat.dam.biel.ocaloka;

public class Player {
    private String name;
    private int actualPosition;
    private boolean isOwner;
    private String color; // New attribute for color

    // Constructor
    public Player(String name) {
        this.name = name;
        this.actualPosition = 0;
        this.isOwner = false;
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public int getActualPosition() {
        return actualPosition;
    }

    public void setActualPosition(int score) {
        this.actualPosition = score;
    }

    public boolean isOwner() {
        return isOwner;
    }

    public void setOwner(boolean isOwner) {
        this.isOwner = isOwner;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

}
