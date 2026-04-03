package janggi.domain.piece;

import janggi.domain.Team;
import janggi.domain.path.Path;
import janggi.domain.path.PieceOnPath;
import janggi.domain.position.Movement;

public class EmptyPiece implements Piece {

    @Override
    public boolean isEmptyPiece() {
        return true;
    }

    @Override
    public boolean isSamePiece(Piece other) {
        return other instanceof EmptyPiece;
    }

    @Override
    public PieceType getType() {
        return PieceType.EMPTY;
    }

    @Override
    public Team getTeam() {
        return Team.NONE;
    }

    @Override
    public Path getPath(Movement movement) {
        throw new IllegalArgumentException("[ERROR] 선택된 기물이 없습니다.");
    }

    @Override
    public void validateCanMove(PieceOnPath piecesOnPath, Piece endPiece) {
        throw new IllegalArgumentException("[ERROR] 빈 기물은 이동할 수 없습니다.");
    }
}
