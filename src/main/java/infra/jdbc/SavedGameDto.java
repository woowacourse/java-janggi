package infra.jdbc;

import domain.game.GameStatus;
import domain.pieces.Side;
import java.time.LocalDateTime;
import java.util.List;

public record SavedGameDto(
        Long gameId,
        Side currentTurn,
        GameStatus status,
        Side winner,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        List<SavedPieceDto> pieces
) {
}
