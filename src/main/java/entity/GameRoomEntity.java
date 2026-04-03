package entity;

import java.time.LocalDateTime;

public record GameRoomEntity(long id, String name, LocalDateTime createdAt) {
    public static GameRoomEntity of(long id, String name, LocalDateTime createdAt) {
        return new GameRoomEntity(id, name, createdAt);
    }
}
