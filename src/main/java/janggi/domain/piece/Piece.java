package janggi.domain.piece;

import janggi.domain.board.Point;
import janggi.domain.camp.Camp;
import janggi.domain.piece.type.MoveType;
import janggi.domain.piece.type.PieceType;
import java.util.Set;

public abstract class Piece {

    private final Camp camp;

    public Piece(Camp camp) {
        this.camp = camp;
    }

    public abstract void validateMovementRule(MoveType moveType, Point from, Point to);

    public abstract void validateRouteObstacles(Set<Piece> piecesOnRoute);

    public abstract Set<Point> findRoute(MoveType moveType, Point from, Point to);

    public abstract PieceType getPieceType();

    public void validateCatch(Piece targetPiece) {
        if (this.camp == targetPiece.camp) {
            throw new IllegalArgumentException("같은 진영의 기물을 잡을 수 없습니다.");
        }
    }

    public void validateSelect(Camp baseCamp) {
        if (this.camp != baseCamp) {
            throw new IllegalArgumentException("다른 진영의 기물을 선택할 수 없습니다.");
        }
    }

    protected boolean isSameCamp(Camp other) {
        return this.camp == other;
    }

    public Camp getCamp() {
        return camp;
    }
}
