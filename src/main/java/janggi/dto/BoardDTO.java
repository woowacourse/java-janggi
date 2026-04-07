package janggi.dto;

import janggi.domain.board.Position;
import janggi.domain.piece.Piece;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public record BoardDTO(Map<PositionDTO, PieceDTO> piecePosition) {

    public BoardDTO {
        piecePosition = Collections.unmodifiableMap(piecePosition);
    }

    public static BoardDTO from(Map<Position, Piece> piecePositions) {
        Map<PositionDTO, PieceDTO> piecePosition = new HashMap<>();
        piecePositions.forEach(
                (position, piece) -> piecePosition.put(PositionDTO.from(position), PieceDTO.from(piece)));
        return new BoardDTO(piecePosition);
    }
}
