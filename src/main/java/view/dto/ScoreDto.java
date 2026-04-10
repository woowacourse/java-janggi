package view.dto;

import domain.game.Side;

public record ScoreDto(
        Side side,
        double score
) {
}
