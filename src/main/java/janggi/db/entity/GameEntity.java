package janggi.db.entity;

public class GameEntity {

    private final Long id;
    private final String turn;

    public GameEntity(Long id, String turn) {
        this.id = id;
        this.turn = turn;
    }

    public Long getId() {
        return id;
    }

    public String getTurn() {
        return turn;
    }
}
