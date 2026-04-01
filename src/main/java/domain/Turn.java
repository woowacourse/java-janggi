package domain;

import domain.board.BoardErrorMessage;
import domain.piece.Team;

public record Turn(
        Team turnOwnTeam
) {
    public Turn passTurn() {
        if (Team.CHO == turnOwnTeam) {
            return new Turn(Team.HAN);
        }

        if (Team.HAN == turnOwnTeam) {
            return new Turn(Team.CHO);
        }

        throw new IllegalStateException(BoardErrorMessage.UNDEFINED_TEAM_ERROR.getMessage());
    }
}
