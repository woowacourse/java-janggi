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

public class BoardDesignPolicy {

    private final Map<Position, Piece> policy;

    // TODO: 마상 패턴 분리하기
    public BoardDesignPolicy() {
        policy = new HashMap<>();
        settingCho();
        settingHan();
    }

    private void settingCho() {
        policy.put(Position.from(1, 1), new Piece(Dynasty.CHO, new ChariotMoveStrategy()));
        policy.put(Position.from(1, 2), new Piece(Dynasty.CHO, new HorseMoveStrategy()));
        policy.put(Position.from(1, 3), new Piece(Dynasty.CHO, new ElephantMoveStrategy()));
        policy.put(Position.from(1, 4), new Piece(Dynasty.CHO, new GuardMoveStrategy()));

        policy.put(Position.from(1, 6), new Piece(Dynasty.CHO, new GuardMoveStrategy()));
        policy.put(Position.from(1, 7), new Piece(Dynasty.CHO, new HorseMoveStrategy()));
        policy.put(Position.from(1, 8), new Piece(Dynasty.CHO, new ElephantMoveStrategy()));
        policy.put(Position.from(1, 9), new Piece(Dynasty.CHO, new ChariotMoveStrategy()));

        policy.put(Position.from(2, 5), new Piece(Dynasty.CHO, new GeneralMoveStrategy()));

        policy.put(Position.from(3, 2), new Piece(Dynasty.CHO, new CannonMoveStrategy()));
        policy.put(Position.from(3, 8), new Piece(Dynasty.CHO, new CannonMoveStrategy()));

        policy.put(Position.from(4, 1), new Piece(Dynasty.CHO, new SoldierMoveStrategy()));
        policy.put(Position.from(4, 3), new Piece(Dynasty.CHO, new SoldierMoveStrategy()));
        policy.put(Position.from(4, 5), new Piece(Dynasty.CHO, new SoldierMoveStrategy()));
        policy.put(Position.from(4, 7), new Piece(Dynasty.CHO, new SoldierMoveStrategy()));
        policy.put(Position.from(4, 9), new Piece(Dynasty.CHO, new SoldierMoveStrategy()));
    }

    private void settingHan() {
        policy.put(Position.from(10, 1), new Piece(Dynasty.HAN, new ChariotMoveStrategy()));
        policy.put(Position.from(10, 2), new Piece(Dynasty.HAN, new HorseMoveStrategy()));
        policy.put(Position.from(10, 3), new Piece(Dynasty.HAN, new ElephantMoveStrategy()));
        policy.put(Position.from(10, 4), new Piece(Dynasty.HAN, new GuardMoveStrategy()));

        policy.put(Position.from(10, 6), new Piece(Dynasty.HAN, new GuardMoveStrategy()));
        policy.put(Position.from(10, 7), new Piece(Dynasty.HAN, new HorseMoveStrategy()));
        policy.put(Position.from(10, 8), new Piece(Dynasty.HAN, new ElephantMoveStrategy()));
        policy.put(Position.from(10, 9), new Piece(Dynasty.HAN, new ChariotMoveStrategy()));

        policy.put(Position.from(9, 5), new Piece(Dynasty.HAN, new GeneralMoveStrategy()));

        policy.put(Position.from(8, 2), new Piece(Dynasty.HAN, new CannonMoveStrategy()));
        policy.put(Position.from(8, 8), new Piece(Dynasty.HAN, new CannonMoveStrategy()));

        policy.put(Position.from(7, 1), new Piece(Dynasty.HAN, new SoldierMoveStrategy()));
        policy.put(Position.from(7, 3), new Piece(Dynasty.HAN, new SoldierMoveStrategy()));
        policy.put(Position.from(7, 5), new Piece(Dynasty.HAN, new SoldierMoveStrategy()));
        policy.put(Position.from(7, 7), new Piece(Dynasty.HAN, new SoldierMoveStrategy()));
        policy.put(Position.from(7, 9), new Piece(Dynasty.HAN, new SoldierMoveStrategy()));
    }

}
