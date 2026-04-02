package domain.board;

import domain.piece.Piece;
import domain.setup.Arrangements;
import domain.piece.PieceType;
import domain.piece.Team;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PositionLayout {
    private static final Map<Position, PieceType> HAN_PIECES_LAYOUT = Map.ofEntries(
            Map.entry(new Position(Column.A, Row.ZERO), PieceType.CHARIOT),
            Map.entry(new Position(Column.D, Row.ZERO), PieceType.GUARD),
            Map.entry(new Position(Column.E, Row.ONE), PieceType.GENERAL),
            Map.entry(new Position(Column.F, Row.ZERO), PieceType.GUARD),
            Map.entry(new Position(Column.I, Row.ZERO), PieceType.CHARIOT),
            Map.entry(new Position(Column.B, Row.TWO), PieceType.CANNON),
            Map.entry(new Position(Column.H, Row.TWO), PieceType.CANNON),
            Map.entry(new Position(Column.A, Row.THREE), PieceType.SOLDIER),
            Map.entry(new Position(Column.C, Row.THREE), PieceType.SOLDIER),
            Map.entry(new Position(Column.E, Row.THREE), PieceType.SOLDIER),
            Map.entry(new Position(Column.G, Row.THREE), PieceType.SOLDIER),
            Map.entry(new Position(Column.I, Row.THREE), PieceType.SOLDIER)
    );

    private static final List<Column> INNER_COLUMNS = List.of(Column.B, Column.C, Column.G, Column.H);

    public static Map<Position, Piece> build(Arrangements arrangements) {
        Map<Position, Piece> result = new HashMap<>();
        placeHanPieces(result);
        placeHanInnerPieces(result, arrangements);
        placeChoPieces(result);
        placeCHoInnerPieces(result, arrangements);
        return result;
    }

    private static void placeHanPieces(Map<Position, Piece> result) {
        HAN_PIECES_LAYOUT.forEach((position, pieceType) ->
                result.put(position, new Piece(Team.HAN, pieceType)));
    }

    private static void placeHanInnerPieces(Map<Position, Piece> result, Arrangements arrangements) {
        List<PieceType> innerPieces = arrangements.arrangeFor(Team.HAN).innerPieces();
        for (int index = 0; index < INNER_COLUMNS.size(); index++) {
            result.put(new Position(INNER_COLUMNS.get(index), Row.ZERO), new Piece(Team.HAN, innerPieces.get(index)));
        }
    }

    private static void placeChoPieces(Map<Position, Piece> result) {
        HAN_PIECES_LAYOUT.forEach((position, pieceType) -> {
            Position reversedPosition = new Position(position.column(), position.row().reverse());
            result.put(reversedPosition, new Piece(Team.CHO, pieceType));
        });
    }

    private static void placeCHoInnerPieces(Map<Position, Piece> result, Arrangements arrangements) {
        List<PieceType> innerPieces = arrangements.arrangeFor(Team.CHO).innerPieces();
        for (int index = 0; index < INNER_COLUMNS.size(); index++) {
            result.put(new Position(INNER_COLUMNS.get(index), Row.ZERO.reverse()),
                    new Piece(Team.CHO, innerPieces.get(index)));
        }
    }

}
