package view.dto;

import domain.pieces.Side;

public record GameScoreResultDto(
        Side winner,
        double choScore,
        double hanScore
) {
}
