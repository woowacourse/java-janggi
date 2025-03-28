package piece;

import board.Board;
import position.Position;
import validator.DirectionCheckable;
import validator.ObstructionCheckable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiPredicate;

public class Cannon extends Piece implements DirectionCheckable, ObstructionCheckable {

    public Cannon(final Position position, final Country country) {
        super(position, country);
    }

    @Override
    public void validateMoveCondition(Position src, Position dest, Board board) {
        validateDirection(src, dest);
        List<Position> internalPositions = getInternalPositions(dest);
        validateObstruction(board, internalPositions, 1);

        List<Position> existPositions = board.findExistPositions(internalPositions);
        Piece findPiece = board.getPieceBy(existPositions.getFirst());
        if (this.equalsType(findPiece)) {
            throw new IllegalArgumentException("포는 포를 죽일 수 없습니다.");
        }
    }

    @Override
    public BiPredicate<Position, Position> directionRule() {
        return Position::isSameLine;
    }

    private List<Position> getInternalPositions(Position destination) {
        List<Position> positions = new ArrayList<>();
        for (int x = Math.min(position.x(), destination.x()); x <= Math.max(position.x(), destination.x()); x++) {
            for (int y = Math.min(position.y(), destination.y()); y <= Math.max(position.y(), destination.y()); y++) {
                positions.add(new Position(x, y));
            }
        }
        positions.removeLast();
        return positions;
    }

    @Override
    public boolean equalsType(final Piece piece) {
        return piece instanceof Cannon;
    }
}
