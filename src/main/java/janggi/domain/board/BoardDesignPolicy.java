package janggi.domain.board;

import janggi.domain.dynasty.Dynasty;
import janggi.domain.piece.CannonMoveStrategy;
import janggi.domain.piece.ChariotMoveStrategy;
import janggi.domain.piece.ElephantMoveStrategy;
import janggi.domain.piece.GeneralMoveStrategy;
import janggi.domain.piece.GuardMoveStrategy;
import janggi.domain.piece.HorseMoveStrategy;
import janggi.domain.piece.Piece;
import janggi.domain.piece.SoldierMoveStrategy;
import janggi.domain.position.Position;
import java.util.HashMap;
import java.util.Map;

import static janggi.domain.dynasty.Dynasty.CHO;
import static janggi.domain.dynasty.Dynasty.HAN;

public class BoardDesignPolicy {

    private final Map<Dynasty, HorseElephantPosition> horseElephantPositionMap;

    public BoardDesignPolicy(Map<Dynasty, HorseElephantPosition> horseElephantPositionMap) {
        this.horseElephantPositionMap = horseElephantPositionMap;
    }

    public Map<Position, Piece> initBoard() {
        Map<Position, Piece> policy = new HashMap<>();
        settingCho(policy, horseElephantPositionMap.get(CHO));
        settingHan(policy, horseElephantPositionMap.get(HAN));
        return policy;
    }

    private static void settingCho(Map<Position, Piece> board, HorseElephantPosition horseElephantPos) {
        board.put(Position.from(1, 1), new Piece(CHO, new ChariotMoveStrategy()));
        board.put(Position.from(1, horseElephantPos.leftHorseColumn()), new Piece(CHO, new HorseMoveStrategy()));
        board.put(Position.from(1, horseElephantPos.leftElephantColumn()), new Piece(CHO, new ElephantMoveStrategy()));
        board.put(Position.from(1, 4), new Piece(CHO, new GuardMoveStrategy()));

        board.put(Position.from(1, 6), new Piece(CHO, new GuardMoveStrategy()));
        board.put(Position.from(1, horseElephantPos.rightHorseColumn()), new Piece(CHO, new HorseMoveStrategy()));
        board.put(Position.from(1, horseElephantPos.rightElephantColumn()), new Piece(CHO, new ElephantMoveStrategy()));
        board.put(Position.from(1, 9), new Piece(CHO, new ChariotMoveStrategy()));

        board.put(Position.from(2, 5), new Piece(CHO, new GeneralMoveStrategy()));

        board.put(Position.from(3, 2), new Piece(CHO, new CannonMoveStrategy()));
        board.put(Position.from(3, 8), new Piece(CHO, new CannonMoveStrategy()));

        board.put(Position.from(4, 1), new Piece(CHO, new SoldierMoveStrategy()));
        board.put(Position.from(4, 3), new Piece(CHO, new SoldierMoveStrategy()));
        board.put(Position.from(4, 5), new Piece(CHO, new SoldierMoveStrategy()));
        board.put(Position.from(4, 7), new Piece(CHO, new SoldierMoveStrategy()));
        board.put(Position.from(4, 9), new Piece(CHO, new SoldierMoveStrategy()));
    }

    private static void settingHan(Map<Position, Piece> board, HorseElephantPosition horseElephantPos) {
        board.put(Position.from(10, 1), new Piece(HAN, new ChariotMoveStrategy()));
        board.put(Position.from(10, horseElephantPos.leftHorseColumn()), new Piece(HAN, new HorseMoveStrategy()));
        board.put(Position.from(10, horseElephantPos.leftElephantColumn()), new Piece(HAN, new ElephantMoveStrategy()));
        board.put(Position.from(10, 4), new Piece(HAN, new GuardMoveStrategy()));

        board.put(Position.from(10, 6), new Piece(HAN, new GuardMoveStrategy()));
        board.put(Position.from(10, horseElephantPos.rightHorseColumn()), new Piece(HAN, new HorseMoveStrategy()));
        board.put(Position.from(10, horseElephantPos.rightElephantColumn()), new Piece(HAN, new ElephantMoveStrategy()));
        board.put(Position.from(10, 9), new Piece(HAN, new ChariotMoveStrategy()));

        board.put(Position.from(9, 5), new Piece(HAN, new GeneralMoveStrategy()));

        board.put(Position.from(8, 2), new Piece(HAN, new CannonMoveStrategy()));
        board.put(Position.from(8, 8), new Piece(HAN, new CannonMoveStrategy()));

        board.put(Position.from(7, 1), new Piece(HAN, new SoldierMoveStrategy()));
        board.put(Position.from(7, 3), new Piece(HAN, new SoldierMoveStrategy()));
        board.put(Position.from(7, 5), new Piece(HAN, new SoldierMoveStrategy()));
        board.put(Position.from(7, 7), new Piece(HAN, new SoldierMoveStrategy()));
        board.put(Position.from(7, 9), new Piece(HAN, new SoldierMoveStrategy()));
    }

}
