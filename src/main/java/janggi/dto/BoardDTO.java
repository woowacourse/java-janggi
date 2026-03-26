package janggi.dto;

import janggi.domain.piece.PieceVO;
import janggi.domain.board.Position;
import java.util.Collections;
import java.util.Map;

public record BoardDTO(Map<Position, PieceVO> piecePosition) {
    public BoardDTO {
        piecePosition = Collections.unmodifiableMap(piecePosition);
    }
}
