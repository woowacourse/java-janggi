package domain.game;

import domain.piece.Piece;
import domain.piece.Team;

public class Turn {
    private final Team team;

    public Turn(Team team) {
        this.team = team;
    }

    public boolean belongsTo(Piece piece) {
        //TODO: 삭제 또는 부정표현으로 변경
        return piece.isOwnedBy(team);
    }

    public Turn changeTeam() {
        return new Turn(team.enemy());
    }

    public Team getTeam() {
        return team;
    }
}
