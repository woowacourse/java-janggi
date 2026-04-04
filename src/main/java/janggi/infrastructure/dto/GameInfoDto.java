package janggi.infrastructure.dto;

import java.util.List;

public record GameInfoDto(
        String gameId,
        String currentTurn,
        List<PieceDto> pieces
) {
}