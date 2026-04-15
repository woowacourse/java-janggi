package janggi.dto;

import janggi.domain.Side;
import janggi.domain.result.GameResult;

public record FinalResultDto(String winner, ScoreResultDto scoreResultDto) {

    public static FinalResultDto of(GameResult gameResult) {
        Side winnerSide = gameResult.getWinner();
        String winner = winnerSide.getNameFormat();
        ScoreResultDto scoreResultDto = ScoreResultDto.from(gameResult);
        return new FinalResultDto(winner, scoreResultDto);
    }
}
