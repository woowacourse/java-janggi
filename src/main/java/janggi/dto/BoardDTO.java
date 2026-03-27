package janggi.dto;

import janggi.domain.piece.Piece;
import janggi.domain.board.Position;
import java.util.Collections;
import java.util.Map;

public record BoardDTO(Map<Position, Piece> piecePosition) {
    public BoardDTO {
        piecePosition = Collections.unmodifiableMap(piecePosition);
    }
}
