package controller.dto;

import domain.Team;

public record CurrentScore(String teamName,
                           Integer score) {

    public static CurrentScore of(Team team, int score) {
        return new CurrentScore(team.getKoreanName(), score);
    }
}
