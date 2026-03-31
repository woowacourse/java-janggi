package janggi.domain.board;

import janggi.domain.piece.*;
import janggi.domain.vo.Position;

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

        initSoldier(board);
        initCannon(board);
        initTank(board);
        initHorse(board);
        initElephant(board);
        initAdvisor(board);
        initKing(board);

        return board;
    }

    private static void initSoldier(Map<Position, Piece> board) {
        for (int col : SOLDIER_COLS) {
            board.put(new Position(getHanRow(SOLDIER_ROWS), col), new Soldier(Team.HAN));
            board.put(new Position(getChoRow(SOLDIER_ROWS), col), new Soldier(Team.CHO));
        }
    }

    private static void initCannon(Map<Position, Piece> board) {
        for (int col : CANNON_COLS) {
            board.put(new Position(getHanRow(CANNON_ROWS), col), new Cannon(Team.HAN));
            board.put(new Position(getChoRow(CANNON_ROWS), col), new Cannon(Team.CHO));
        }
    }

    private static void initTank(Map<Position, Piece> board) {
        for (int col : TANK_COLS) {
            board.put(new Position(getHanRow(BOTTOM_ROWS), col), new Tank(Team.HAN));
            board.put(new Position(getChoRow(BOTTOM_ROWS), col), new Tank(Team.CHO));
        }
    }

    private static void initHorse(Map<Position, Piece> board) {
        for (int col : HORSE_COLS) {
            board.put(new Position(getHanRow(BOTTOM_ROWS), col), new Horse(Team.HAN));
            board.put(new Position(getChoRow(BOTTOM_ROWS), col), new Horse(Team.CHO));
        }
    }

    private static void initElephant(Map<Position, Piece> board) {
        for (int col : ELEPHANT_COLS) {
            board.put(new Position(getHanRow(BOTTOM_ROWS), col), new Elephant(Team.HAN));
            board.put(new Position(getChoRow(BOTTOM_ROWS), col), new Elephant(Team.CHO));
        }
    }

    private static void initAdvisor(Map<Position, Piece> board) {
        for (int col : ADVISOR_COLS) {
            board.put(new Position(getHanRow(BOTTOM_ROWS), col), new Advisor(Team.HAN));
            board.put(new Position(getChoRow(BOTTOM_ROWS), col), new Advisor(Team.CHO));
        }
    }

    private static void initKing(Map<Position, Piece> board) {
        for (int col : KING_COL) {
            board.put(new Position(getHanRow(KING_ROWS), col), new King(Team.HAN));
            board.put(new Position(getChoRow(KING_ROWS), col), new King(Team.CHO));
        }
    }

    private static int getHanRow(List<Integer> rows) {
        return rows.get(0);
    }

    private static int getChoRow(List<Integer> rows) {
        return rows.get(1);
    }
}
