package janggi.dto;

import janggi.domain.Side;
import janggi.domain.result.ScoreResult;

public record FinalResultDto(String winner, ScoreResultDto scoreResultDto) {

    public static FinalResultDto of(Side side, ScoreResult scoreResult) {
        String winner = side.getNameFormat();
        ScoreResultDto scoreResultDto = ScoreResultDto.from(scoreResult);
        return new FinalResultDto(winner, scoreResultDto);
    }
}
