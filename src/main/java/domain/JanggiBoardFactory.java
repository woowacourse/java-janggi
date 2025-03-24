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
import java.util.Map;

public class JanggiBoardFactory {

    public static Map<JanggiPosition, Piece> createJanggiBoard() {
        Map<JanggiPosition, Piece> janggiBoard = new HashMap<>();
        for (Column column : Column.values()) {
            for (Row row : Row.values()) {
                janggiBoard.put(new JanggiPosition(row.getRow(), column.getColumn()), new Empty());
            }
        }
        initChoJanggiBoard(janggiBoard);
        initHanJanggiBoard(janggiBoard);
        return janggiBoard;
    }

    private static void initChoJanggiBoard(Map<JanggiPosition, Piece> janggiBoard) {
        janggiBoard.put(new JanggiPosition(Row.NINE.getRow(), Column.FIVE.getColumn()), new 궁(Side.CHO));
        janggiBoard.put(new JanggiPosition(Row.ZERO.getRow(), Column.ONE.getColumn()), new 차(Side.CHO));
        janggiBoard.put(new JanggiPosition(Row.ZERO.getRow(), Column.NINE.getColumn()), new 차(Side.CHO));
        janggiBoard.put(new JanggiPosition(Row.EIGHT.getRow(), Column.TWO.getColumn()), new 포(Side.CHO));
        janggiBoard.put(new JanggiPosition(Row.EIGHT.getRow(), Column.EIGHT.getColumn()), new 포(Side.CHO));
        janggiBoard.put(new JanggiPosition(Row.SEVEN.getRow(), Column.ONE.getColumn()), new 졸병(Side.CHO));
        janggiBoard.put(new JanggiPosition(Row.SEVEN.getRow(), Column.THREE.getColumn()), new 졸병(Side.CHO));
        janggiBoard.put(new JanggiPosition(Row.SEVEN.getRow(), Column.FIVE.getColumn()), new 졸병(Side.CHO));
        janggiBoard.put(new JanggiPosition(Row.SEVEN.getRow(), Column.SEVEN.getColumn()), new 졸병(Side.CHO));
        janggiBoard.put(new JanggiPosition(Row.SEVEN.getRow(), Column.NINE.getColumn()), new 졸병(Side.CHO));
        janggiBoard.put(new JanggiPosition(Row.ZERO.getRow(), Column.FOUR.getColumn()), new 사(Side.CHO));
        janggiBoard.put(new JanggiPosition(Row.ZERO.getRow(), Column.SIX.getColumn()), new 사(Side.CHO));
        janggiBoard.put(new JanggiPosition(Row.ZERO.getRow(), Column.TWO.getColumn()), new 마(Side.CHO));
        janggiBoard.put(new JanggiPosition(Row.ZERO.getRow(), Column.EIGHT.getColumn()), new 마(Side.CHO));
        janggiBoard.put(new JanggiPosition(Row.ZERO.getRow(), Column.THREE.getColumn()), new 상(Side.CHO));
        janggiBoard.put(new JanggiPosition(Row.ZERO.getRow(), Column.SEVEN.getColumn()), new 상(Side.CHO));
    }

    private static void initHanJanggiBoard(Map<JanggiPosition, Piece> janggiBoard) {
        janggiBoard.put(new JanggiPosition(Row.TWO.getRow(), Column.FIVE.getColumn()), new 궁(Side.HAN));
        janggiBoard.put(new JanggiPosition(Row.ONE.getRow(), Column.ONE.getColumn()), new 차(Side.HAN));
        janggiBoard.put(new JanggiPosition(Row.ONE.getRow(), Column.NINE.getColumn()), new 차(Side.HAN));
        janggiBoard.put(new JanggiPosition(Row.THREE.getRow(), Column.TWO.getColumn()), new 포(Side.HAN));
        janggiBoard.put(new JanggiPosition(Row.THREE.getRow(), Column.EIGHT.getColumn()), new 포(Side.HAN));
        janggiBoard.put(new JanggiPosition(Row.FOUR.getRow(), Column.ONE.getColumn()), new 졸병(Side.HAN));
        janggiBoard.put(new JanggiPosition(Row.FOUR.getRow(), Column.THREE.getColumn()), new 졸병(Side.HAN));
        janggiBoard.put(new JanggiPosition(Row.FOUR.getRow(), Column.FIVE.getColumn()), new 졸병(Side.HAN));
        janggiBoard.put(new JanggiPosition(Row.FOUR.getRow(), Column.SEVEN.getColumn()), new 졸병(Side.HAN));
        janggiBoard.put(new JanggiPosition(Row.FOUR.getRow(), Column.NINE.getColumn()), new 졸병(Side.HAN));
        janggiBoard.put(new JanggiPosition(Row.ONE.getRow(), Column.FOUR.getColumn()), new 사(Side.HAN));
        janggiBoard.put(new JanggiPosition(Row.ONE.getRow(), Column.SIX.getColumn()), new 사(Side.HAN));
        janggiBoard.put(new JanggiPosition(Row.ONE.getRow(), Column.TWO.getColumn()), new 마(Side.HAN));
        janggiBoard.put(new JanggiPosition(Row.ONE.getRow(), Column.EIGHT.getColumn()), new 마(Side.HAN));
        janggiBoard.put(new JanggiPosition(Row.ONE.getRow(), Column.THREE.getColumn()), new 상(Side.HAN));
        janggiBoard.put(new JanggiPosition(Row.ONE.getRow(), Column.SEVEN.getColumn()), new 상(Side.HAN));
    }
}
