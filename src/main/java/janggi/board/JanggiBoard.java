package janggi.board;

import janggi.Side;
import janggi.piece.*;

import java.util.HashMap;
import java.util.Map;

import static janggi.board.InitialPositions.*;

public class JanggiBoard {

    private static final int ROW_SIZE = 9;
    private static final int COL_SIZE = 10;

    private final Map<Position, Piece> board;

    private JanggiBoard(final Map<Position, Piece> board) {
        this.board = board;
    }

    public static JanggiBoard initialize() {
        Map<Position, Piece> board = new HashMap<>();

        for (int row = 0; row < ROW_SIZE; row++) {
            for (int col = 0; col < COL_SIZE; col++) {
                board.put(new Position(row, col), new Empty());
            }
        }

        initializeSoldier(board);
        initializeElephant(board);
        initializeHorse(board);
        initializeCannon(board);
        return new JanggiBoard(board);
    }

    private static void initializeSoldier(Map<Position, Piece> board) {
        for (Position choSoldierPosition : CHO_SOLDIER_POSITION.getPositions()) {
            board.put(choSoldierPosition, new Soldier(Side.CHO));
        }
        for (Position hanSoldierPosition : HAN_SOLDIER_POSITIONS.getPositions()) {
            board.put(hanSoldierPosition, new Soldier(Side.HAN));
        }
    }

    private static void initializeElephant(final Map<Position, Piece> board) {
        for (Position choElephantPosition : CHO_ELEPHANT_POSITIONS.getPositions()) {
            board.put(choElephantPosition, new Elephant(Side.CHO));
        }
        for (Position hanElephantPosition : HAN_ELEPHANT_POSITIONS.getPositions()) {
            board.put(hanElephantPosition, new Elephant(Side.CHO));
        }
    }

    private static void initializeHorse(final Map<Position, Piece> board) {
        for (Position choHorsePosition : CHO_HORSE_POSITIONS.getPositions()) {
            board.put(choHorsePosition, new Horse(Side.CHO));
        }
        for (Position hanHorsePosition : HAN_HORSE_POSITIONS.getPositions()) {
            board.put(hanHorsePosition, new Horse(Side.HAN));
        }
    }

    private static void initializeCannon(final Map<Position, Piece> board) {

        for (Position choCannonPosition : CHO_CANNON_POSITIONS.getPositions()) {
            board.put(choCannonPosition, new Cannon(Side.CHO));
        }
        for (Position hanCannonPosition : HAN_CANNON_POSITIONS.getPositions()) {
            board.put(hanCannonPosition, new Cannon(Side.HAN));
        }


    }

    public Map<Position, Piece> getBoard() {
        return board;
    }

}
