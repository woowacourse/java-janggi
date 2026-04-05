package domain.piece;

import domain.ErrorMessage;
import domain.Offset;
import domain.board.Position;

import java.util.List;
import java.util.Objects;

public abstract class Piece {
    private final PieceType pieceType;
    private final Team team;

    public Piece(PieceType pieceType, Team team) {
        this.pieceType = pieceType;
        this.team = team;
    }

    public final List<Offset> getPathOffset(Position from, Position to) {
        validateMoveRule(from, to);
        return generatePaths(from, to);
    }

    public void validateMove(List<Piece> blockedPieces) {
        if (!blockedPieces.isEmpty()) {
            throw new IllegalStateException(ErrorMessage.PATH_BLOCKED.getMessage());
        }
    }

    protected abstract void validateMoveRule(Position from, Position to);

    protected abstract List<Offset> generatePaths(Position from, Position to);

    public boolean isSameTeam(Team team) {
        return this.team == team;
    }

    public boolean isSameTeam(Piece another) {
        return isSameTeam(another.team);
    }

    public boolean isSameType(PieceType type) {
        return this.pieceType == type;
    }

    public PieceType getPieceType() {
        return pieceType;
    }

    public Team getTeam() {
        return team;
    }
}
