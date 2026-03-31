package janggi.domain.piece;

import janggi.domain.Team;
import janggi.domain.position.Position;
import java.util.List;

public class EmptyPiece implements Piece {

    private static final String PIECE_NAME = "빈";

    @Override
    public boolean isEmptyPiece() {
        return true;
    }

    @Override
    public boolean isSamePiece(Piece other) {
        return other instanceof EmptyPiece;
    }

    @Override
    public String getDisplayName() {
        return PIECE_NAME;
    }

    @Override
    public Team getTeam() {
        return Team.NONE;
    }

    @Override
    public List<Position> getPath(Position from, Position to) {
        throw new IllegalArgumentException("[ERROR] 선택된 기물이 없습니다.");
    }

    @Override
    public boolean canMove(List<Piece> piecesOnPath, Piece endPiece) {
        return false;
    }
}
