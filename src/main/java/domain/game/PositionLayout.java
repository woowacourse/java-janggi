package domain.game;

import domain.vo.Arrangements;
import domain.vo.Col;
import domain.vo.Row;
import domain.vo.Team;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PositionLayout {
    private static final Map<Position, PieceType> hanPiecesLayout = Map.ofEntries(
            Map.entry(new Position(Col.A, Row.ZERO), PieceType.CHARIOT),
            Map.entry(new Position(Col.D, Row.ZERO), PieceType.GUARD),
            Map.entry(new Position(Col.E, Row.ZERO), PieceType.GENERAL),
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

        hanPiecesLayout.forEach((position, pieceType) -> {
            result.put(position, new Piece(Team.HAN, pieceType));
        });

        List<PieceType> innerPieces = arrangements.getHan().getInnerPieces();
        for (int i = 0; i < INNER_COLS.size(); i++) {
            Row row = Row.ZERO;
            Position pos = new Position(INNER_COLS.get(i), row);
            result.put(pos, new Piece(Team.HAN, innerPieces.get(i)));
        }

        hanPiecesLayout.forEach((position, pieceType) -> {
            Position actualPosition = new Position(position.col(), position.row().reverse());
            result.put(actualPosition, new Piece(Team.CHO, pieceType));
        });

        innerPieces = arrangements.getCho().getInnerPieces();
        for (int i = 0; i < INNER_COLS.size(); i++) {
            Row row = Row.ZERO.reverse();
            Position pos = new Position(INNER_COLS.get(i), row);
            result.put(pos, new Piece(Team.CHO, innerPieces.get(i)));
        }

        return result;
    }

}
