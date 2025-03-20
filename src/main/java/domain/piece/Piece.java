package domain.piece;

import domain.Position;
import domain.TeamType;
import java.util.List;
import java.util.function.Predicate;

public abstract class Piece {
    protected Position position;
    protected final TeamType teamType;

    protected Piece(Position position, TeamType teamType) {
        this.position = position;
        this.teamType = teamType;
    }

    protected Piece(Piece piece) {
        this.position = piece.position;
        this.teamType = piece.teamType;
    }

    public void moveTo(Position position) {
        this.position = position;
    }

    public boolean hasSamePosition(Position position) {
        return this.position.equals(position);
    }

    public boolean isSameTeam(Piece piece) {
        return this.teamType.equals(piece.teamType);
    }

    public boolean isSameTeam(TeamType teamType) {
        return this.teamType.equals(teamType);
    }

    public boolean isSameType(PieceType pieceType) {
        return this.getType().equals(pieceType);
    }

    protected boolean hasNotTeamAtPosition(Position expectedPosition, List<Piece> alivePieces,
                                        Predicate<Piece> predicate) {
        boolean hasTeamAtPosition = alivePieces.stream()
                .anyMatch(piece -> piece.hasSamePosition(expectedPosition) && (piece.isSameTeam(this) || predicate.test(
                        piece)));
        return !hasTeamAtPosition;
    }

    public abstract boolean canMove(Position expectedPosition, List<Piece> pieces);

    public Position getPosition() {
        return position;
    }

    public abstract PieceType getType();

    public abstract Piece newInstance();
}
