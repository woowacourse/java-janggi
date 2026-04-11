package janggi.persistence.entity;

import janggi.persistence.entity.vo.Status;
import janggi.persistence.entity.vo.Turn;

public class GameEntity {
    private String id;
    private String name;
    private Status status;
    private Turn turn;

    public GameEntity(String id, String name, Status status, Turn turn) {
        validateNameLength(name);
        this.id = id;
        this.name = name;
        this.status = status;
        this.turn = turn;
    }

    private void validateNameLength(String name) {
        if (name.length() < 1 || name.length() > 50) {
            throw new IllegalArgumentException("게임 이름 길이는 1이상 50 이하여야 합니다.");
        }
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
