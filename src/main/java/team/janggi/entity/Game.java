package team.janggi.entity;

public class Game {
    private final int id;
    private final String gameName;
    private final String currentTurn;

    public Game(int id, String gameName, String currentTurn) {
        this.id = id;
        this.gameName = gameName;
        this.currentTurn = currentTurn;
    }

    public int getId() {
        return id;
    }

    public String getCurrentTurn() {
        return currentTurn;
    }

    public String getGameName() {
        return gameName;
    }

}
