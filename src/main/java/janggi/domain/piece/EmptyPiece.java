package janggi.domain.piece;

import janggi.domain.path.Path;
import janggi.domain.path.PieceOnPath;
import janggi.domain.position.Movement;
import janggi.domain.team.Team;

public class EmptyPiece implements Piece {

    @Override
    public boolean isEmptyPiece() {
        return true;
    }

    @Override
    public boolean isSameTeam(Team team) {
        return team == Team.NONE;
    }

    @Override
    public boolean isSameType(PieceType type) {
        return getType() == type;
    }

    @Override
    public double getScore() {
        return 0.0;
    }

    @Override
    public Team getTeam() {
        return Team.NONE;
    }

    @Override
    public PieceType getType() {
        return PieceType.EMPTY;
    }

    @Override
    public Path getPath(Movement movement) {
        throw new IllegalArgumentException("[ERROR] 선택된 기물이 없습니다.");
    }

    @Override
    public void validateCanMove(PieceOnPath pieceOnPath, Piece endPiece) {
        throw new IllegalArgumentException("[ERROR] 빈 기물은 이동할 수 없습니다.");
    }
}
