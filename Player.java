package cat.dam.biel.ocaloka;


public class Player {
    private String name;
    private int actualPosition;

    // Constructor
    public Player(String name) {
        this.name = name;
        this.actualPosition = 0;
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

}