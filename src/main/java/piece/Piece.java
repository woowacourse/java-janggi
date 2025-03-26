package piece;

import java.util.List;
import java.util.Objects;
import move.JanggiMoveBehavior;
import piece.player.Team;
import piece.position.JanggiPosition;

public class Piece {

    private final Team team;
    private final JanggiMoveBehavior moveBehavior;
    private JanggiPosition position;

    public Piece(JanggiPosition position, JanggiMoveBehavior moveBehavior, Team team) {
        this.position = position;
        this.moveBehavior = moveBehavior;
        this.team = team;
    }

    public void move(Pieces onRoutePieces, JanggiPosition movePosition) {
        this.position = moveBehavior.moveOnRoute(movePosition, onRoutePieces, team);
    }

    public List<JanggiPosition> calculateLegalRoute(JanggiPosition selectPiecePosition, JanggiPosition movePosition) {
        return moveBehavior.calculateLegalRoute(selectPiecePosition, movePosition, team);
    }

    public boolean isSamePosition(JanggiPosition destination) {
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

    public JanggiPosition position() {
        return position;
    }

    public PieceType type() {
        return moveBehavior.getPieceType();
    }

    public PieceScore pieceScore() {
        return moveBehavior.getPieceScore();
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
