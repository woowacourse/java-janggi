package dto;

import java.time.LocalDateTime;

public record GameRoomDto(long id, String name, LocalDateTime createdAt) {
    public static GameRoomDto of(long id, String name, LocalDateTime createdAt) {
        return new GameRoomDto(id, name, createdAt);
    }
}
