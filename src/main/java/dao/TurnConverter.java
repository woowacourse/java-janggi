package dao;

import game.Turn;

public class TurnConverter {

    public static TurnEntity toEntity(final Turn turn) {
        return new TurnEntity(null, turn.getValue());
    }

    public static Turn toTurn(final TurnEntity turnEntity) {
        return new Turn(turnEntity.value());
    }

}
