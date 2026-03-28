package janggi.dto;

import java.util.Collections;
import java.util.Map;

public record BoardDTO(Map<PositionDTO, PieceDTO> piecePosition) {

    public BoardDTO {
        piecePosition = Collections.unmodifiableMap(piecePosition);
    }
}
