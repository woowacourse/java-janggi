package piece;

import board.Board;
import coordinate.Coordinate;
import java.util.Set;
import team.Team;

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

    public abstract String getName();

    public final boolean isSameTeam(Piece piece) {
        return piece.team.equals(this.team);
    }

    public final Team getTeam() {
        return team;
    }
}
