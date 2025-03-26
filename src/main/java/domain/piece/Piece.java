package domain.piece;

import domain.position.Position;
import domain.piece.path.PathFinder;
import domain.piece.path.PathValidator;
import java.util.List;
import java.util.Map;

public abstract class Piece {
    protected final TeamType teamType;
    private final PathFinder pathFinder;
    private final PathValidator pathValidator;

    protected Piece(TeamType teamType, PathFinder pathFinder, PathValidator pathValidator) {
        this.teamType = teamType;
        this.pathFinder = pathFinder;
        this.pathValidator = pathValidator;
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

    public TeamType getTeamType() {
        return teamType;
    }

    public void validateCanMove(Position from, Position to, Map<Position, Piece> alivePieces) {
        List<Position> intermediatePositions = pathFinder.findIntermediatePositions(from, to);
        pathValidator.validatePath(this, to, intermediatePositions, alivePieces);
    }

    ;

    public abstract PieceType getType();
}
