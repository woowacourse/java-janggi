package infra.jdbc;

import domain.game.GameStatus;
import domain.pieces.Side;
import java.time.LocalDateTime;
import java.util.List;

public record GameEntity(
        Long gameId,
        Side currentTurn,
        GameStatus status,
        Side winner,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        List<PieceEntity> pieces
) {
}
