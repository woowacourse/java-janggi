package domain.board;

import domain.piece.Piece;
import domain.piece.PieceType;
import java.util.HashMap;
import java.util.Map;

public final class BoardInitializer {

    private static final Map<Position, PieceType> HAN_INITIAL_POSITIONS = Map.ofEntries(
            Map.entry(Position.of(1, 1), PieceType.CHARIOT),
            Map.entry(Position.of(1, 4), PieceType.GUARD),
            Map.entry(Position.of(1, 6), PieceType.GUARD),
            Map.entry(Position.of(1, 9), PieceType.CHARIOT),

            Map.entry(Position.of(2, 5), PieceType.GENERAL),

            Map.entry(Position.of(3, 2), PieceType.CANNON),
            Map.entry(Position.of(3, 8), PieceType.CANNON),

            Map.entry(Position.of(4, 1), PieceType.SOLDIER),
            Map.entry(Position.of(4, 3), PieceType.SOLDIER),
            Map.entry(Position.of(4, 5), PieceType.SOLDIER),
            Map.entry(Position.of(4, 7), PieceType.SOLDIER),
            Map.entry(Position.of(4, 9), PieceType.SOLDIER)
    );

    private static final Map<Position, PieceType> CHO_INITIAL_POSITIONS = Map.ofEntries(
            Map.entry(Position.of(10, 1), PieceType.CHARIOT),
            Map.entry(Position.of(10, 4), PieceType.GUARD),
            Map.entry(Position.of(10, 6), PieceType.GUARD),
            Map.entry(Position.of(10, 9), PieceType.CHARIOT),

            Map.entry(Position.of(9, 5), PieceType.GENERAL),

            Map.entry(Position.of(8, 2), PieceType.CANNON),
            Map.entry(Position.of(8, 8), PieceType.CANNON),

            Map.entry(Position.of(7, 1), PieceType.SOLDIER),
            Map.entry(Position.of(7, 3), PieceType.SOLDIER),
            Map.entry(Position.of(7, 5), PieceType.SOLDIER),
            Map.entry(Position.of(7, 7), PieceType.SOLDIER),
            Map.entry(Position.of(7, 9), PieceType.SOLDIER)
    );

    private BoardInitializer() {
    }

    public static Board init(final ElephantSetup choElephantSetup, final ElephantSetup hanElephantSetup) {
        Map<Position, Piece> pieces = new HashMap<>();

        CHO_INITIAL_POSITIONS.forEach((position, pieceType) -> pieces.put(position, Piece.choPieceOf(pieceType)));
        HAN_INITIAL_POSITIONS.forEach((position, pieceType) -> pieces.put(position, Piece.hanPieceOf(pieceType)));

        choElephantSetup.getPiecePositions()
                .forEach((position, pieceType) -> pieces.put(position.mirror(), Piece.choPieceOf(pieceType)));

        hanElephantSetup.getPiecePositions()
                .forEach((position, pieceType) -> pieces.put(position, Piece.hanPieceOf(pieceType)));

        return Board.init(pieces);
    }
}
