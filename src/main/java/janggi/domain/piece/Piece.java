package janggi.domain.piece;

import janggi.domain.Path;
import janggi.domain.Position;
import janggi.domain.Space;
import janggi.domain.Team;
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
            throw new IllegalArgumentException("이동하려는 위치에 같은 팀의 말이 존재합니다.");
        }
    }

    public void validateRoutes(List<Piece> pieces) {
        if (!pieces.isEmpty()) {
            throw new IllegalArgumentException("이동 경로 사이에 다른 말이 있으면 안됩니다.");
        }
    }

    public boolean isEqualTeam(Team team) {
        return this.team == team;
    }

    @Override
    public boolean isBlank() {
        return false;
    }

    @Override
    public String displayValue() {
        return pieceType.getName();
    }

    public boolean isSameType(PieceType pieceType) {
        return this.pieceType == pieceType;
    }
}
