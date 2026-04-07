package janggi.dto;

import janggi.domain.space.Position;
import janggi.domain.piece.Piece;
import java.util.Map;
import java.util.stream.Collectors;

public record BoardDto(Map<PositionDto, PieceDto> board) {
    public static BoardDto from(Map<Position, Piece> board) {
        Map<PositionDto, PieceDto> dtoMap = board.entrySet().stream()
                .collect(Collectors.toUnmodifiableMap(
                        entry -> PositionDto.from(entry.getKey()),
                        entry -> PieceDto.from(entry.getValue())
                ));
        return new BoardDto(dtoMap);
    }
}
