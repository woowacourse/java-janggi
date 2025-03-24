package piece;

import java.util.List;
import java.util.Objects;
import move.MoveBehavior;
import piece.position.Position;

public class Piece {

    private final Team team;
    private final MoveBehavior moveBehavior;
    private Position position;

    public Piece(Position position, MoveBehavior moveBehavior, Team team) {
        this.position = position;
        this.moveBehavior = moveBehavior;
        this.team = team;
    }

    public void move(Pieces onRoutePieces, Position movePosition) {
        this.position = moveBehavior.move(movePosition, onRoutePieces, team);
    }

    public List<Position> calculateLegalRoute(Position selectPiecePosition, Position movePosition) {
        return moveBehavior.calculateLegalRoute(selectPiecePosition, movePosition, team);
    }

    public boolean isSamePosition(Position destination) {
        return position.equals(destination);
    }

    public boolean isSamePosition(Piece comparePiece) {
        return isSamePosition(comparePiece.position);
    }

    public boolean isSameType(PieceType pieceType) {
        return moveBehavior.isSameType(pieceType);
    }

    public boolean isSameTeam(Team moveTeam) {
        return team.equals(moveTeam);
    }

    public boolean isSameTeam(Piece comparePiece) {
        return isSameTeam(comparePiece.team);
    }

    public Team team() {
        return team;
    }

    public Position getPosition() {
        return position;
    }

    public PieceType getType() {
        return moveBehavior.getPieceType();
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
        return team == piece.team && Objects.equals(moveBehavior, piece.moveBehavior) && Objects.equals(
                position, piece.position);
    }

    @Override
    public int hashCode() {
        return Objects.hash(team, moveBehavior, position);
    }

    @Override
    public String toString() {
        return "Piece{" +
                "team=" + team +
                ", moveBehavior=" + moveBehavior +
                ", position=" + position +
                '}';
    }
}
