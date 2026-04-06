package janggi.domain.space.piece;

import janggi.domain.board.Path;
import janggi.domain.position.Position;
import janggi.domain.space.Space;
import java.util.List;

public abstract class Piece implements Space {

    private final Team team;
    private final PieceType pieceType;

    public Piece(Team team, PieceType pieceType) {
        this.team = team;
        this.pieceType = pieceType;
    }

    public abstract void validateMove(Position from, Position to);

    public abstract Path getPath(Position from, Position to);

    public void validateArrival(Space space) {
        if (space.isBlank()) {
            return;
        }

        Piece piece = (Piece) space;
        if (isEqualTeam(piece.team)) {
            throw new IllegalStateException("이동하려는 위치에 같은 팀의 말이 존재합니다.");
        }

        validateSpecificArrival(piece);
    }

    public void validateRoutes(List<Piece> pieces) {
        if (!pieces.isEmpty()) {
            throw new IllegalStateException("이동 경로 사이에 다른 말이 있으면 안됩니다.");
        }
    }

    public void verifyMove(Position from, Position to, List<Piece> blockedPieces, Space targetSpace) {
        validateMove(from, to);
        validateRoutes(blockedPieces);
        validateArrival(targetSpace);
    }

    public boolean isEqualTeam(Team team) {
        return this.team == team;
    }

    @Override
    public boolean isBlank() {
        return false;
    }

    public boolean isSameType(Piece piece) {
        return this.pieceType == piece.pieceType;
    }

    public boolean isSameType(PieceType pieceType) {
        return this.pieceType == pieceType;
    }

    public int getScore() {
        return pieceType.getScore();
    }

    public Team getTeam() {
        return team;
    }

    @Override
    public String toString() {
        return pieceType.toString();
    }

    protected void validateSpecificArrival(Piece piece) {
    }
}
