package janggi.dao.entity;

import janggi.domain.piece.Dynasty;
import java.util.Objects;

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

    public void setCurrentTurn(Dynasty currentTurn) {
        this.currentTurn = currentTurn;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        GameEntity that = (GameEntity) o;
        return Objects.equals(id, that.id) && status == that.status && currentTurn == that.currentTurn;
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(id);
        result = 31 * result + Objects.hashCode(status);
        result = 31 * result + Objects.hashCode(currentTurn);
        return result;
    }
}
