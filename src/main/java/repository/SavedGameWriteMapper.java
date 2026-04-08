package repository;

import domain.game.JanggiGame;
import domain.pieces.Piece;
import domain.pieces.Side;
import domain.position.Position;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class SavedGameWriteMapper {

    private final Clock clock;

    public SavedGameWriteMapper(Clock clock) {
        this.clock = clock;
    }

    public SavedGameDto toSavedGameDto(JanggiGame janggiGame) {
        LocalDateTime now = LocalDateTime.now(clock);
        return toSavedGameDto(null, now, janggiGame);
    }

    public SavedGameDto toSavedGameDto(Long gameId, LocalDateTime createdAt, JanggiGame janggiGame) {
        return new SavedGameDto(
                gameId,
                janggiGame.currentTurn(),
                janggiGame.gameResult().status(),
                janggiGame.gameResult().winner(),
                createdAt,
                LocalDateTime.now(clock),
                janggiGame.board().pieces().entrySet().stream()
                        .filter(entry -> !entry.getValue().isEmpty())
                        .sorted(byPosition())
                        .map(this::toSavedPieceDto)
                        .toList()
        );
    }

    private Comparator<Map.Entry<Position, Piece>> byPosition() {
        return Comparator.comparingInt((Map.Entry<Position, Piece> entry) -> entry.getKey().row())
                .thenComparingInt(entry -> entry.getKey().column());
    }

    private SavedPieceDto toSavedPieceDto(Map.Entry<Position, Piece> entry) {
        Position position = entry.getKey();
        Piece piece = entry.getValue();

        return new SavedPieceDto(
                position.row(),
                position.column(),
                piece.isCho() ? Side.CHO : Side.HAN,
                piece.getType()
        );
    }
}
