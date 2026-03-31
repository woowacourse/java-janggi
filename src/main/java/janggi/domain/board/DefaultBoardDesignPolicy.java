package janggi.domain.board;

import janggi.domain.dynasty.Dynasty;
import janggi.domain.piece.*;
import janggi.domain.position.Position;

import java.util.HashMap;
import java.util.Map;

import static janggi.domain.piece.PieceType.*;

public class DefaultBoardDesignPolicy implements BoardDesignPolicy {

    private final Map<Dynasty, HorseElephantPosition> horseElephantPositionMap;

    public DefaultBoardDesignPolicy(Map<Dynasty, HorseElephantPosition> horseElephantPositionMap) {
        this.horseElephantPositionMap = horseElephantPositionMap;
    }

    public Map<Position, Piece> initBoard() {
        Map<Position, Piece> board = new HashMap<>();
        for (Dynasty dynasty : Dynasty.values()) {
            settingPiece(board, horseElephantPositionMap.get(dynasty), dynasty);
        }
        return board;
    }

    private static void settingPiece(Map<Position, Piece> board, HorseElephantPosition horseElephantPos, Dynasty dynasty) {
        settingFirstRow(board, horseElephantPos, dynasty);
        settingSecondRow(board, dynasty);
        settingThirdRow(board, dynasty);
        settingFourthRow(board, dynasty);
    }

    private static void settingFirstRow(Map<Position, Piece> board, HorseElephantPosition horseElephantPos, Dynasty dynasty) {
        int row = dynasty.resolveRow(1);

        board.put(Position.from(row, 1), new Piece(dynasty, CHARIOT));
        board.put(Position.from(row, 4), new Piece(dynasty, GUARD));
        board.put(Position.from(row, 6), new Piece(dynasty, GUARD));
        board.put(Position.from(row, 9), new Piece(dynasty, CHARIOT));
        settingHorseAndElephant(board, horseElephantPos, dynasty, row);
    }

    private static void settingHorseAndElephant(Map<Position, Piece> board, HorseElephantPosition horseElephantPos, Dynasty dynasty, int row) {
        board.put(Position.from(row, horseElephantPos.leftHorseColumn()), new Piece(dynasty, HORSE));
        board.put(Position.from(row, horseElephantPos.leftElephantColumn()), new Piece(dynasty, ELEPHANT));
        board.put(Position.from(row, horseElephantPos.rightHorseColumn()), new Piece(dynasty, HORSE));
        board.put(Position.from(row, horseElephantPos.rightElephantColumn()), new Piece(dynasty, ELEPHANT));
    }

    private static void settingSecondRow(Map<Position, Piece> board, Dynasty dynasty) {
        int row = dynasty.resolveRow(2);
        board.put(Position.from(row, 5), new Piece(dynasty, PieceType.GENERAL));
    }

    private static void settingThirdRow(Map<Position, Piece> board, Dynasty dynasty) {
        int row = dynasty.resolveRow(3);
        board.put(Position.from(row, 2), new Piece(dynasty, CANNON));
        board.put(Position.from(row, 8), new Piece(dynasty, CANNON));
    }

    private static void settingFourthRow(Map<Position, Piece> board, Dynasty dynasty) {
        int row = dynasty.resolveRow(4);
        board.put(Position.from(row, 1), new Piece(dynasty, SOLDIER));
        board.put(Position.from(row, 3), new Piece(dynasty, SOLDIER));
        board.put(Position.from(row, 5), new Piece(dynasty, SOLDIER));
        board.put(Position.from(row, 7), new Piece(dynasty, SOLDIER));
        board.put(Position.from(row, 9), new Piece(dynasty, SOLDIER));
    }

}
