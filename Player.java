package cat.dam.biel.ocaloka;

public class Player {
    private String name;
    private String email; // New field for email
    private int actualPosition;
    private int posicioAnterior;
    private boolean isOwner;
    private String color; // New field for color

    // Constructor
    public Player(String name, String email) {
        this.name = name;
        this.email = email;
        this.actualPosition = 0;
        this.isOwner = false;
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
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

    public int getPosicioAnterior() {
        return posicioAnterior;
    }

    public void setPosicioAnterior(int posicioAnterior) {
        this.posicioAnterior = posicioAnterior;
    }
}
