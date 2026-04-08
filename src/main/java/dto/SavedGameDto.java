package dto;

import java.time.LocalDateTime;

public record SavedGameDto(
        int gameId,
        LocalDateTime modifiedDate
) {
}
