package janggi.dto;

import janggi.domain.PieceVO;
import janggi.domain.Position;
import java.util.Collections;
import java.util.Map;

public record BoardDTO(Map<Position, PieceVO> piecePosition) {
    public BoardDTO {
        piecePosition = Collections.unmodifiableMap(piecePosition);
    }
}
