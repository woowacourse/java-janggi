package object.piece;

import java.util.Objects;
import object.Coordinate;
import object.Route;
import object.strategy.MoveStrategy;

public class Piece {

    private final Team team;
    private final MoveRule moveRule;
    private final Coordinate currentPosition;

    public Piece(Team team, MoveStrategy moveStrategy, PieceType pieceType, Coordinate currentPosition) {
        this.currentPosition = currentPosition;
        this.moveRule = new MoveRule(moveStrategy, pieceType);
        this.team = team;
    }

    public Piece(Team team, MoveRule moveRule, Coordinate currentPosition) {
        this.currentPosition = currentPosition;
        this.moveRule = moveRule;
        this.team = team;
    }

    public Piece move(Coordinate destination, Pieces onRoutePieces) {
        Coordinate movedPosition = moveRule.move(destination, onRoutePieces, team);
        return new Piece(this.team, this.moveRule, movedPosition);
    }

    public Route getLegalRoute(Coordinate destination) {
        return moveRule.getLegalRoute(this.currentPosition, destination, team);
    }

    public boolean isSameTeam(Team moveTeam) {
        return team.equals(moveTeam);
    }

    public boolean isSameTeam(Piece comparePiece) {
        return isSameTeam(comparePiece.team);
    }

    public boolean isSamePosition(Coordinate destination) {
        return currentPosition.equals(destination);
    }

    public boolean isSamePosition(Piece comparePiece) {
        return isSamePosition(comparePiece.currentPosition);
    }

    public boolean isSameType(PieceType pieceType) {
        return moveRule.isSameType(pieceType);
    }

    public Team getTeam() {
        return team;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Piece piece = (Piece) o;
        return Objects.equals(currentPosition, piece.currentPosition) && Objects.equals(moveRule, piece.moveRule);
    }

    @Override
    public int hashCode() {
        return Objects.hash(currentPosition, moveRule);
    }
}
