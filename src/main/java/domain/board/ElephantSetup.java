package domain.board;

import static domain.piece.PieceType.ELEPHANT;
import static domain.piece.PieceType.HORSE;

import domain.piece.PieceType;
import domain.piece.Position;
import java.util.Map;

public enum ElephantSetup {
    InnerElephantSetup(Map.of(
            Position.of(1, 2), HORSE,
            Position.of(1, 3), ELEPHANT,
            Position.of(1, 7), ELEPHANT,
            Position.of(1, 8), HORSE
    )),
    OuterElephantSetup(Map.of(
            Position.of(1, 2), ELEPHANT,
            Position.of(1, 3), HORSE,
            Position.of(1, 7), HORSE,
            Position.of(1, 8), ELEPHANT
    )),
    RightElephantSetup(Map.of(
            Position.of(1, 2), HORSE,
            Position.of(1, 3), ELEPHANT,
            Position.of(1, 7), HORSE,
            Position.of(1, 8), ELEPHANT
    )),
    LeftElephantSetup(Map.of(
            Position.of(1, 2), ELEPHANT,
            Position.of(1, 3), HORSE,
            Position.of(1, 7), ELEPHANT,
            Position.of(1, 8), HORSE
    ));

    private final Map<Position, PieceType> piecePositions;

    ElephantSetup(final Map<Position, PieceType> piecePositions) {
        this.piecePositions = piecePositions;
    }

    public Map<Position, PieceType> getPiecePositions() {
        return piecePositions;
    }
}
