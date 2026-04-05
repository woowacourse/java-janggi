package game;

import domain.Team;

public enum GameState {
    CHO_TURN(Team.CHO),
    HAN_TURN(Team.HAN),
    FINISHED(Team.NONE);

    private final Team team;

    GameState(Team team) {
        this.team = team;
    }

    public Team getTeam() {
        return team;
    }

    public GameState nextTurn(boolean isKingCaught) {
        if (isKingCaught) {
            return FINISHED;
        }
        if (this == CHO_TURN) {
            return HAN_TURN;
        }
        return CHO_TURN;
    }

    public boolean isTurnOf(Team team) {
        return team == this.team;
    }
}
