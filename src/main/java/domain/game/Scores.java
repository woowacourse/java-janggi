package domain.game;

import java.util.Map;

public class Scores {
    private final Map<Team, Score> scores;

    public Scores(Map<Team, Score> scores) {
        this.scores = scores;
    }

    public Score get(Team team) {
        return scores.get(team);
    }
}
