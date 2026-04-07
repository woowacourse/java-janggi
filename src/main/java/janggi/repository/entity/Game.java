package janggi.repository.entity;

public class Game {

    private Long id;
    private final String turn;
    private final boolean isActive;

    public Game(String turn, boolean isActive) {
        this.turn = turn;
        this.isActive = isActive;
    }

    public Game(Long id, String turn, boolean isActive) {
        this.id = id;
        this.turn = turn;
        this.isActive = isActive;
    }

    public Long getId() {
        return id;
    }

    public String getTurn() {
        return turn;
    }

    public boolean isActive() {
        return isActive;
    }
}
