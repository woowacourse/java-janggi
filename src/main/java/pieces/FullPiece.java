package pieces;

import java.util.List;
import java.util.Objects;
import movepolicy.MoveContext;
import movepolicy.destination.DestinationRule;
import movepolicy.path.PathRule;
import participant.Turn;
import position.Position;

public abstract class FullPiece implements Piece {

    private final Side side;

    public FullPiece(Side side) {
        this.side = side;
    }

    @Override
    public final boolean isEmpty() {
        return false;
    }

    @Override
    public final MoveContext askMoveContext(Position departure, Position destination, Turn turn) {
        validateSide(turn);
        validateDestination(departure, destination);
        List<Position> pathPositions = getPathPositions(departure, destination);
        DestinationRule destinationRule = getDestinationRule();
        PathRule pathRule = getPathRule();
        return new MoveContext(pathPositions, destinationRule, pathRule);
    }

    private void validateSide(Turn turn) {
        if (!turn.isMatchSide(side)) {
            throw new IllegalArgumentException("다른 진영의 말은 이동시킬 수 없습니다.");
        }
    }

    protected abstract void validateDestination(Position departure, Position destination);

    protected abstract List<Position> getPathPositions(Position departure, Position destination);

    protected abstract DestinationRule getDestinationRule();

    protected abstract PathRule getPathRule();

    public abstract boolean isPo();

    protected final boolean isHan() {
        return side.isHan();
    }

    protected final boolean isCho() {
        return side.isCho();
    }

    public final boolean isSameSide(FullPiece destinationPiece) {
        if (isHan() && destinationPiece.isHan()) {
            return true;
        }
        return isCho() && destinationPiece.isCho();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FullPiece other = (FullPiece) o;
        return side == other.side;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(side);
    }

    public boolean equalsSide(Turn turn) {
        return turn.isMatchSide(side);
    }
}
