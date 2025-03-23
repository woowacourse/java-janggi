package janggi.domain;

import static janggi.domain.Team.BLUE;
import static janggi.domain.Team.RED;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Turn {
    private static final int VALID_TEAM_SIZE = 2;
    private static final int TURN_STEP = 1;

    private final List<Team> teams;

    private Turn(final List<Team> teams) {
        validateTeamSize(teams);
        this.teams = teams;
    }

    public static Turn initialize() {
        return new Turn(new ArrayList<>(List.of(BLUE, RED)));
    }

    private void validateTeamSize(List<Team> teams) {
        if (isInvalidSize(teams)) {
            throw new IllegalStateException("올바른 팀 사이즈가 아닙니다.");
        }
    }

    private boolean isInvalidSize(List<Team> teams) {
        return teams.size() != VALID_TEAM_SIZE;
    }

    public Team getCurrentTurn() {
        return teams.getFirst();
    }

    public void changeTurn() {
        Collections.rotate(teams, TURN_STEP);
    }
}
