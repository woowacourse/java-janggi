package domain.board;

import domain.Piece;
import domain.PieceType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BoardFactory {
    private static final List<Integer> PAWN_COLUMNS = List.of(0, 2, 4, 6, 8);
    private static final List<Integer> ROOK_COLUMNS = List.of(0, 8);
    private static final List<Integer> ADVISOR_COLUMNS = List.of(3, 5);
    private static final List<Integer> CANNON_COLUMNS = List.of(1, 7);

    private BoardFactory() {
    }

    public static Board createBoard(InitializeSetting initializeSetting) {
        Map<Position, Piece> pieces = new HashMap<>();

        List<PieceType> initialSetting = initializeSetting.getInitialSetting();
        for (Integer pawnColumn : PAWN_COLUMNS) {
            pieces.put(new Position(pawnColumn, 3), new Piece(PieceType.PAWN));
        }

        for (Integer rookColumn : ROOK_COLUMNS) {
            pieces.put(new Position(rookColumn, 0), new Piece(PieceType.ROOK));
        }

        for (Integer advisorColumn : ADVISOR_COLUMNS) {
            pieces.put(new Position(advisorColumn, 0), new Piece(PieceType.ADVISOR));
        }

        for (Integer cannonColumn : CANNON_COLUMNS) {
            pieces.put(new Position(cannonColumn, 2), new Piece(PieceType.CANNON));
        }

        pieces.put(new Position(4, 1), new Piece(PieceType.KING));

        pieces.put(new Position(1, 0), new Piece(initialSetting.get(0)));
        pieces.put(new Position(2, 0), new Piece(initialSetting.get(1)));
        pieces.put(new Position(6, 0), new Piece(initialSetting.get(2)));
        pieces.put(new Position(7, 0), new Piece(initialSetting.get(3)));

        return new Board(pieces);
    }
}
