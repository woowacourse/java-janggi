package domain.piece;

import domain.piece.path.PathFinder;
import domain.piece.path.PathValidator;
import domain.position.Position;
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

    public boolean isSameTeam(TeamType teamType) {
        return this.teamType.equals(teamType);
    }

    public boolean isSameType(PieceType pieceType) {
        return this.getType().equals(pieceType);
    }

    public TeamType getTeamType() {
        return teamType;
    }

    public void validateCanMove(TeamType turn, Position from, Position to, Map<Position, Piece> alivePieces) {
        validateOwnPiece(turn);
        List<Position> intermediatePositions = pathFinder.findIntermediatePositions(from, to);
        pathValidator.validatePath(teamType, to, intermediatePositions, alivePieces);
    }

    public abstract PieceType getType();

    private void validateOwnPiece(TeamType turn) {
        if (!isSameTeam(turn)) {
            throw new IllegalArgumentException("본인 말만 움직일 수 있습니다.");
        }
    }
}
