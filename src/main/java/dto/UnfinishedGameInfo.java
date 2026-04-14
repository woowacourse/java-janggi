package dto;

import java.time.LocalDateTime;

public record UnfinishedGameInfo(long gameId, LocalDateTime lastPlayedAt) {
    public static UnfinishedGameInfo from(long gameId, LocalDateTime lastPlayedAt) {
        return new UnfinishedGameInfo(gameId, lastPlayedAt);
    }
}
