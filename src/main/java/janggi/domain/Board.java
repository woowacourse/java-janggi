package janggi.domain;

import janggi.domain.piece.None;
import janggi.domain.piece.Piece;
import janggi.domain.piece.Position;
import java.util.HashMap;
import java.util.Map;

public class Board {
    private final Map<Position, Piece> board;

    public Board(Map<Position, Piece> board) {
        this.board = new HashMap<>(board);
    }

    public Piece getPieceByPosition(final Position position) {
        return board.get(position);
    }

    public Map<Position, Piece> getBoard() {
        return new HashMap<>(board);
    }

    public void movePiece(Position beforePosition, Position afterPosition) {
        Piece piece = board.get(beforePosition);
        if (piece.isNone()) {
            throw new IllegalArgumentException("해당 위치에 이동시킬 기물이 존재하지 않습니다.");
        }
        piece.getMovableValidator(beforePosition, afterPosition).accept(board);
        board.put(beforePosition, new None());
        board.put(afterPosition, piece);
    }
}
