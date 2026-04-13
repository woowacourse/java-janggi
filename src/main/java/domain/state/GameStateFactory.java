package domain.state;

import domain.piece.Team;

public final class GameStateFactory {

    private GameStateFactory() {
    }

    public static GameState from(final String winner, final String currentTeam) {
        if (winner != null) {
            return new FinishedState(Team.valueOf(winner));
        }

        if (Team.CHO.name().equals(currentTeam)) {
            return new ChoPlayingState();
        }
        if (Team.HAN.name().equals(currentTeam)) {
            return new HanPlayingState();
        }
        throw new IllegalArgumentException("존재하지 않는 팀입니다. currentTeam=" + currentTeam);
    }
}
