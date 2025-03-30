package persistence;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import piece.player.Team;

class TeamAndScore {
    private final Team team;
    private int score;

    public TeamAndScore(Team team, int score) {
        this.team = team;
        this.score = score;
    }

    public Team getTeam() {
        return team;
    }

    public int getScore() {
        return score;
    }

    public void addScore(int score) {
        this.score += score;
    }
}

public class FakeJanggiTurnDao implements JanggiTurnDao {

    private final Map<Integer, Map<Team, TeamAndScore>> turnScores = new HashMap<>();

    @Override
    public void addTurnScore(Team team, int turn, int score) {
        Map<Team, TeamAndScore> turnScore = turnScores.getOrDefault(turn, new HashMap<>());

        turnScore.putIfAbsent(team, new TeamAndScore(team, 0));
        turnScore.get(team).addScore(score);

        turnScores.put(turn, turnScore);
    }

    @Override
    public Optional<Integer> findLatestTurnId() {
        if (turnScores.isEmpty()) {
            return Optional.empty();
        }
        return turnScores.keySet()
                .stream()
                .max(Integer::compareTo);
    }

    @Override
    public Optional<Integer> findLatestTurn() {
        return findLatestTurnId();
    }

    @Override
    public void deleteAll() {
        turnScores.clear();
    }
}
