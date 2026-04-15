package dao;

public record GameRoomRawData(
        long id,
        String name,
        String currentTurn,
        String status,
        int consecutivePassCount
) {
}
