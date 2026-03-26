package janggi.domain;

import janggi.domain.piece.Advisor;
import janggi.domain.piece.Cannon;
import janggi.domain.piece.Elephant;
import janggi.domain.piece.EmptyPosition;
import janggi.domain.piece.Horse;
import janggi.domain.piece.King;
import janggi.domain.piece.Piece;
import janggi.domain.piece.Soldier;
import janggi.domain.piece.Tank;
import janggi.domain.piece.Team;
import java.util.ArrayList;
import java.util.List;

public class BoardInitializer {
    private static final int ROW_LEN = 10;
    private static final int COL_LEN = 9;

    private static final List<Integer> soldierCol = List.of(0, 2, 4, 6, 8);
    private static final List<Integer> cannonCol = List.of(1, 7);
    private static final List<Integer> tankCol = List.of(0, 8);
    private static final List<Integer> horseCol = List.of(1, 7);
    private static final List<Integer> elephantCol = List.of(2, 6);
    private static final List<Integer> advisorCol = List.of(3, 5);
    private static final int kingCol = 4;

    public static List<List<Piece>> createBoard() {
        List<List<Piece>> board = createEmptyBoard();

        initTank(board);
        initHorse(board);
        initElephant(board);
        initAdvisor(board);

        initKing(board);
        initCannon(board);
        initSoldier(board);
        return board;
    }

    public static List<List<Piece>> createEmptyBoard() {
        List<List<Piece>> board = new ArrayList<>();
        initBoard(board);
        return board;
    }

    private static void initBoard(List<List<Piece>> board) {
        for (int row = 0; row < ROW_LEN; row++) {
            board.add(new ArrayList<>());
        }

        for (int row = 0; row < ROW_LEN; row++) {
            for (int col = 0; col < COL_LEN; col++) {
                board.get(row).add(new EmptyPosition(Team.OTHER));
            }
        }
    }

    private static void initSoldier(List<List<Piece>> board) {
        for (int col : soldierCol) {
            board.get(3).set(col, new Soldier(Team.HAN));
            board.get(6).set(col, new Soldier(Team.CHO));
        }
    }

    private static void initCannon(List<List<Piece>> board) {
        for (int col : cannonCol) {
            board.get(2).set(col, new Cannon(Team.HAN));
            board.get(7).set(col, new Cannon(Team.CHO));
        }
    }

    private static void initKing(List<List<Piece>> board) {
        board.get(1).set(kingCol, new King(Team.HAN));
        board.get(8).set(kingCol, new King(Team.CHO));
    }

    private static void initAdvisor(List<List<Piece>> board) {
        for (int col : advisorCol) {
            board.get(0).set(col, new Advisor(Team.HAN));
            board.get(9).set(col, new Advisor(Team.CHO));
        }
    }

    private static void initElephant(List<List<Piece>> board) {
        for (int col : elephantCol) {
            board.get(0).set(col, new Elephant(Team.HAN));
            board.get(9).set(col, new Elephant(Team.CHO));
        }
    }

    private static void initHorse(List<List<Piece>> board) {
        for (int col : horseCol) {
            board.get(0).set(col, new Horse(Team.HAN));
            board.get(9).set(col, new Horse(Team.CHO));
        }
    }

    private static void initTank(List<List<Piece>> board) {
        for (int col : tankCol) {
            board.get(0).set(col, new Tank(Team.HAN));
            board.get(9).set(col, new Tank(Team.CHO));
        }
    }

}
