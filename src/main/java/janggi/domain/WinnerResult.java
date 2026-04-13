package janggi.domain;

import janggi.domain.team.TeamType;

public record WinnerResult(
    TeamType winner,
    int winnerScore
) {
}
