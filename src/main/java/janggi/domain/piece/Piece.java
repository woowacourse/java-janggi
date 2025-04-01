package janggi.domain.piece;

import janggi.domain.Board;
import janggi.domain.Team;
import janggi.domain.move.Position;
import java.util.Objects;
import java.util.Set;

public class Piece {

    private final Team team;
    private final PieceBehavior pieceBehavior;

    public Piece(Team team, PieceBehavior pieceBehavior) {
        this.team = team;
        this.pieceBehavior = pieceBehavior;
    }

    public boolean isSameSide(Team compareTeam) {
        return team.isSameSide(compareTeam);
    }

    public boolean isGeneralOnSameTeam(Team compareTeam) {
        return team.isSameSide(compareTeam) && pieceBehavior.isGeneral();
    }

    public boolean isCannon() {
        return pieceBehavior.isCannon();
    }

    public int toScore() {
        return pieceBehavior.toScore();
    }

    public Set<Position> getAvailableMovePositions(Board board, Position currentPosition) {
        BoardPositionInfo boardPositionInfo = new BoardPositionInfo(board, currentPosition, team);

        return pieceBehavior.generateAvailableMovePositions(boardPositionInfo);
    }

    public String toName() {
        return team.toName(pieceBehavior);
    }

    public Team getTeam() {
        return team;
    }

    public String getName() {
        return pieceBehavior.toName();
    }

    @Override
    public int hashCode() {
        return Objects.hash(team, pieceBehavior);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Piece piece = (Piece) o;
        return team == piece.team && Objects.equals(pieceBehavior, piece.pieceBehavior);
    }
}
