package domain;

import domain.piece.Cannon;
import domain.piece.Chariot;
import domain.piece.Elephant;
import domain.piece.Empty;
import domain.piece.General;
import domain.piece.Guard;
import domain.piece.Horse;
import domain.piece.Piece;
import domain.piece.Side;
import domain.piece.Soldier;
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
        janggiBoard.put(new JanggiPosition(Row.NINE.getRow(), Column.FIVE.getColumn()), new General(Side.CHO));
        janggiBoard.put(new JanggiPosition(Row.ZERO.getRow(), Column.ONE.getColumn()), new Chariot(Side.CHO));
        janggiBoard.put(new JanggiPosition(Row.ZERO.getRow(), Column.NINE.getColumn()), new Chariot(Side.CHO));
        janggiBoard.put(new JanggiPosition(Row.EIGHT.getRow(), Column.TWO.getColumn()), new Cannon(Side.CHO));
        janggiBoard.put(new JanggiPosition(Row.EIGHT.getRow(), Column.EIGHT.getColumn()), new Cannon(Side.CHO));
        janggiBoard.put(new JanggiPosition(Row.SEVEN.getRow(), Column.ONE.getColumn()), new Soldier(Side.CHO));
        janggiBoard.put(new JanggiPosition(Row.SEVEN.getRow(), Column.THREE.getColumn()), new Soldier(Side.CHO));
        janggiBoard.put(new JanggiPosition(Row.SEVEN.getRow(), Column.FIVE.getColumn()), new Soldier(Side.CHO));
        janggiBoard.put(new JanggiPosition(Row.SEVEN.getRow(), Column.SEVEN.getColumn()), new Soldier(Side.CHO));
        janggiBoard.put(new JanggiPosition(Row.SEVEN.getRow(), Column.NINE.getColumn()), new Soldier(Side.CHO));
        janggiBoard.put(new JanggiPosition(Row.ZERO.getRow(), Column.FOUR.getColumn()), new Guard(Side.CHO));
        janggiBoard.put(new JanggiPosition(Row.ZERO.getRow(), Column.SIX.getColumn()), new Guard(Side.CHO));
        janggiBoard.put(new JanggiPosition(Row.ZERO.getRow(), Column.TWO.getColumn()), new Horse(Side.CHO));
        janggiBoard.put(new JanggiPosition(Row.ZERO.getRow(), Column.EIGHT.getColumn()), new Horse(Side.CHO));
        janggiBoard.put(new JanggiPosition(Row.ZERO.getRow(), Column.THREE.getColumn()), new Elephant(Side.CHO));
        janggiBoard.put(new JanggiPosition(Row.ZERO.getRow(), Column.SEVEN.getColumn()), new Elephant(Side.CHO));
    }

    private static void initHanJanggiBoard(Map<JanggiPosition, Piece> janggiBoard) {
        janggiBoard.put(new JanggiPosition(Row.TWO.getRow(), Column.FIVE.getColumn()), new General(Side.HAN));
        janggiBoard.put(new JanggiPosition(Row.ONE.getRow(), Column.ONE.getColumn()), new Chariot(Side.HAN));
        janggiBoard.put(new JanggiPosition(Row.ONE.getRow(), Column.NINE.getColumn()), new Chariot(Side.HAN));
        janggiBoard.put(new JanggiPosition(Row.THREE.getRow(), Column.TWO.getColumn()), new Cannon(Side.HAN));
        janggiBoard.put(new JanggiPosition(Row.THREE.getRow(), Column.EIGHT.getColumn()), new Cannon(Side.HAN));
        janggiBoard.put(new JanggiPosition(Row.FOUR.getRow(), Column.ONE.getColumn()), new Soldier(Side.HAN));
        janggiBoard.put(new JanggiPosition(Row.FOUR.getRow(), Column.THREE.getColumn()), new Soldier(Side.HAN));
        janggiBoard.put(new JanggiPosition(Row.FOUR.getRow(), Column.FIVE.getColumn()), new Soldier(Side.HAN));
        janggiBoard.put(new JanggiPosition(Row.FOUR.getRow(), Column.SEVEN.getColumn()), new Soldier(Side.HAN));
        janggiBoard.put(new JanggiPosition(Row.FOUR.getRow(), Column.NINE.getColumn()), new Soldier(Side.HAN));
        janggiBoard.put(new JanggiPosition(Row.ONE.getRow(), Column.FOUR.getColumn()), new Guard(Side.HAN));
        janggiBoard.put(new JanggiPosition(Row.ONE.getRow(), Column.SIX.getColumn()), new Guard(Side.HAN));
        janggiBoard.put(new JanggiPosition(Row.ONE.getRow(), Column.TWO.getColumn()), new Horse(Side.HAN));
        janggiBoard.put(new JanggiPosition(Row.ONE.getRow(), Column.EIGHT.getColumn()), new Horse(Side.HAN));
        janggiBoard.put(new JanggiPosition(Row.ONE.getRow(), Column.THREE.getColumn()), new Elephant(Side.HAN));
        janggiBoard.put(new JanggiPosition(Row.ONE.getRow(), Column.SEVEN.getColumn()), new Elephant(Side.HAN));
    }
}
