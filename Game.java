package cat.dam.biel.ocaloka;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private String name;
    private List<Player> players;
    private Board board;
    private int currentPlayerIndex;
    private boolean isStarted;

    public Game(String name) {
        this.name = name;
        players = new ArrayList<>();
        board = null;
        currentPlayerIndex = 0;
        isStarted = false;
    }

    public String getName() {
        return name;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public Board getBoard() {
        return board;
    }

    public int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }

    public void setCurrentPlayerIndex(int currentPlayerIndex) {
        this.currentPlayerIndex = currentPlayerIndex;
    }

    public boolean isStarted() {
        return isStarted;
    }

    public void setStarted(boolean started) {
        isStarted = started;
    }
}
