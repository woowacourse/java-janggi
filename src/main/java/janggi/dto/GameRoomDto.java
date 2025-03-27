package janggi.dto;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public record GameRoomDto(
        int roomId,
        int boardId,
        String turnColor,
        String winner,
        boolean isFinished,
        int redScore,
        int blueScore,
        LocalDateTime startTime,
        LocalDateTime last_updated
) {
    public static GameRoomDto createForShowRooms(int roomId, int boardId, String turnColor, Timestamp startTime,
                                                 Timestamp lastUpdated) {
        return new GameRoomDto(roomId, boardId, turnColor, null, false, 0, 0, startTime.toLocalDateTime(),
                lastUpdated.toLocalDateTime());
    }

    public static GameRoomDto createForState(int roomId, String turnColor, int redScore, int blueScore) {
        return new GameRoomDto(roomId, 0, turnColor, turnColor, true, redScore, blueScore, null, null);
    }
}
