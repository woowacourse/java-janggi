package janggi.domain.piece;

import janggi.domain.Team;
import janggi.domain.path.Path;
import janggi.domain.position.Position;
import java.util.List;

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
    public Path getPath(Position from, Position to) {
        throw new IllegalArgumentException("[ERROR] 선택된 기물이 없습니다.");
    }

    @Override
    public boolean canMove(List<Piece> piecesOnPath, Piece endPiece) {
        return false;
    }
}
