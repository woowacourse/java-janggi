package janggi.factory;

import janggi.domain.Arrangement;
import janggi.domain.PalaceTopology;
import janggi.domain.Position;
import janggi.domain.Side;
import janggi.domain.board.Board;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BoardFactory {
    private static final double INITIAL_PIECE_SCORE = 72.0;
    private static final double HAN_FIRST_MOVE_ADVANTAGE = 1.5;
    private static final double CHO_INITIAL_SCORE = INITIAL_PIECE_SCORE;
    private static final double HAN_INITIAL_SCORE = INITIAL_PIECE_SCORE + HAN_FIRST_MOVE_ADVANTAGE;

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

    private BoardFactory() {
        throw new AssertionError(UTILITY_CLASS_INSTANTIATION_MESSAGE);
    }

    public static Map<Position, Piece> createInitialBoard(Arrangement choArrangement, Arrangement hanArrangement) {
        PalaceTopology palaceTopology = PalaceTopology.from();
        PieceFactory pieceFactory = new PieceFactory(palaceTopology);
        Map<Position, Piece> board = createEmptyBoard(pieceFactory);

        initHanPosition.forEach((key, value) -> value.forEach(position -> board.put(position, pieceFactory.create(key, Side.HAN))));
        initChoPosition.forEach((key, value) -> value.forEach(position -> board.put(position, pieceFactory.create(key, Side.CHO))));

        List<PieceType> choMaSangPieceOrder = arrangeMap.get(choArrangement);
        List<PieceType> hanMaSangPieceOrder = arrangeMap.get(hanArrangement);

        for (int i = 0; i < 4; i++) {
            board.put(choMaSangPosition.get(i), pieceFactory.create(choMaSangPieceOrder.get(i), Side.CHO));
            board.put(hanMaSangPosition.get(i), pieceFactory.create(hanMaSangPieceOrder.get(i), Side.HAN));
        }
        return board;
    }

    public static Map<Side, Double> createInitialScoresBySide() {
        Map<Side, Double> scoresBySide = new HashMap<>();
        scoresBySide.put(Side.HAN, HAN_INITIAL_SCORE);
        scoresBySide.put(Side.CHO, CHO_INITIAL_SCORE);
        return scoresBySide;
    }

    public static Map<Position, Piece> createEmptyBoard(PieceFactory pieceFactory) {
        Map<Position, Piece> pieces = new HashMap<>();
        for (int i = Board.BOARD_START_ROWS; i <= Board.BOARD_END_ROWS; i++) {
            for (int j = Board.BOARD_START_COLS; j <= Board.BOARD_END_COLS; j++) {
                pieces.put(new Position(i, j), pieceFactory.create(PieceType.NONE, Side.EMPTY));
            }
        }
        return pieces;
    }
}