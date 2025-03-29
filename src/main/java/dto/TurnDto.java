package dto;

import domain.game.Turn;
import domain.piece.Team;

public class TurnDto {
    private final Team team;

    public TurnDto(Team team) {
        this.team = team;
    }

    public Turn toTurn() {
        return new Turn(team);
    }
}
