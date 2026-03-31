package entity;

import java.time.LocalDateTime;

public record GameRoomEntity(long id, String name, LocalDateTime createdAt) {
}
