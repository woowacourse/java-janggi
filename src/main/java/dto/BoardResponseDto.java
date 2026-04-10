package dto;

import domain.position.Position;

import java.util.Map;

public record BoardResponseDto(
        Map<Position, PieceDto> state
) { }
