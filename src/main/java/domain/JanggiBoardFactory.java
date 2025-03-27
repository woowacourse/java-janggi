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
        for (File file : File.values()) {
            for (Rank rank : Rank.values()) {
                janggiBoard.put(new JanggiPosition(rank.getRank(), file.getFile()), new Empty());
            }
        }
        initChoJanggiBoard(janggiBoard);
        initHanJanggiBoard(janggiBoard);
        return janggiBoard;
    }

    private static void initChoJanggiBoard(Map<JanggiPosition, Piece> janggiBoard) {
        janggiBoard.put(new JanggiPosition(Rank.FIVE.getRank(), File.NINE.getFile()), new General(Side.CHO));
        janggiBoard.put(new JanggiPosition(Rank.ONE.getRank(), File.ZERO.getFile()), new Chariot(Side.CHO));
        janggiBoard.put(new JanggiPosition(Rank.NINE.getRank(), File.ZERO.getFile()), new Chariot(Side.CHO));
        janggiBoard.put(new JanggiPosition(Rank.TWO.getRank(), File.EIGHT.getFile()), new Cannon(Side.CHO));
        janggiBoard.put(new JanggiPosition(Rank.EIGHT.getRank(), File.EIGHT.getFile()), new Cannon(Side.CHO));
        janggiBoard.put(new JanggiPosition(Rank.ONE.getRank(), File.SEVEN.getFile()), new Soldier(Side.CHO));
        janggiBoard.put(new JanggiPosition(Rank.THREE.getRank(), File.SEVEN.getFile()), new Soldier(Side.CHO));
        janggiBoard.put(new JanggiPosition(Rank.FIVE.getRank(), File.SEVEN.getFile()), new Soldier(Side.CHO));
        janggiBoard.put(new JanggiPosition(Rank.SEVEN.getRank(), File.SEVEN.getFile()), new Soldier(Side.CHO));
        janggiBoard.put(new JanggiPosition(Rank.NINE.getRank(), File.SEVEN.getFile()), new Soldier(Side.CHO));
        janggiBoard.put(new JanggiPosition(Rank.FOUR.getRank(), File.ZERO.getFile()), new Guard(Side.CHO));
        janggiBoard.put(new JanggiPosition(Rank.SIX.getRank(), File.ZERO.getFile()), new Guard(Side.CHO));
        janggiBoard.put(new JanggiPosition(Rank.TWO.getRank(), File.ZERO.getFile()), new Horse(Side.CHO));
        janggiBoard.put(new JanggiPosition(Rank.EIGHT.getRank(), File.ZERO.getFile()), new Horse(Side.CHO));
        janggiBoard.put(new JanggiPosition(Rank.THREE.getRank(), File.ZERO.getFile()), new Elephant(Side.CHO));
        janggiBoard.put(new JanggiPosition(Rank.SEVEN.getRank(), File.ZERO.getFile()), new Elephant(Side.CHO));
    }

    private static void initHanJanggiBoard(Map<JanggiPosition, Piece> janggiBoard) {
        janggiBoard.put(new JanggiPosition(Rank.FIVE.getRank(), File.TWO.getFile()), new General(Side.HAN));
        janggiBoard.put(new JanggiPosition(Rank.ONE.getRank(), File.ONE.getFile()), new Chariot(Side.HAN));
        janggiBoard.put(new JanggiPosition(Rank.NINE.getRank(), File.ONE.getFile()), new Chariot(Side.HAN));
        janggiBoard.put(new JanggiPosition(Rank.TWO.getRank(), File.THREE.getFile()), new Cannon(Side.HAN));
        janggiBoard.put(new JanggiPosition(Rank.EIGHT.getRank(), File.THREE.getFile()), new Cannon(Side.HAN));
        janggiBoard.put(new JanggiPosition(Rank.ONE.getRank(), File.FOUR.getFile()), new Soldier(Side.HAN));
        janggiBoard.put(new JanggiPosition(Rank.THREE.getRank(), File.FOUR.getFile()), new Soldier(Side.HAN));
        janggiBoard.put(new JanggiPosition(Rank.FIVE.getRank(), File.FOUR.getFile()), new Soldier(Side.HAN));
        janggiBoard.put(new JanggiPosition(Rank.SEVEN.getRank(), File.FOUR.getFile()), new Soldier(Side.HAN));
        janggiBoard.put(new JanggiPosition(Rank.NINE.getRank(), File.FOUR.getFile()), new Soldier(Side.HAN));
        janggiBoard.put(new JanggiPosition(Rank.FOUR.getRank(), File.ONE.getFile()), new Guard(Side.HAN));
        janggiBoard.put(new JanggiPosition(Rank.SIX.getRank(), File.ONE.getFile()), new Guard(Side.HAN));
        janggiBoard.put(new JanggiPosition(Rank.TWO.getRank(), File.ONE.getFile()), new Horse(Side.HAN));
        janggiBoard.put(new JanggiPosition(Rank.EIGHT.getRank(), File.ONE.getFile()), new Horse(Side.HAN));
        janggiBoard.put(new JanggiPosition(Rank.THREE.getRank(), File.ONE.getFile()), new Elephant(Side.HAN));
        janggiBoard.put(new JanggiPosition(Rank.SEVEN.getRank(), File.ONE.getFile()), new Elephant(Side.HAN));
    }
}
