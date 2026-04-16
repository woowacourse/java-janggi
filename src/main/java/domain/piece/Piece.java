package domain.piece;

import domain.vo.Position;
import domain.path.Paths;
import domain.strategy.PieceMoveStrategy;
import java.util.List;

public abstract class Piece {

    private static final String PIECE_NOT_FOUND = "해당 위치에 기물이 존재하지 않습니다.";
    private static final String NOT_OWN_PIECE = "선택한 기물은 아군 기물이 아닙니다.";
    protected static final String CANNOT_CAPTURE_OWN_PIECE = "아군 기물은 잡을 수 없습니다.";
    protected static final String INVALID_TARGET_POSITION = "이동할 수 없는 목적지입니다.";

    protected final Side side;
    protected final PieceMoveStrategy strategy;
    protected final Paths paths;

    protected Piece(Side side, Paths paths, PieceMoveStrategy strategy) {
        this.side = side;
        this.paths = paths;
        this.strategy = strategy;
    }

    public void validateMovement(Side currentTurn, Piece targetPiece) {
        if (this instanceof Empty) {
            throw new IllegalArgumentException(PIECE_NOT_FOUND);
        }
        if (!side.equals(currentTurn)) {
            throw new IllegalArgumentException(NOT_OWN_PIECE);
        }

        checkTarget(targetPiece);
    }

    public void checkTarget(Piece piece) {
        if (piece.isSameSide(side)) {
            throw new IllegalArgumentException(CANNOT_CAPTURE_OWN_PIECE);
        }
    }

    public void checkRoute(List<Piece> pieces) {
        for (Piece piece : pieces) {
            if (!(piece instanceof Empty)) {
                throw new IllegalArgumentException(INVALID_TARGET_POSITION);
            }
        }
    }

    public boolean isSameSide(Side side) {
        return this.side == side;
    }

    public abstract List<Position> findRoute(Position sourcePosition, Position targetPosition);

    public abstract String getName();

    public abstract int getScore();
}
