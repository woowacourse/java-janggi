package dto;

import java.util.Map;


public record BoardDto(
        Map<PositionDto, PieceDto> pieces
) {
}
