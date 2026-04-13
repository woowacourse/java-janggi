package domain;

import domain.strategy.*;
import domain.vo.Position;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

public class BoardFactory {

    private static final Comparator<Position> POSITION_COMPARATOR = Comparator
            .comparingInt(Position::getRow).reversed()
            .thenComparingInt(Position::getCol);

    private BoardFactory() {}

    public static Board of(final Map<Position, Piece> board) {
        return Board.of(board);
    }

    public static Board setUpLeftElephantFormation(Map<Position, Piece> initialBoard, Team team) {
        Map<Position, Piece> board = new TreeMap<>(POSITION_COMPARATOR);
        board.putAll(initialBoard);
        if (team == Team.CHU) {
            board.put(Position.of(0, 1), Piece.of(Team.CHU, Type.ELEPHANT));
            board.put(Position.of(0, 6), Piece.of(Team.CHU, Type.ELEPHANT));
            board.put(Position.of(0, 2), Piece.of(Team.CHU, Type.HORSE));
            board.put(Position.of(0, 7), Piece.of(Team.CHU, Type.HORSE));
        }
        if (team == Team.HAN) {
            board.put(Position.of(9, 1), Piece.of(Team.HAN, Type.ELEPHANT));
            board.put(Position.of(9, 6), Piece.of(Team.HAN, Type.ELEPHANT));
            board.put(Position.of(9, 2), Piece.of(Team.HAN, Type.HORSE));
            board.put(Position.of(9, 7), Piece.of(Team.HAN, Type.HORSE));
        }

        return Board.of(board);
    }

    public static Board setUpRightElephantFormation(Map<Position, Piece> initialBoard, Team team) {
        Map<Position, Piece> board = new TreeMap<>(POSITION_COMPARATOR);
        board.putAll(initialBoard);
        if (team == Team.CHU) {
            board.put(Position.of(0, 2), Piece.of(Team.CHU, Type.ELEPHANT));
            board.put(Position.of(0, 7), Piece.of(Team.CHU, Type.ELEPHANT));
            board.put(Position.of(0, 1), Piece.of(Team.CHU, Type.HORSE));
            board.put(Position.of(0, 6), Piece.of(Team.CHU, Type.HORSE));
        }
        if (team == Team.HAN) {
            board.put(Position.of(9, 2), Piece.of(Team.HAN, Type.ELEPHANT));
            board.put(Position.of(9, 7), Piece.of(Team.HAN, Type.ELEPHANT));
            board.put(Position.of(9, 1), Piece.of(Team.HAN, Type.HORSE));
            board.put(Position.of(9, 6), Piece.of(Team.HAN, Type.HORSE));
        }

        return Board.of(board);
    }

    public static Board setUpInnerElephantFormation(Map<Position, Piece> initialBoard, Team team) {
        Map<Position, Piece> board = new TreeMap<>(POSITION_COMPARATOR);
        board.putAll(initialBoard);
        if (team == Team.CHU) {
            board.put(Position.of(0, 2), Piece.of(Team.CHU, Type.ELEPHANT));
            board.put(Position.of(0, 6), Piece.of(Team.CHU, Type.ELEPHANT));
            board.put(Position.of(0, 1), Piece.of(Team.CHU, Type.HORSE));
            board.put(Position.of(0, 7), Piece.of(Team.CHU, Type.HORSE));
        }
        if (team == Team.HAN) {
            board.put(Position.of(9, 2), Piece.of(Team.HAN, Type.ELEPHANT));
            board.put(Position.of(9, 6), Piece.of(Team.HAN, Type.ELEPHANT));
            board.put(Position.of(9, 1), Piece.of(Team.HAN, Type.HORSE));
            board.put(Position.of(9, 7), Piece.of(Team.HAN, Type.HORSE));
        }

        return Board.of(board);
    }

    public static Board setUpOuterElephantFormation(Map<Position, Piece> initialBoard, Team team) {
        Map<Position, Piece> board = new TreeMap<>(POSITION_COMPARATOR);
        board.putAll(initialBoard);
        if (team == Team.CHU) {
            board.put(Position.of(0, 1), Piece.of(Team.CHU, Type.ELEPHANT));
            board.put(Position.of(0, 7), Piece.of(Team.CHU, Type.ELEPHANT));
            board.put(Position.of(0, 2), Piece.of(Team.CHU, Type.HORSE));
            board.put(Position.of(0, 6), Piece.of(Team.CHU, Type.HORSE));
        }
        if (team == Team.HAN) {
            board.put(Position.of(9, 1), Piece.of(Team.HAN, Type.ELEPHANT));
            board.put(Position.of(9, 7), Piece.of(Team.HAN, Type.ELEPHANT));
            board.put(Position.of(9, 2), Piece.of(Team.HAN, Type.HORSE));
            board.put(Position.of(9, 6), Piece.of(Team.HAN, Type.HORSE));
        }

        return Board.of(board);
    }

    public static Board setUp() {
        Map<Position, Piece> board = new TreeMap<>(POSITION_COMPARATOR);

        board.put(Position.of(0, 0),Piece.of(Team.CHU, Type.CHARIOT));
        board.put(Position.of(0, 1),Piece.of(Team.CHU, Type.ELEPHANT));
        board.put(Position.of(0, 2),Piece.of(Team.CHU, Type.HORSE));
        board.put(Position.of(0, 3),Piece.of(Team.CHU, Type.GUARD));
        board.put(Position.of(0, 5),Piece.of(Team.CHU, Type.GUARD));
        board.put(Position.of(0, 6),Piece.of(Team.CHU, Type.ELEPHANT));
        board.put(Position.of(0, 7),Piece.of(Team.CHU, Type.HORSE));
        board.put(Position.of(0, 8),Piece.of(Team.CHU, Type.CHARIOT));
        board.put(Position.of(1, 4),Piece.of(Team.CHU, Type.GENERAL));
        board.put(Position.of(2, 1),Piece.of(Team.CHU, Type.CANNON));
        board.put(Position.of(2, 7),Piece.of(Team.CHU, Type.CANNON));
        board.put(Position.of(3, 0),Piece.of(Team.CHU, Type.SOLDIER));
        board.put(Position.of(3, 2),Piece.of(Team.CHU, Type.SOLDIER));
        board.put(Position.of(3, 4),Piece.of(Team.CHU, Type.SOLDIER));
        board.put(Position.of(3, 6),Piece.of(Team.CHU, Type.SOLDIER));
        board.put(Position.of(3, 8),Piece.of(Team.CHU, Type.SOLDIER));

        board.put(Position.of(9, 0),Piece.of(Team.HAN, Type.CHARIOT));
        board.put(Position.of(9, 1),Piece.of(Team.HAN, Type.ELEPHANT));
        board.put(Position.of(9, 2),Piece.of(Team.HAN, Type.HORSE));
        board.put(Position.of(9, 3),Piece.of(Team.HAN, Type.GUARD));
        board.put(Position.of(9, 5),Piece.of(Team.HAN, Type.GUARD));
        board.put(Position.of(9, 6),Piece.of(Team.HAN, Type.ELEPHANT));
        board.put(Position.of(9, 7),Piece.of(Team.HAN, Type.HORSE));
        board.put(Position.of(9, 8),Piece.of(Team.HAN, Type.CHARIOT));
        board.put(Position.of(8, 4),Piece.of(Team.HAN, Type.GENERAL));
        board.put(Position.of(7, 1),Piece.of(Team.HAN, Type.CANNON));
        board.put(Position.of(7, 7),Piece.of(Team.HAN, Type.CANNON));
        board.put(Position.of(6, 0),Piece.of(Team.HAN, Type.SOLDIER));
        board.put(Position.of(6, 2),Piece.of(Team.HAN, Type.SOLDIER));
        board.put(Position.of(6, 4),Piece.of(Team.HAN, Type.SOLDIER));
        board.put(Position.of(6, 6),Piece.of(Team.HAN, Type.SOLDIER));
        board.put(Position.of(6, 8),Piece.of(Team.HAN, Type.SOLDIER));

        return Board.of(board);
    }
}
