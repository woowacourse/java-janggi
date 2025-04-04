package domain.entity;

import domain.game.Turn;

public class JanggiGameEntity {

    private final Long id;

    private final Turn turn;

    public JanggiGameEntity(Long id, Turn turn) {
        this.id = id;
        this.turn = turn;
    }

    public JanggiGameEntity(Turn turn) {
        this(null, turn);
    }

    public Turn getTurn() {
        return turn;
    }
}
