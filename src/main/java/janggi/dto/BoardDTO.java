package janggi.dto;

import janggi.domain.Position;
import java.util.Collections;
import java.util.Map;

public record BoardDTO(Map<Position, String> piecePosition) {
    public BoardDTO {
        piecePosition = Collections.unmodifiableMap(piecePosition);
    }
}
