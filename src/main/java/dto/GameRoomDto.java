package dto;

import domain.place.piece.Side;
import java.time.LocalDateTime;

public record GameRoomDto(long id, String name, Side side, LocalDateTime createdAt) {
    public static GameRoomDto of(long id, String name, Side side, LocalDateTime createdAt) {
        return new GameRoomDto(id, name, side,createdAt);
    }
}
