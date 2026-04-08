package janggi.domain.board;

import janggi.domain.piece.*;
import janggi.domain.vo.position.Position;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BoardInitializer {
    private static final List<Integer> SOLDIER_ROWS = List.of(3, 6);
    private static final List<Integer> CANNON_ROWS = List.of(2, 7);
    private static final List<Integer> KING_ROWS = List.of(1, 8);
    private static final List<Integer> BOTTOM_ROWS = List.of(0, 9);

    private static final List<Integer> SOLDIER_COLS = List.of(0, 2, 4, 6, 8);
    private static final List<Integer> CANNON_COLS = List.of(1, 7);
    private static final List<Integer> TANK_COLS = List.of(0, 8);
    private static final List<Integer> HORSE_COLS = List.of(1, 7);
    private static final List<Integer> ELEPHANT_COLS = List.of(2, 6);
    private static final List<Integer> ADVISOR_COLS = List.of(3, 5);
    private static final List<Integer> KING_COL = List.of(4);

    public static Map<Position, Piece> createBoard() {
        Map<Position, Piece> board = new HashMap<>();

        createPiece(board, SOLDIER_ROWS, SOLDIER_COLS, PieceType.SOLDIER);
        createPiece(board, CANNON_ROWS, CANNON_COLS, PieceType.CANNON);
        createPiece(board, BOTTOM_ROWS, TANK_COLS, PieceType.TANK);
        createPiece(board, BOTTOM_ROWS, HORSE_COLS, PieceType.HORSE);
        createPiece(board, BOTTOM_ROWS, ELEPHANT_COLS, PieceType.ELEPHANT);
        createPiece(board, BOTTOM_ROWS, ADVISOR_COLS, PieceType.ADVISOR);
        createPiece(board, KING_ROWS, KING_COL, PieceType.KING);

        return board;
    }

    private static void createPiece(Map<Position, Piece> board, List<Integer> rows, List<Integer> cols, PieceType type) {
        for (int col : cols) {
            board.put(new Position(getHanRow(rows), col), PieceFactory.create(type, Team.HAN));
            board.put(new Position(getChoRow(rows), col), PieceFactory.create(type, Team.CHO));
        }
    }

    private static int getHanRow(List<Integer> rows) {
        return rows.get(0);
    }

    private static int getChoRow(List<Integer> rows) {
        return rows.get(1);
    }
}
