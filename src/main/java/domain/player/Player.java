package domain.player;

import java.util.Objects;

public final class Player {
    private final Team team;
    private final int id;

    private Score score;
    private boolean isTurn;

    public Player(final int id, final Team team, final Score score, final boolean isTurn) {
        this.id = id;
        this.team = team;
        this.score = score;
        this.isTurn = isTurn;
    }

    public Player(final int id, final Team team) {
        this.id = validateId(id);
        this.team = Objects.requireNonNull(team, "Team 정보가 NULL일 수 없습니다.");
        this.isTurn = this.team.isFirst();
        this.score = Score.generateInitialScoreByTeam(team);
    }

    public static int validateId(final int value) {
        if (value <= 0) {
            throw new IllegalArgumentException("Player ID는 음수일 수 없습니다.");
        }
        return value;
    }

    public boolean isTurn() {
        return isTurn;
    }

    public void switchTurn() {
        this.isTurn = !isTurn;
    }

    public void addScore(final Score other) {
        this.score = score.add(other);
    }

    public int getId() {
        return id;
    }

    public Team getTeam() {
        return team;
    }

    public Score getScore() {
        return score;
    }
}
