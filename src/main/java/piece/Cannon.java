package piece;

import board.Board;
import movement.Movement;
import position.Position;
import position.PositionFactory;
import validator.DirectionCheckable;
import validator.ObstructionCheckable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiPredicate;

public class Cannon extends Piece implements DirectionCheckable, ObstructionCheckable {

    private static final int EXPECTED_INTERNAL_POSITION_COUNT = 1;

    public Cannon(final Position position, final Country country) {
        super(position, country);
    }

    @Override
    public void validateMoveCondition(Position src, Position dest, Board board) {
        validateDirection(src, dest);
        List<Position> allPositions = getAllPositions(dest);
        validateExistNode(allPositions);
        validateNonObstruction(board, allPositions);

        List<Position> existPositions = board.findExistPositions(allPositions); // TODO 2025. 3. 29. 20:53: allPosition에는 src, dest도 포함되어 있음
        Piece findPiece = board.getPieceBy(existPositions.getFirst());
        if (this.equalsType(findPiece)) {
            throw new IllegalArgumentException("포는 포를 뛰어 넘을 수 없습니다.");
        }
    }

    private void validateNonObstruction(Board board, List<Position> internalPositions) {
        validateObstruction(board, internalPositions, EXPECTED_INTERNAL_POSITION_COUNT);
    }

    private void validateExistNode(List<Position> allPositions) {
        for (int i = 0; i < allPositions.size() - 1; i++) {
            PositionFactory.validateAdjacentPositionBy(allPositions.get(i), allPositions.get(i + 1));
        }
    }

    private List<Position> getAllPositions(Position destination) {
        Movement movement = Movement.findByPositions(position, destination);

        List<Position> positions = new ArrayList<>();
        Position buffer = position;
        positions.add(buffer);
        while (buffer.x() != destination.x() || buffer.y() != destination.y()) {
            buffer = buffer.move(movement);
            positions.add(buffer);
        }
        return positions;
    }

    @Override
    public BiPredicate<Position, Position> directionRule() {
        return (src, dest) -> src.isSameLine(dest) || src.isDiagonal(dest);
    }

    @Override
    public boolean equalsType(final Piece piece) {
        return piece instanceof Cannon;
    }

    @Override
    protected int getScore() {
        return 7;
    }
}
