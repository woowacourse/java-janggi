package janggi.domain;

import static janggi.domain.Team.BLUE;
import static janggi.domain.Team.RED;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Turn {
    private final List<Team> teams;

    private Turn(final List<Team> teams) {
        validateTeamSize(teams);
        this.teams = teams;
    }

    public static Turn initialize() {
        return new Turn(new ArrayList<>(List.of(BLUE, RED)));
    }

    private void validateTeamSize(final List<Team> teams) {
        if (isInvalidSize(teams)) {
            throw new IllegalStateException("올바른 팀 사이즈가 아닙니다.");
        }
    }

    private boolean isInvalidSize(final List<Team> teams) {
        return teams.size() != 2;
    }

    public Team getCurrentTurn() {
        return teams.getFirst();
    }

    public void changeTurn() {
        Collections.rotate(teams, 1);
    }
}
