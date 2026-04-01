package janggi.initializer;

import janggi.domain.Arrangement;
import janggi.domain.Position;
import janggi.domain.Side;
import janggi.domain.board.Board;
import janggi.domain.piece.Cha;
import janggi.domain.piece.Gung;
import janggi.domain.piece.Ma;
import janggi.domain.piece.None;
import janggi.domain.piece.Pawn;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import janggi.domain.piece.Po;
import janggi.domain.piece.Sa;
import janggi.domain.piece.Sang;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class BoardInitializer {
    private static final String UTILITY_CLASS_INSTANTIATION_MESSAGE = "BoardInitializer는 유틸리티 클래스이므로 인스턴스화할 수 없습니다.";

    private static final Map<Arrangement, List<PieceType>> arrangeMap = Map.of(
            Arrangement.MA_SANG_MA_SANG, List.of(PieceType.MA, PieceType.SANG, PieceType.MA, PieceType.SANG),
            Arrangement.MA_SANG_SANG_MA, List.of(PieceType.MA, PieceType.SANG, PieceType.SANG, PieceType.MA),
            Arrangement.SANG_MA_MA_SANG, List.of(PieceType.SANG, PieceType.MA, PieceType.MA, PieceType.SANG),
            Arrangement.SANG_MA_SANG_MA, List.of(PieceType.SANG, PieceType.MA, PieceType.SANG, PieceType.MA)
    );

    private static final List<Position> choMaSangPosition = List.of(
            new Position(10, 2), new Position(10, 3), new Position(10, 7), new Position(10, 8)
    );

    private static final List<Position> hanMaSangPosition = List.of(
            new Position(1, 2), new Position(1, 3), new Position(1, 7), new Position(1, 8)
    );

    private static final Map<PieceType, Function<Side, Piece>> pieceMap = Map.of(
            PieceType.CHA, Cha::new,
            PieceType.GUNG, Gung::new,
            PieceType.MA, Ma::new,
            PieceType.NONE, (side) -> new None(),
            PieceType.PAWN, Pawn::from,
            PieceType.PO, Po::new,
            PieceType.SA, Sa::new,
            PieceType.SANG, Sang::new
    );

    private static final Map<PieceType, List<Position>> initHanPosition = Map.of(
            PieceType.CHA, List.of(new Position(1, 1), new Position(1, 9)),
            PieceType.SA, List.of(new Position(1, 4), new Position(1, 6)),
            PieceType.GUNG, List.of(new Position(2, 5)),
            PieceType.PO, List.of(new Position(3, 2), new Position(3, 8)),
            PieceType.PAWN, List.of(new Position(4, 1), new Position(4, 3), new Position(4, 5), new Position(4, 7), new Position(4, 9))
    );

    private static final Map<PieceType, List<Position>> initChoPosition = Map.of(
            PieceType.CHA, List.of(new Position(10, 1), new Position(10, 9)),
            PieceType.SA, List.of(new Position(10, 4), new Position(10, 6)),
            PieceType.GUNG, List.of(new Position(9, 5)),
            PieceType.PO, List.of(new Position(8, 2), new Position(8, 8)),
            PieceType.PAWN, List.of(new Position(7, 1), new Position(7, 3), new Position(7, 5), new Position(7, 7), new Position(7, 9))
    );

    private BoardInitializer() {
        throw new AssertionError(UTILITY_CLASS_INSTANTIATION_MESSAGE);
    }

    public static Map<Position, Piece> createBoard(Arrangement choArrangement, Arrangement hanArrangement) {
        Map<Position, Piece> board = initBoard();

        initHanPosition.forEach((key, value) -> value.forEach(position -> board.put(position, pieceMap.get(key).apply(Side.HAN))));
        initChoPosition.forEach((key, value) -> value.forEach(position -> board.put(position, pieceMap.get(key).apply(Side.CHO))));

        List<PieceType> choMaSangPieceOrder = arrangeMap.get(choArrangement);
        List<PieceType> hanMaSangPieceOrder = arrangeMap.get(hanArrangement);

        for (int i = 0; i < 4; i++) {
            board.put(choMaSangPosition.get(i), pieceMap.get(choMaSangPieceOrder.get(i)).apply(Side.CHO));
            board.put(hanMaSangPosition.get(i), pieceMap.get(hanMaSangPieceOrder.get(i)).apply(Side.HAN));
        }
        return board;
    }

    private static Map<Position, Piece> initBoard() {
        Map<Position, Piece> pieces = new HashMap<>();
        for (int i = Board.BOARD_START_ROWS; i <= Board.BOARD_END_ROWS; i++) {
            for (int j = Board.BOARD_START_COLS; j <= Board.BOARD_END_COLS; j++) {
                Position position = new Position(i, j);
                pieces.put(position, new None());
            }
        }
        return pieces;
    }
}
