package janggi.domain.board;

import janggi.domain.dynasty.Dynasty;
import janggi.domain.piece.Piece;
import janggi.domain.position.Position;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static janggi.common.ErrorMessage.*;

public class Board {

    private final Map<Position, Piece> board;

    public Board(BoardDesignPolicy boardDesignPolicy) {
        this.board = new HashMap<>(boardDesignPolicy.initBoard());
    }

    public Map<Position, Piece> board() {
        return Map.copyOf(board);
    }

    public List<Position> canMovePosition(Position from, Dynasty currentTurn) {
        if (!board.containsKey(from)) {
            throw new IllegalArgumentException(PIECE_NOT_FOUND.message());
        }
        Piece piece = board.get(from);
        if (!piece.isSameDynasty(currentTurn)) {
            throw new IllegalArgumentException(INVALID_PIECE_OWNER.message());
        }
        return piece.canMovePosition(board, from);
    }

    public void movePiece(Position from, Position to, Dynasty currentTurn) {
        List<Position> positions = canMovePosition(from, currentTurn);
        if (!positions.contains(to)) {
            throw new IllegalArgumentException(INVALID_PIECE_MOVE.message());
        }

        Piece fromPiece = board.remove(from);
        board.put(to, fromPiece);
    }

}
