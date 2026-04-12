package janggi.dto;

import janggi.db.entity.GameEntity;

public class GameResponse {

    private final Long id;
    private final String turn;

    private GameResponse(Long id, String turn) {
        this.id = id;
        this.turn = turn;
    }

    public static GameResponse from(GameEntity entity) {
        return new GameResponse(entity.getId(), entity.getTurn().name());
    }

    public Long getId() {
        return id;
    }

    public String getTurn() {
        return turn;
    }
}
