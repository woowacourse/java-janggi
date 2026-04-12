package infra.jdbc;

import domain.game.JanggiGame;
import domain.pieces.Piece;
import domain.position.Position;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class SavedGameWriteMapper {

    private final Clock clock;

    public SavedGameWriteMapper(Clock clock) {
        this.clock = clock;
    }

    public GameEntity toEntity(JanggiGame janggiGame) {
        LocalDateTime now = LocalDateTime.now(clock);
        return new GameEntity(
                janggiGame.gameId(),
                janggiGame.currentTurn(),
                janggiGame.gameResult().status(),
                janggiGame.gameResult().winner(),
                now,
                now,
                toPieceEntities(janggiGame)
        );
    }

    public List<PieceEntity> toPieceEntities(JanggiGame janggiGame) {
        return janggiGame.board().pieces().entrySet().stream()
                .filter(entry -> !entry.getValue().isEmpty())
                .map(this::toPieceEntity)
                .toList();
    }

    public LocalDateTime updatedAt() {
        return LocalDateTime.now(clock);
    }

    private PieceEntity toPieceEntity(Map.Entry<Position, Piece> entry) {
        Position position = entry.getKey();
        Piece piece = entry.getValue();

        return new PieceEntity(
                position.row(),
                position.column(),
                piece.getSide(),
                piece.getType()
        );
    }
}
