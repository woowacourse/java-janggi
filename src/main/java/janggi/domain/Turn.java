package janggi.domain;

import static janggi.domain.Team.BLUE;
import static janggi.domain.Team.RED;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Turn {
    private static final int VALID_TEAM_SIZE = 2;
    private static final int TURN_STEP = 1;
    private static final int MAX_GAME_TURN_COUNT = 30;

    private final List<Team> teams;
    private int turnCount = 1;

    private Turn(final List<Team> teams) {
        validateTeamSize(teams);
        this.teams = teams;
    }

    public static Turn initialize(final Team currentTeam) {
        if (currentTeam == BLUE) {
            return new Turn(new ArrayList<>(List.of(BLUE, RED)));
        }
        return new Turn(new ArrayList<>(List.of(RED, BLUE)));
    }

    private void validateTeamSize(final List<Team> teams) {
        if (isInvalidSize(teams)) {
            throw new IllegalStateException("올바른 팀 사이즈가 아닙니다.");
        }
    }

    private boolean isInvalidSize(final List<Team> teams) {
        return teams.size() != VALID_TEAM_SIZE;
    }

    public Team getCurrentTurn() {
        return teams.getFirst();
    }

    public void changeTurn() {
        addTurnCount();
        Collections.rotate(teams, TURN_STEP);
    }

    private void addTurnCount() {
        turnCount += 1;
    }

    public boolean isDraw() {
        return turnCount >= MAX_GAME_TURN_COUNT;
    }
}
