package janggi.db.entity;

import janggi.domain.common.Team;

public class GameEntity {
    private final Long id;
    private final Team turn;
    private final boolean isFinished;

    public GameEntity(Long id, Team turn, boolean isFinished) {
        this.id = id;
        this.turn = turn;
        this.isFinished = isFinished;
    }

    public Long getId() {
        return id;
    }

    public Team getTurn() {
        return turn;
    }

    public boolean isFinished() {
        return isFinished;
    }
}
