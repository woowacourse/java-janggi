package janggi.dto;

import java.time.LocalDateTime;

public record GameRoomDto(
        int roomId,
        String turnColor,
        String winner,
        boolean isFinished,
        int redScore,
        int blueScore,
        LocalDateTime startTime,
        LocalDateTime last_updated
) {
    public static GameRoomDto createForShowRooms(int roomId, String turnColor, LocalDateTime startTime,
                                                 LocalDateTime lastUpdated) {
        return new GameRoomDto(roomId, turnColor, null, false, 0, 0, startTime,
                lastUpdated);
    }

    public static GameRoomDto createForState(int roomId, String turnColor, int redScore, int blueScore) {
        return new GameRoomDto(roomId, turnColor, turnColor, true, redScore, blueScore, null, null);
    }
}
