package domain.game;

import domain.vo.Arrangements;
import domain.vo.Col;
import domain.vo.PieceType;
import domain.vo.Row;
import domain.vo.Team;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PositionLayout {
    private static final Map<Position, PieceType> hanPiecesLayout = Map.ofEntries(
            Map.entry(new Position(Col.A, Row.ZERO), PieceType.CHARIOT),
            Map.entry(new Position(Col.D, Row.ZERO), PieceType.GUARD),
            Map.entry(new Position(Col.E, Row.ONE), PieceType.GENERAL),
            Map.entry(new Position(Col.F, Row.ZERO), PieceType.GUARD),
            Map.entry(new Position(Col.I, Row.ZERO), PieceType.CHARIOT),
            Map.entry(new Position(Col.B, Row.TWO), PieceType.CANNON),
            Map.entry(new Position(Col.H, Row.TWO), PieceType.CANNON),
            Map.entry(new Position(Col.A, Row.THREE), PieceType.SOLDIER),
            Map.entry(new Position(Col.C, Row.THREE), PieceType.SOLDIER),
            Map.entry(new Position(Col.E, Row.THREE), PieceType.SOLDIER),
            Map.entry(new Position(Col.G, Row.THREE), PieceType.SOLDIER),
            Map.entry(new Position(Col.I, Row.THREE), PieceType.SOLDIER)
    );

    private static final List<Col> INNER_COLS = List.of(Col.B, Col.C, Col.G, Col.H);

    public static Map<Position, Piece> build(Arrangements arrangements) {
        Map<Position, Piece> result = new HashMap<>();
        placeHanPieces(result);
        placeHanInnerPieces(result, arrangements);
        placeChoPieces(result);
        placeCHoInnerPieces(result, arrangements);
        return result;
    }

    private static void placeHanPieces(Map<Position, Piece> result) {
        hanPiecesLayout.forEach((position, pieceType) ->
                result.put(position, new Piece(Team.HAN, pieceType)));
    }

    private static void placeHanInnerPieces(Map<Position, Piece> result, Arrangements arrangements) {
        List<PieceType> innerPieces = arrangements.arrangeFor(Team.HAN).innerPieces();
        for (int index = 0; index < INNER_COLS.size(); index++) {
            result.put(new Position(INNER_COLS.get(index), Row.ZERO), new Piece(Team.HAN, innerPieces.get(index)));
        }
    }

    private static void placeChoPieces(Map<Position, Piece> result) {
        hanPiecesLayout.forEach((position, pieceType) -> {
            Position reversedPosition = new Position(position.col(), position.row().reverse());
            result.put(reversedPosition, new Piece(Team.CHO, pieceType));
        });
    }

    private static void placeCHoInnerPieces(Map<Position, Piece> result, Arrangements arrangements) {
        List<PieceType> innerPieces = arrangements.arrangeFor(Team.CHO).innerPieces();
        for (int index = 0; index < INNER_COLS.size(); index++) {
            result.put(new Position(INNER_COLS.get(index), Row.ZERO.reverse()), new Piece(Team.CHO, innerPieces.get(index)));
        }
    }

}
