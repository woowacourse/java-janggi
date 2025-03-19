package domain.piece;

import domain.Board;
import domain.Coordinate;
import domain.Team;
import java.util.Set;

public abstract class Piece {

    protected final Team team;

    public Piece(Team team) {
        this.team = team;
    }

    public final boolean canMove(Board board, Coordinate departure, Coordinate arrival) {
        if (!findMovableCandidates(departure).contains(arrival)) {
            return false;
        }
        if (!canMoveConsideringObstacles(board, departure, arrival)) {
            return false;
        }
        return true;
    }

    protected abstract Set<Coordinate> findMovableCandidates(Coordinate departure);

    protected abstract boolean canMoveConsideringObstacles(Board board, Coordinate departure, Coordinate arrival);

    protected abstract Set<Coordinate> findPaths(Coordinate departure, Coordinate arrival);

    public final boolean isSameTeam(Piece piece) {
        return piece.team.equals(this.team);
    }

    public boolean isPo() {
        return this instanceof Po;
    }
}
