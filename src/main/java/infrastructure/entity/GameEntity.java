package infrastructure.entity;

import domain.game.Game;
import domain.piece.Country;

public class GameEntity {

    private final Long id;
    private final String name;
    private final String currentTurn;

    public GameEntity(Long id, String name, String currentTurn) {
        this.id = id;
        this.name = name;
        this.currentTurn = currentTurn;
    }

    public static GameEntity from(Game joinGame) {
        return new GameEntity(
                null,
                joinGame.getName(),
                joinGame.getCurrentName()
        );
    }

    public Game toDomain() {
        return new Game(
                name,
                Country.valueOf(currentTurn)
        );
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCurrentTurn() {
        return currentTurn;
    }

}
