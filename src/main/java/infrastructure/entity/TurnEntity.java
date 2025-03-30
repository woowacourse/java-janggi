package infrastructure.entity;

import domain.game.Turn;
import domain.piece.Country;

public class TurnEntity {

    private final String currentTurn;

    public TurnEntity(String currentTurn) {
        this.currentTurn = currentTurn;
    }

    public static TurnEntity from(Turn current) {
        return new TurnEntity(current.getCountry().name());
    }

    public Turn toDomain() {
        return new Turn(Country.valueOf(currentTurn));
    }

    public String getCurrentTurn() {
        return currentTurn;
    }

}
