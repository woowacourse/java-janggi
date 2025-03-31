package janggi.dao.entity;

import static janggi.dao.entity.Status.END;

import janggi.domain.piece.Dynasty;
import java.util.Objects;

public class GameEntity {

    private Long id;
    private String name;
    private Status status;
    private Dynasty currentTurn;

    public GameEntity(Long id, String name, Status status, Dynasty currentTurn) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.currentTurn = currentTurn;
    }

    public GameEntity(String name, Status status, Dynasty currentTurn) {
        this.name = name;
        this.status = status;
        this.currentTurn = currentTurn;
    }

    public boolean isEnd() {
        return this.status == END;
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

    public void setCurrentTurn(Dynasty currentTurn) {
        this.currentTurn = currentTurn;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        GameEntity that = (GameEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && status == that.status
                && currentTurn == that.currentTurn;
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(id);
        result = 31 * result + Objects.hashCode(name);
        result = 31 * result + Objects.hashCode(status);
        result = 31 * result + Objects.hashCode(currentTurn);
        return result;
    }
}
