package domain;

import domain.piece.Empty;
import domain.piece.Piece;
import domain.piece.Side;
import domain.piece.궁;
import domain.piece.마;
import domain.piece.사;
import domain.piece.상;
import domain.piece.졸병;
import domain.piece.차;
import domain.piece.포;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JanggiBoardFactory {

    private static final List<Integer> rows = List.of(0, 9, 8, 7, 6, 5, 4, 3, 2, 1);
    private static final List<Integer> columns = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9);

    public static Map<JanggiPosition, Piece> createJanggiBoard() {
        Map<JanggiPosition, Piece> janggiBoard = new HashMap<>();
        for (Integer column : columns) {
            for (Integer row : rows) {
                janggiBoard.put(new JanggiPosition(row, column), new Empty());
            }
        }
        initChoJanggiBoard(janggiBoard);
        initHanJanggiBoard(janggiBoard);
        return janggiBoard;
    }

    private static void initChoJanggiBoard(Map<JanggiPosition, Piece> janggiBoard) {
        janggiBoard.put(new JanggiPosition(9, 5), new 궁(Side.CHO));
        janggiBoard.put(new JanggiPosition(0, 1), new 차(Side.CHO));
        janggiBoard.put(new JanggiPosition(0, 9), new 차(Side.CHO));
        janggiBoard.put(new JanggiPosition(8, 2), new 포(Side.CHO));
        janggiBoard.put(new JanggiPosition(8, 8), new 포(Side.CHO));
        janggiBoard.put(new JanggiPosition(7, 1), new 졸병(Side.CHO));
        janggiBoard.put(new JanggiPosition(7, 3), new 졸병(Side.CHO));
        janggiBoard.put(new JanggiPosition(7, 5), new 졸병(Side.CHO));
        janggiBoard.put(new JanggiPosition(7, 7), new 졸병(Side.CHO));
        janggiBoard.put(new JanggiPosition(7, 9), new 졸병(Side.CHO));
        janggiBoard.put(new JanggiPosition(0, 4), new 사(Side.CHO));
        janggiBoard.put(new JanggiPosition(0, 6), new 사(Side.CHO));
        janggiBoard.put(new JanggiPosition(0, 2), new 마(Side.CHO));
        janggiBoard.put(new JanggiPosition(0, 8), new 마(Side.CHO));
        janggiBoard.put(new JanggiPosition(0, 3), new 상(Side.CHO));
        janggiBoard.put(new JanggiPosition(0, 7), new 상(Side.CHO));
    }

    private static void initHanJanggiBoard(Map<JanggiPosition, Piece> janggiBoard) {
        janggiBoard.put(new JanggiPosition(2, 5), new 궁(Side.HAN));
        janggiBoard.put(new JanggiPosition(1, 1), new 차(Side.HAN));
        janggiBoard.put(new JanggiPosition(1, 9), new 차(Side.HAN));
        janggiBoard.put(new JanggiPosition(3, 2), new 포(Side.HAN));
        janggiBoard.put(new JanggiPosition(3, 8), new 포(Side.HAN));
        janggiBoard.put(new JanggiPosition(4, 1), new 졸병(Side.HAN));
        janggiBoard.put(new JanggiPosition(4, 3), new 졸병(Side.HAN));
        janggiBoard.put(new JanggiPosition(4, 5), new 졸병(Side.HAN));
        janggiBoard.put(new JanggiPosition(4, 7), new 졸병(Side.HAN));
        janggiBoard.put(new JanggiPosition(4, 9), new 졸병(Side.HAN));
        janggiBoard.put(new JanggiPosition(1, 4), new 사(Side.HAN));
        janggiBoard.put(new JanggiPosition(1, 6), new 사(Side.HAN));
        janggiBoard.put(new JanggiPosition(1, 2), new 마(Side.HAN));
        janggiBoard.put(new JanggiPosition(1, 8), new 마(Side.HAN));
        janggiBoard.put(new JanggiPosition(1, 3), new 상(Side.HAN));
        janggiBoard.put(new JanggiPosition(1, 7), new 상(Side.HAN));
    }
}
