package janggi.domain.game;

import janggi.domain.Team;

public class PlayingTurn {

    private static final int MAX_ROUND = 30;

    private Team current;
    private int round;

    public PlayingTurn() {
        this(Team.CHO, 1);
    }

    public PlayingTurn(Team current, int round) {
        this.current = current;
        this.round = round;
    }

    public void toss() {
        this.round++;
        current = Team.values()[this.round % 2];
    }

    public boolean isEnded() {
        return round > MAX_ROUND;
    }

    public Team currentTeam() {
        return this.current;
    }

    public int currentRound() {
        return round;
    }
}
