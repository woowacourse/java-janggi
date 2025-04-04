package domain.piece;

import domain.board.Board;
import domain.movement.Movement;
import domain.position.Position;
import domain.position.PositionFactory;
import validator.DirectionCheckable;
import validator.ObstructionCheckable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiPredicate;

public class Cannon extends Piece implements DirectionCheckable, ObstructionCheckable {

    private static final int EXPECTED_INTERNAL_POSITION_COUNT = 1;
    private static final int CANNON_SCORE = 7;

    public Cannon(final Position position, final Country country) {
        super(position, country);
    }

    @Override
    public void validateMoveCondition(Position src, Position dest, Board board) {
        validateDirection(src, dest);
        List<Position> allPositions = getAllPositions(dest);
        validateExistNode(allPositions);
        List<Position> internalPositions = getInternalPositions(allPositions);
        validateNonObstruction(board, internalPositions);

        List<Position> existPositions = board.findExistPositions(internalPositions);
        Piece findPiece = board.getPieceBy(existPositions.getFirst());
        if (this.equalsType(findPiece)) {
            throw new IllegalArgumentException("포는 포를 뛰어 넘을 수 없습니다.");
        }
    }

    private static List<Position> getInternalPositions(List<Position> allPositions) {
        List<Position> internalPositions = new ArrayList<>(allPositions);
        internalPositions.removeFirst();
        internalPositions.removeLast();
        return internalPositions;
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
    public int getScore() {
        return CANNON_SCORE;
    }
}
