package domain.entity;

import java.time.LocalDateTime;

public class GameRoomEntity {

    private final Long id;
    private final boolean isFinished;
    private final LocalDateTime createdAt;

    public GameRoomEntity(Long id, boolean isFinished, LocalDateTime createdAt) {
        this.id = id;
        this.isFinished = isFinished;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
