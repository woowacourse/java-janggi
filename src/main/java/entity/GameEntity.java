package entity;

import java.time.OffsetDateTime;

public class GameEntity {
    private final Long id;
    private final String currentTurn;
    private final String status;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;

    public GameEntity(String currentTurn, String status) {
        this(null, currentTurn, status);
    }

    public GameEntity(Long id, String currentTurn, String status) {
        this.id = id;
        this.currentTurn = currentTurn;
        this.status = status;
    }

    public static GameEntity from(String currentTurn, String status) {
        return new GameEntity(
                currentTurn,
                status
        );
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

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public OffsetDateTime getUpdatedAt() {
        return updatedAt;
    }
}
