package janggi.dto;

import janggi.domain.board.Position;
import janggi.domain.piece.Piece;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public record BoardDto(Map<PositionDto, PieceDto> piecePosition) {

    public BoardDto {
        piecePosition = Collections.unmodifiableMap(piecePosition);
    }

    public static BoardDto from(Map<Position, Piece> piecePositions) {
        Map<PositionDto, PieceDto> piecePosition = new HashMap<>();
        piecePositions.forEach(
                (position, piece) -> piecePosition.put(PositionDto.from(position), PieceDto.from(piece)));
        return new BoardDto(piecePosition);
    }
}
