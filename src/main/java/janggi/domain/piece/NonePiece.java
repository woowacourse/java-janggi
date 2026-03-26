package janggi.domain.piece;

import janggi.domain.BoardInterface;
import janggi.domain.Position;

import java.util.List;

public abstract class NonePiece implements Piece {
    protected final PieceType pieceType;

    public NonePiece(PieceType pieceType){
        this.pieceType = pieceType;
    }

    @Override
    public List<Position> findRoute(Position start, Position end) {
        return List.of();
    }

    @Override
    public boolean isMovable(List<Position> path, BoardInterface boardInterface) {
        return false;
    }

    @Override
    public boolean isPo() {
        return false;
    }
}
