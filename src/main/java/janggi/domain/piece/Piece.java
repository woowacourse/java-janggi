package janggi.domain.piece;

import janggi.domain.board.BoardChecker;
import janggi.domain.board.Position;
import janggi.domain.piece.movement.Movement;
import janggi.domain.piece.movement.MovementFactory;
import java.util.List;
import java.util.Objects;

public class Piece {

    private final Camp camp;
    private final PieceType pieceType;
    private final Movement movement;

    public Piece(Camp camp, PieceType pieceType) {
        this.camp = camp;
        this.pieceType = pieceType;
        this.movement = MovementFactory.create(camp, pieceType);
    }

    public void validateMove(Position source, Position destination, BoardChecker board) {
        List<Position> path = movement.findPath(source, destination);
        movement.checkPath(path, camp, board);
    }

    public double score() {
        return pieceType.score();
    }

    public boolean isGeneral() {
        return pieceType.isGeneral();
    }

    public boolean isSameCamp(Camp camp) {
        return this.camp == camp;
    }

    public boolean isSamePieceType(PieceType pieceType) {
        return this.pieceType == pieceType;
    }

    public Camp getCamp() {
        return camp;
    }

    public PieceType getPieceType() {
        return pieceType;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Piece piece = (Piece) o;
        return getCamp() == piece.getCamp() && getPieceType() == piece.getPieceType();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCamp(), getPieceType());
    }
}
