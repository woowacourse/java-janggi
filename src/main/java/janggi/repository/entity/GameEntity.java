package janggi.repository.entity;

public class GameEntity {

    private Long id;
    private final String turn;
    private final boolean isActive;

    public GameEntity(String turn, boolean isActive) {
        this.turn = turn;
        this.isActive = isActive;
    }

    public GameEntity(Long id, String turn, boolean isActive) {
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
