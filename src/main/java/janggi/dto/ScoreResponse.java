package janggi.dto;

import janggi.domain.common.Team;
import java.util.Map;

public class ScoreResponse {
    private final String choName;
    private final String hanName;
    private final double choScore;
    private final double hanScore;

    private ScoreResponse(String choName, String hanName, double choScore, double hanScore) {
        this.choName = choName;
        this.hanName = hanName;
        this.choScore = choScore;
        this.hanScore = hanScore;
    }

    public static ScoreResponse from(Map<Team, Double> teamScores) {
        TeamResponse cho = TeamResponse.from(Team.CHO);
        TeamResponse han = TeamResponse.from(Team.HAN);

        return new ScoreResponse(cho.getName(), han.getName(), teamScores.get(Team.CHO), teamScores.get(Team.HAN));
    }

    public String getChoName() {
        return choName;
    }

    public String getHanName() {
        return hanName;
    }

    public double getChoScore() {
        return choScore;
    }

    public double getHanScore() {
        return hanScore;
    }
}
