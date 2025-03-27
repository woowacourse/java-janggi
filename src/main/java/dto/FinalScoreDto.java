package dto;

import java.util.Map;
import java.util.stream.Collectors;

import model.Team;

public record FinalScoreDto(
    Map<TeamDto, Integer> scores
) {

    public static FinalScoreDto of(Map<Team, Integer> scores) {
        return new FinalScoreDto(
            scores.entrySet().stream()
                .collect(Collectors.toMap(
                    set -> TeamDto.from(set.getKey()),
                    Map.Entry::getValue
                )));
    }
}
