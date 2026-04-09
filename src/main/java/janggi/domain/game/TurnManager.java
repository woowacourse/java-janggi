package janggi.domain.game;

import janggi.domain.team.Team;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class TurnManager {

    private static final int MINIMUM_TEAMS_SIZE = 1;
    private static final int MAXIMUM_TEAMS_SIZE = 2;

    private final Deque<Team> teams;
    private int turnTaken;

    public TurnManager(final int turnTaken, final List<Team> teams) {
        validateTeamsSize(teams);
        this.teams = new LinkedList<>(teams);
        this.turnTaken = turnTaken;
    }

    public static TurnManager init(final Team blueTeam, final Team redTeam) {
        final int initialTurnsTaken = 1;
        return new TurnManager(initialTurnsTaken, List.of(blueTeam, redTeam));
    }

    private void validateTeamsSize(final List<Team> teams) {
        if (teams.size() < MINIMUM_TEAMS_SIZE || teams.size() > MAXIMUM_TEAMS_SIZE) {
            throw new IllegalArgumentException(
                String.format("전체 팀 개수는 %d 이상 %d 이하여야 합니다.", MINIMUM_TEAMS_SIZE,
                    MAXIMUM_TEAMS_SIZE));
        }
    }

    public Team getCurrentTeam() {
        return teams.getFirst();
    }

    public void progressToNext() {
        teams.addLast(teams.removeFirst());
        turnTaken++;
    }

    public List<Team> getTeams() {
        return teams.stream().toList();
    }

    public int getTurnTaken() {
        return turnTaken;
    }
}
