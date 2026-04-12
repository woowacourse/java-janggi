package janggi.persistence.entity;

import janggi.domain.Camp;
import janggi.persistence.entity.vo.Status;

import java.util.Objects;

public class GameEntity {
    private String id;
    private String name;
    private Status status;
    private Camp camp;

    public GameEntity(String id, String name, Status status, Camp camp) {
        validateNameLength(name);
        this.id = id;
        this.name = name;
        this.status = status;
        this.camp = camp;
    }

    private void validateNameLength(String name) {
        if (name.length() < 1 || name.length() > 50) {
            throw new IllegalArgumentException("게임 이름 길이는 1이상 50 이하여야 합니다.");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof GameEntity other)) return false;
        return Objects.equals(id, other.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
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

    public Camp camp() {
        return camp;
    }
}
