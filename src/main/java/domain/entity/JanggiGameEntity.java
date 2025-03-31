package domain.entity;

import domain.game.Turn;

public class JanggiGameEntity {

    private Long id;

    private Turn turn;

    public JanggiGameEntity(Long id, Turn turn) {
        this.id = id;
        this.turn = turn;
    }

    public JanggiGameEntity(Turn turn) {
        this(-1L, turn);
    }

    public Turn getTurn() {
        return turn;
    }
}
