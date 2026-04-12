package janggi.dto;

import janggi.domain.Side;
import janggi.domain.result.ScoreResult;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

public record ScoreResultDto(Map<String, Double> scoreResults) {

    public static ScoreResultDto from(ScoreResult scoreResult) {
        Map<String, Double> scoreDto = new LinkedHashMap<>();
        Arrays.stream(Side.values())
                .filter(side -> !side.equals(Side.NONE))
                .forEachOrdered(side -> scoreDto.put(side.getNameFormat(), scoreResult.getScoreOf(side)));

        return new ScoreResultDto(scoreDto);
    }
}
