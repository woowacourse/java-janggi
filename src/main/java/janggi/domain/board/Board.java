package janggi.domain.board;

import janggi.domain.dynasty.Dynasty;
import janggi.domain.piece.Piece;
import janggi.domain.position.Position;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Board {

    private final Map<Position, Piece> board;

    public static final String PIECE_NOT_FOUND_MESSAGE = "해당 위치(%d, %d)에 기물이 존재하지 않습니다.";
    public static final String INVALID_PIECE_OWNER_MESSAGE = "해당 위치(%d, %d)에 기물이 존재하지 않습니다.";
    public static final String INVALID_PIECE_MOVE_MESSAGE = "(%d, %d)위치의 기물을 (%d, %d)로 옮길 수 없습니다.";

    public Board(BoardDesignPolicy boardDesignPolicy) {
        this.board = new HashMap<>(boardDesignPolicy.initBoard());
    }

    public Map<Position, Piece> board() {
        return Map.copyOf(board);
    }

    public List<Position> canMovePosition(Position from, Dynasty currentTurn) {
        if (!board.containsKey(from)) {
            throw new IllegalArgumentException(
                    String.format(PIECE_NOT_FOUND_MESSAGE, from.row().row(), from.column().column()));
        }
        Piece piece = board.get(from);
        if (!piece.isAlly(currentTurn)) {
            throw new IllegalArgumentException(
                    String.format(INVALID_PIECE_OWNER_MESSAGE, from.row().row(), from.column().column()));
        }
        return piece.canMovePosition(board, from);
    }

    public void movePiece(Position from, Position to, Dynasty currentTurn) {
        List<Position> positions = canMovePosition(from, currentTurn);
        if (!positions.contains(to)) {
            throw new IllegalArgumentException(
                    String.format(INVALID_PIECE_MOVE_MESSAGE,
                            from.row().row(), from.column().column(),
                            to.row().row(), to.column().column()));
        }

        Piece fromPiece = board.remove(from);
        board.put(to, fromPiece);
    }

}
