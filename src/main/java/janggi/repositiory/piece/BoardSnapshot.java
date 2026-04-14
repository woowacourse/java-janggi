package janggi.repositiory.piece;

import janggi.domain.piece.Piece;
import janggi.domain.vo.position.Position;

import java.util.Map;

public record BoardSnapshot(
        Long gameId,
        Map<Position, Piece> pieces
) {
    public BoardSnapshot updatePieces(Map<Position, Piece> board) {
        return new BoardSnapshot(this.gameId, board);
    }
}