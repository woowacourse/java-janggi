package janggi.domain.board;

import janggi.domain.Turn;
import janggi.domain.piece.Empty;
import janggi.domain.piece.Piece;
import janggi.domain.piece.limit.*;
import janggi.domain.piece.unlimit.Cannon;
import janggi.domain.piece.unlimit.Chariot;

import java.util.HashMap;
import java.util.Map;

import static janggi.domain.board.InitialPositions.*;

public class BoardInitializer {

    public static Map<Position, Piece> initialPieces(final int xLimit, final int yLimit) {
        Map<Position, Piece> board = new HashMap<>();

        for (int x = 0; x < xLimit; x++) {
            for (int y = 0; y < yLimit; y++) {
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
        return board;
    }

    private static void initializeSoldier(final Map<Position, Piece> board) {
        for (Position choSoldierPosition : CHO_SOLDIER_POSITIONS.getPositions()) {
            board.put(choSoldierPosition, new Soldier(Turn.CHO));
        }
        for (Position hanSoldierPosition : HAN_SOLDIER_POSITIONS.getPositions()) {
            board.put(hanSoldierPosition, new Soldier(Turn.HAN));
        }
    }

    private static void initializeElephant(final Map<Position, Piece> board) {
        for (Position choElephantPosition : CHO_ELEPHANT_POSITIONS.getPositions()) {
            board.put(choElephantPosition, new Elephant(Turn.CHO));
        }
        for (Position hanElephantPosition : HAN_ELEPHANT_POSITIONS.getPositions()) {
            board.put(hanElephantPosition, new Elephant(Turn.HAN));
        }
    }

    private static void initializeHorse(final Map<Position, Piece> board) {
        for (Position choHorsePosition : CHO_HORSE_POSITIONS.getPositions()) {
            board.put(choHorsePosition, new Horse(Turn.CHO));
        }
        for (Position hanHorsePosition : HAN_HORSE_POSITIONS.getPositions()) {
            board.put(hanHorsePosition, new Horse(Turn.HAN));
        }
    }

    private static void initializeCannon(final Map<Position, Piece> board) {
        for (Position choCannonPosition : CHO_CANNON_POSITIONS.getPositions()) {
            board.put(choCannonPosition, new Cannon(Turn.CHO));
        }
        for (Position hanCannonPosition : HAN_CANNON_POSITIONS.getPositions()) {
            board.put(hanCannonPosition, new Cannon(Turn.HAN));
        }
    }

    private static void initializeChariot(final Map<Position, Piece> board) {
        for (Position choChariotPosition : CHO_CHARIOT_POSITIONS.getPositions()) {
            board.put(choChariotPosition, new Chariot(Turn.CHO));
        }
        for (Position hanChariotPosition : HAN_CHARIOT_POSITIONS.getPositions()) {
            board.put(hanChariotPosition, new Chariot(Turn.HAN));
        }
    }

    private static void initializeGuard(final Map<Position, Piece> board) {
        for (Position choGuardPosition : CHO_GUARD_POSITIONS.getPositions()) {
            board.put(choGuardPosition, new Guard(Turn.CHO));
        }
        for (Position hanGuardPosition : HAN_GUARD_POSITIONS.getPositions()) {
            board.put(hanGuardPosition, new Guard(Turn.HAN));
        }
    }

    private static void initializeKing(final Map<Position, Piece> board) {
        for (Position choKingPosition : CHO_KING_POSITIONS.getPositions()) {
            board.put(choKingPosition, new King(Turn.CHO));
        }
        for (Position hanKingPosition : HAN_KING_POSITIONS.getPositions()) {
            board.put(hanKingPosition, new King(Turn.HAN));
        }
    }

}
