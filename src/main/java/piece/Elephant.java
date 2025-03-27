package piece;

import game.Board;
import java.util.List;
import java.util.Set;
import position.Movement;
import position.Position;

public class Elephant extends Piece {
    private static final Set<List<Movement>> pieceMovements = Set.of(
            List.of(Movement.UP, Movement.UP_UP_RIGHT, Movement.UP_UP_UP_RIGHT_RIGHT),
            List.of(Movement.UP, Movement.UP_UP_LEFT, Movement.UP_UP_UP_LEFT_LEFT),
            List.of(Movement.DOWN, Movement.DOWN_DOWN_RIGHT, Movement.DOWN_DOWN_DOWN_RIGHT_RIGHT),
            List.of(Movement.DOWN, Movement.DOWN_DOWN_LEFT, Movement.DOWN_DOWN_DOWN_LEFT_LEFT),
            List.of(Movement.RIGHT, Movement.UP_RIGHT_RIGHT, Movement.UP_UP_RIGHT_RIGHT_RIGHT),
            List.of(Movement.RIGHT, Movement.DOWN_RIGHT_RIGHT, Movement.DOWN_DOWN_RIGHT_RIGHT_RIGHT),
            List.of(Movement.LEFT, Movement.UP_LEFT_LEFT, Movement.UP_UP_LEFT_LEFT_LEFT),
            List.of(Movement.LEFT, Movement.DOWN_LEFT_LEFT, Movement.DOWN_DOWN_LEFT_LEFT_LEFT)
    );

    public Elephant(final Country country) {
        super(PieceType.ELEPHANT, country);
    }

    public List<Position> getPathForMoving(Position fromPosition, Position toPosition) {
        return pieceMovements.stream()
                .map(route -> fromPosition.findMovablePositions(route))
                .filter(path -> !path.isEmpty() && path.getLast().equals(toPosition))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당 위치로 이동할 수 없습니다."));

    }

    @Override
    public void validateRoute(final List<Position> positions, Board board) {
        if (positions.subList(0, positions.size() - 1).stream()
                .anyMatch(position -> board.getBoard().containsKey(position))) {
            throw new IllegalArgumentException("중간에 기물이 있어 갈 수 없습니다.");
        }
    }
}
