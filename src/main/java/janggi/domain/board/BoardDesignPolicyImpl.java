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

public class BoardDesignPolicyImpl implements BoardDesignPolicy{

    public Map<Position, Piece> initBoard() {
        Map<Position, Piece> policy = new HashMap<>();
        settingCho(policy);
        settingHan(policy);
        return policy;
    }

    private static void settingCho(Map<Position, Piece> board) {
        board.put(Position.from(1, 1), new Piece(Dynasty.CHO, new ChariotMoveStrategy()));
        board.put(Position.from(1, 2), new Piece(Dynasty.CHO, new HorseMoveStrategy()));
        board.put(Position.from(1, 3), new Piece(Dynasty.CHO, new ElephantMoveStrategy()));
        board.put(Position.from(1, 4), new Piece(Dynasty.CHO, new GuardMoveStrategy()));

        board.put(Position.from(1, 6), new Piece(Dynasty.CHO, new GuardMoveStrategy()));
        board.put(Position.from(1, 7), new Piece(Dynasty.CHO, new HorseMoveStrategy()));
        board.put(Position.from(1, 8), new Piece(Dynasty.CHO, new ElephantMoveStrategy()));
        board.put(Position.from(1, 9), new Piece(Dynasty.CHO, new ChariotMoveStrategy()));

        board.put(Position.from(2, 5), new Piece(Dynasty.CHO, new GeneralMoveStrategy()));

        board.put(Position.from(3, 2), new Piece(Dynasty.CHO, new CannonMoveStrategy()));
        board.put(Position.from(3, 8), new Piece(Dynasty.CHO, new CannonMoveStrategy()));

        board.put(Position.from(4, 1), new Piece(Dynasty.CHO, new SoldierMoveStrategy()));
        board.put(Position.from(4, 3), new Piece(Dynasty.CHO, new SoldierMoveStrategy()));
        board.put(Position.from(4, 5), new Piece(Dynasty.CHO, new SoldierMoveStrategy()));
        board.put(Position.from(4, 7), new Piece(Dynasty.CHO, new SoldierMoveStrategy()));
        board.put(Position.from(4, 9), new Piece(Dynasty.CHO, new SoldierMoveStrategy()));
    }

    private static void settingHan(Map<Position, Piece> board) {
        board.put(Position.from(10, 1), new Piece(Dynasty.HAN, new ChariotMoveStrategy()));
        board.put(Position.from(10, 2), new Piece(Dynasty.HAN, new HorseMoveStrategy()));
        board.put(Position.from(10, 3), new Piece(Dynasty.HAN, new ElephantMoveStrategy()));
        board.put(Position.from(10, 4), new Piece(Dynasty.HAN, new GuardMoveStrategy()));

        board.put(Position.from(10, 6), new Piece(Dynasty.HAN, new GuardMoveStrategy()));
        board.put(Position.from(10, 7), new Piece(Dynasty.HAN, new HorseMoveStrategy()));
        board.put(Position.from(10, 8), new Piece(Dynasty.HAN, new ElephantMoveStrategy()));
        board.put(Position.from(10, 9), new Piece(Dynasty.HAN, new ChariotMoveStrategy()));

        board.put(Position.from(9, 5), new Piece(Dynasty.HAN, new GeneralMoveStrategy()));

        board.put(Position.from(8, 2), new Piece(Dynasty.HAN, new CannonMoveStrategy()));
        board.put(Position.from(8, 8), new Piece(Dynasty.HAN, new CannonMoveStrategy()));

        board.put(Position.from(7, 1), new Piece(Dynasty.HAN, new SoldierMoveStrategy()));
        board.put(Position.from(7, 3), new Piece(Dynasty.HAN, new SoldierMoveStrategy()));
        board.put(Position.from(7, 5), new Piece(Dynasty.HAN, new SoldierMoveStrategy()));
        board.put(Position.from(7, 7), new Piece(Dynasty.HAN, new SoldierMoveStrategy()));
        board.put(Position.from(7, 9), new Piece(Dynasty.HAN, new SoldierMoveStrategy()));
    }

}
