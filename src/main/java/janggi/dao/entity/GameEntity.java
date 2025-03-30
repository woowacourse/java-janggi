package janggi.dao.entity;

import janggi.domain.piece.Dynasty;

public class GameEntity {

    private Long id;
    private Status status;
    private Dynasty currentTurn;

    public GameEntity(Long id, Status status, Dynasty currentTurn) {
        this.id = id;
        this.status = status;
        this.currentTurn = currentTurn;
    }

    public GameEntity(Status status, Dynasty currentTurn) {
        this.status = status;
        this.currentTurn = currentTurn;
    }

    public Long getId() {
        return id;
    }

    public Status getStatus() {
        return status;
    }

    public Dynasty getCurrentTurn() {
        return currentTurn;
    }
}
