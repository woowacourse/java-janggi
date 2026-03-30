package janggi.domain.board;

import static janggi.domain.dynasty.Dynasty.CHO;
import static janggi.domain.dynasty.Dynasty.HAN;
import static janggi.domain.piece.PieceType.CANNON;
import static janggi.domain.piece.PieceType.CHARIOT;
import static janggi.domain.piece.PieceType.ELEPHANT;
import static janggi.domain.piece.PieceType.GENERAL;
import static janggi.domain.piece.PieceType.GUARD;
import static janggi.domain.piece.PieceType.HORSE;
import static janggi.domain.piece.PieceType.SOLDIER;

import janggi.domain.dynasty.Dynasty;
import janggi.domain.piece.Piece;
import janggi.domain.position.Position;
import java.util.HashMap;
import java.util.Map;

public class DefaultBoardDesignPolicy implements BoardDesignPolicy {

    private final Map<Dynasty, HorseElephantPosition> horseElephantPositionMap;

    public DefaultBoardDesignPolicy(Map<Dynasty, HorseElephantPosition> horseElephantPositionMap) {
        this.horseElephantPositionMap = horseElephantPositionMap;
    }

    public Map<Position, Piece> initBoard() {
        Map<Position, Piece> policy = new HashMap<>();
        settingCho(policy, horseElephantPositionMap.get(CHO));
        settingHan(policy, horseElephantPositionMap.get(HAN));
        return policy;
    }

    private static void settingCho(Map<Position, Piece> board, HorseElephantPosition horseElephantPos) {
        board.put(Position.from(1, 1), new Piece(CHO, CHARIOT));
        board.put(Position.from(1, horseElephantPos.leftHorseColumn()), new Piece(CHO, HORSE));
        board.put(Position.from(1, horseElephantPos.leftElephantColumn()), new Piece(CHO, ELEPHANT));
        board.put(Position.from(1, 4), new Piece(CHO, GUARD));

        board.put(Position.from(1, 6), new Piece(CHO, GUARD));
        board.put(Position.from(1, horseElephantPos.rightHorseColumn()), new Piece(CHO, HORSE));
        board.put(Position.from(1, horseElephantPos.rightElephantColumn()), new Piece(CHO, ELEPHANT));
        board.put(Position.from(1, 9), new Piece(CHO, CHARIOT));

        board.put(Position.from(2, 5), new Piece(CHO, GENERAL));

        board.put(Position.from(3, 2), new Piece(CHO, CANNON));
        board.put(Position.from(3, 8), new Piece(CHO, CANNON));

        board.put(Position.from(4, 1), new Piece(CHO, SOLDIER));
        board.put(Position.from(4, 3), new Piece(CHO, SOLDIER));
        board.put(Position.from(4, 5), new Piece(CHO, SOLDIER));
        board.put(Position.from(4, 7), new Piece(CHO, SOLDIER));
        board.put(Position.from(4, 9), new Piece(CHO, SOLDIER));
    }

    private static void settingHan(Map<Position, Piece> board, HorseElephantPosition horseElephantPos) {
        board.put(Position.from(10, 1), new Piece(HAN, CHARIOT));
        board.put(Position.from(10, horseElephantPos.leftHorseColumn()), new Piece(HAN, HORSE));
        board.put(Position.from(10, horseElephantPos.leftElephantColumn()), new Piece(HAN, ELEPHANT));
        board.put(Position.from(10, 4), new Piece(HAN, GUARD));

        board.put(Position.from(10, 6), new Piece(HAN, GUARD));
        board.put(Position.from(10, horseElephantPos.rightHorseColumn()), new Piece(HAN, HORSE));
        board.put(Position.from(10, horseElephantPos.rightElephantColumn()), new Piece(HAN, ELEPHANT));
        board.put(Position.from(10, 9), new Piece(HAN, CHARIOT));

        board.put(Position.from(9, 5), new Piece(HAN, GENERAL));

        board.put(Position.from(8, 2), new Piece(HAN, CANNON));
        board.put(Position.from(8, 8), new Piece(HAN, CANNON));

        board.put(Position.from(7, 1), new Piece(HAN, SOLDIER));
        board.put(Position.from(7, 3), new Piece(HAN, SOLDIER));
        board.put(Position.from(7, 5), new Piece(HAN, SOLDIER));
        board.put(Position.from(7, 7), new Piece(HAN, SOLDIER));
        board.put(Position.from(7, 9), new Piece(HAN, SOLDIER));
    }

}
