package janggi.board;

import janggi.Side;
import janggi.piece.*;
import janggi.view.OutputView;

import java.util.HashMap;
import java.util.Map;

import static janggi.board.InitialPositions.*;

public class JanggiBoard {

    private static final int X_LIMIT = 9;
    private static final int Y_LIMIT = 10;

    private final Map<Position, Piece> board;

    private JanggiBoard(final Map<Position, Piece> board) {
        this.board = board;
    }

    public static JanggiBoard initialize() {
        Map<Position, Piece> board = new HashMap<>();

        for (int x = 0; x < X_LIMIT; x++) {
            for (int y = 0; y < Y_LIMIT; y++) {
                board.put(new Position(x, y), new Empty());
            }
        }

        initializeSoldier(board);
        initializeElephant(board);
        initializeHorse(board);
        initializeCannon(board);
        initializeChariot(board);
        initializeGuard(board);
        initializeKing(board);
        return new JanggiBoard(board);
    }

    public void printBoard() {
        OutputView outputView = new OutputView();
        outputView.printBoard(board);
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
            board.put(hanElephantPosition, new Elephant(Side.HAN));
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

    private static void initializeChariot(final Map<Position, Piece> board) {
        for (Position choChariotPosition : CHO_CHARIOT_POSITIONS.getPositions()) {
            board.put(choChariotPosition, new Chariot(Side.CHO));
        }
        for (Position hanChariotPosition : HAN_CHARIOT_POSITIONS.getPositions()) {
            board.put(hanChariotPosition, new Chariot(Side.HAN));
        }
    }

    private static void initializeGuard(final Map<Position, Piece> board) {
        for (Position choGuardPosition : CHO_GUARD_POSITIONS.getPositions()) {
            board.put(choGuardPosition, new Guard(Side.CHO));
        }
        for (Position hanGuardPosition : HAN_GUARD_POSITIONS.getPositions()) {
            board.put(hanGuardPosition, new Guard(Side.HAN));
        }
    }

    private static void initializeKing(final Map<Position, Piece> board) {
        for (Position choKingPosition : CHO_KING_POSITIONS.getPositions()) {
            board.put(choKingPosition, new King(Side.CHO));
        }
        for (Position hanKingPosition : HAN_KING_POSITIONS.getPositions()) {
            board.put(hanKingPosition, new King(Side.HAN));
        }
    }

    public Map<Position, Piece> getBoard() {
        return board;
    }

}
