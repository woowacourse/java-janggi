package repository.dto;

import java.time.OffsetDateTime;

public class GameDto {
    private final Long id;
    private final OffsetDateTime updatedAt;

    public GameDto(Long id, OffsetDateTime updatedAt) {
        this.id = id;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public OffsetDateTime getUpdatedAt() {
        return updatedAt;
    }
}
