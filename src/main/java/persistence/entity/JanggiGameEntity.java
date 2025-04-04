package persistence.entity;

import domain.game.Turn;

public record JanggiGameEntity(
        Long id,
        Turn turn
) {

    public JanggiGameEntity(Turn turn) {
        this(null, turn);
    }
}
