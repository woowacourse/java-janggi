package janggi.entity;

import janggi.domain.dynasty.Dynasty;
import janggi.domain.game.CurrentTurn;
import janggi.domain.game.Game;

public record TurnEntity(
        String currentTurn
) {

    public static TurnEntity toEntity(String currentTurn) {
        return new TurnEntity(currentTurn);
    }

    public static TurnEntity toEntity(Game game) {
        return new TurnEntity(game.currentDynasty().name());
    }

    public static CurrentTurn toDomain(TurnEntity entity) {
        return new CurrentTurn(Dynasty.valueOf(entity.currentTurn));
    }

}
