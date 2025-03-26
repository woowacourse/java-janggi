package model.piece;

import java.util.List;
import model.Team;
import model.position.Position;

public abstract class Piece {

    private final Team team;

    public Piece(Team team) {
        this.team = team;
    }

    public void checkOfTurn(Team turn) {
        if (this.team.equals(turn)) {
            return;
        }
        throw new IllegalArgumentException("본인 팀의 턴이 아닙니다.");
    }

    public abstract List<Position> calculateAllDirection(Position departure, Position arrival);

    public abstract boolean isCannon();

    public abstract String getName();

    public boolean isSameTeam(Piece piece) {
        return this.team == piece.getTeam();
    }

    public Team getTeam() {
        return team;
    }
}
