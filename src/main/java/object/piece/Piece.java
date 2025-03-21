package object.piece;

import object.Coordinate;
import object.Route;
import object.strategy.MoveStrategy;

public class Piece {

    private final Team team;
    private final MoveStrategy moveStrategy;
    private final Coordinate currentPosition;

    public Piece(Team team, MoveStrategy moveStrategy, Coordinate currentPosition) {
        this.team = team;
        this.moveStrategy = moveStrategy;
        this.currentPosition = currentPosition;
    }

    public Piece move(Coordinate destination, Pieces onRoutePieces) {
        Coordinate movedPosition = moveStrategy.move(destination, onRoutePieces, team);
        return new Piece(this.team, this.moveStrategy, movedPosition);
    }

    public Route getLegalRoute(Coordinate destination) {
        return moveStrategy.getLegalRoute(this.currentPosition, destination, team);
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

    public boolean isSameType(PieceType comparePieceType) {
        return moveStrategy.getPieceType().equals(comparePieceType);
    }

    public Team getTeam() {
        return team;
    }

    public PieceType getPieceType() {
        return moveStrategy.getPieceType();
    }
}
