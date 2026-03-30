package janggi.domain.board;

import janggi.domain.dynasty.Dynasty;
import janggi.domain.piece.Piece;
import janggi.domain.position.Position;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Board {

    private final Map<Position, Piece> board;

    public static final String PIECE_NOT_FOUND_MESSAGE = "해당 위치에 기물이 존재하지 않습니다.";
    public static final String INVALID_PIECE_OWNER_MESSAGE = "해당 위치에 기물이 존재하지 않습니다.";
    public static final String INVALID_PIECE_MOVE_MESSAGE = "해당 위치에 해당 기물을 옮길 수 없습니다.";

    public Board(BoardDesignPolicy boardDesignPolicy) {
        this.board = new HashMap<>(boardDesignPolicy.initBoard());
    }

    public Map<Position, Piece> board() {
        return Map.copyOf(board);
    }

    public List<Position> canMovePosition(Position from, Dynasty currentTurn) {
        if (!board.containsKey(from)) {
            throw new IllegalArgumentException(PIECE_NOT_FOUND_MESSAGE);
        }
        Piece piece = board.get(from);
        if (!piece.isSameDynasty(currentTurn)) {
            throw new IllegalArgumentException(INVALID_PIECE_OWNER_MESSAGE);
        }
        return piece.canMovePosition(board, from);
    }

    public void movePiece(Position from, Position to, Dynasty currentTurn) {
        List<Position> positions = canMovePosition(from, currentTurn);
        if (!positions.contains(to)) {
            throw new IllegalArgumentException(INVALID_PIECE_MOVE_MESSAGE);
        }

        Piece fromPiece = board.remove(from);
        board.put(to, fromPiece);
    }

}
