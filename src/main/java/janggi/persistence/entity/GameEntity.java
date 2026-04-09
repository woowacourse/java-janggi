package janggi.persistence.entity;

import janggi.persistence.entity.vo.Status;
import janggi.persistence.entity.vo.Turn;

public class GameEntity {
    private String id;
    private String name;
    private Status status;
    private Turn turn;

    public GameEntity(String id, String name, Status status, Turn turn) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.turn = turn;
    }

    public String id() {
        return id;
    }

    public String name() {
        return name;
    }

    public Status status() {
        return status;
    }

    public Turn turn() {
        return turn;
    }
}
