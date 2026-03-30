package dto;

import domain.piece.Piece;
import domain.position.Position;

import java.util.Map;
import java.util.stream.Collectors;

public record BoardResponseDto(
        Map<Position, PieceDto> state
) {

    public static BoardResponseDto from(Map<Position, Piece> state) {
        return new BoardResponseDto(state.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> PieceDto.from(entry.getValue()))
                ));
    }
}
