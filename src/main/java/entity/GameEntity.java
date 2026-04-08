package entity;

import java.time.OffsetDateTime;

public class GameEntity {
    private final Long id;
    private final String currentTurn;
    private final String status;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;

    public GameEntity(String currentTurn, String status) {
        this(null, currentTurn, status, null);
    }

    public GameEntity(Long id, String currentTurn, String status, OffsetDateTime updatedAt) {
        this.id = id;
        this.currentTurn = currentTurn;
        this.status = status;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public String getCurrentTurn() {
        return currentTurn;
    }

    public String getStatus() {
        return status;
    }

    public OffsetDateTime getUpdatedAt() {
        return updatedAt;
    }
}
