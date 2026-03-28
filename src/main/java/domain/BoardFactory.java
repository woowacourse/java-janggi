package domain;

import domain.strategy.*;
import domain.vo.Position;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

public class BoardFactory {

    private BoardFactory() {}

    public static Board of(final Map<Position, Piece> board) {
        return Board.of(board);
    }

    public static Board setUp() {
        Map<Position, Piece> board = new TreeMap<>(Comparator
                .comparingInt(Position::getRow).reversed()
                .thenComparingInt(Position::getCol));

        board.put(Position.of(0, 0),Piece.of(Team.CHU, Type.CHARIOT, new ChariotMoveStrategy()));
        board.put(Position.of(0, 1),Piece.of(Team.CHU, Type.ELEPHANT, new ElephantMoveStrategy()));
        board.put(Position.of(0, 2),Piece.of(Team.CHU, Type.HORSE, new HorseMoveStrategy()));
        board.put(Position.of(0, 3),Piece.of(Team.CHU, Type.GUARD, new GuardMoveStrategy()));
        board.put(Position.of(0, 5),Piece.of(Team.CHU, Type.GUARD, new GuardMoveStrategy()));
        board.put(Position.of(0, 6),Piece.of(Team.CHU, Type.ELEPHANT, new ElephantMoveStrategy()));
        board.put(Position.of(0, 7),Piece.of(Team.CHU, Type.HORSE, new HorseMoveStrategy()));
        board.put(Position.of(0, 8),Piece.of(Team.CHU, Type.CHARIOT, new ChariotMoveStrategy()));
        board.put(Position.of(1, 4),Piece.of(Team.CHU, Type.GENERAL, new GeneralMoveStrategy()));
        board.put(Position.of(2, 1),Piece.of(Team.CHU, Type.CANNON, new CannonMoveStrategy()));
        board.put(Position.of(2, 7),Piece.of(Team.CHU, Type.CANNON, new CannonMoveStrategy()));
        board.put(Position.of(3, 0),Piece.of(Team.CHU, Type.SOLIDER, new SoldierMoveStrategy()));
        board.put(Position.of(3, 2),Piece.of(Team.CHU, Type.SOLIDER, new SoldierMoveStrategy()));
        board.put(Position.of(3, 4),Piece.of(Team.CHU, Type.SOLIDER, new SoldierMoveStrategy()));
        board.put(Position.of(3, 6),Piece.of(Team.CHU, Type.SOLIDER, new SoldierMoveStrategy()));
        board.put(Position.of(3, 8),Piece.of(Team.CHU, Type.SOLIDER, new SoldierMoveStrategy()));

        board.put(Position.of(9, 0),Piece.of(Team.HAN, Type.CHARIOT, new ChariotMoveStrategy()));
        board.put(Position.of(9, 1),Piece.of(Team.HAN, Type.ELEPHANT, new ElephantMoveStrategy()));
        board.put(Position.of(9, 2),Piece.of(Team.HAN, Type.HORSE, new HorseMoveStrategy()));
        board.put(Position.of(9, 3),Piece.of(Team.HAN, Type.GUARD, new GuardMoveStrategy()));
        board.put(Position.of(9, 5),Piece.of(Team.HAN, Type.GUARD, new GuardMoveStrategy()));
        board.put(Position.of(9, 6),Piece.of(Team.HAN, Type.ELEPHANT, new ElephantMoveStrategy()));
        board.put(Position.of(9, 7),Piece.of(Team.HAN, Type.HORSE, new HorseMoveStrategy()));
        board.put(Position.of(9, 8),Piece.of(Team.HAN, Type.CHARIOT, new ChariotMoveStrategy()));
        board.put(Position.of(8, 4),Piece.of(Team.HAN, Type.GENERAL, new GeneralMoveStrategy()));
        board.put(Position.of(7, 1),Piece.of(Team.HAN, Type.CANNON, new CannonMoveStrategy()));
        board.put(Position.of(7, 7),Piece.of(Team.HAN, Type.CANNON, new CannonMoveStrategy()));
        board.put(Position.of(6, 0),Piece.of(Team.HAN, Type.SOLIDER, new SoldierMoveStrategy()));
        board.put(Position.of(6, 2),Piece.of(Team.HAN, Type.SOLIDER, new SoldierMoveStrategy()));
        board.put(Position.of(6, 4),Piece.of(Team.HAN, Type.SOLIDER, new SoldierMoveStrategy()));
        board.put(Position.of(6, 6),Piece.of(Team.HAN, Type.SOLIDER, new SoldierMoveStrategy()));
        board.put(Position.of(6, 8),Piece.of(Team.HAN, Type.SOLIDER, new SoldierMoveStrategy()));

        return Board.of(board);
    }
}
