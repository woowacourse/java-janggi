package domain.piece;

import domain.Team;
import java.util.List;

public abstract class Piece {

    private final Team team;

    public Piece(Team team) {
        this.team = team;
    }

    public abstract List<Move> calculatePath(int startRow, int startColumn, int targetRow, int targetColumn);

    public Team getTeam() {
        return team;
    }
}
