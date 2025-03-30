package piece;

import game.Board;
import java.util.HashSet;
import position.Movement;
import position.Path;
import position.Position;

import java.util.List;
import java.util.Set;

public class Soldier extends Piece {
    private static final Set<List<Movement>> ChoPieceMovements = Set.of(
            List.of(Movement.UP),
            List.of(Movement.LEFT),
            List.of(Movement.RIGHT));
    private static final Set<List<Movement>> HanPieceMovements = Set.of(
            List.of(Movement.DOWN),
            List.of(Movement.LEFT),
            List.of(Movement.RIGHT));
    private static final Set<List<Movement>> ChoPalaceMovements = Set.of(
            List.of(Movement.UP_LEFT),
            List.of(Movement.UP_RIGHT)
    );
    private static final Set<List<Movement>> HanPalaceMovements = Set.of(
            List.of(Movement.DOWN_LEFT),
            List.of(Movement.DOWN_RIGHT)
    );
    public Soldier(final Country country) {
        super(PieceType.SOLDIER, country);
    }

    @Override
    public Path findPathForMove(Position fromPosition, Position toPosition) {

        Set<List<Movement>> combinedMovements = addPalaceMovementIfInPalace(fromPosition, toPosition);
        return combinedMovements .stream()
                .map(fromPosition::findMovablePath)
                .filter(path -> !path.isEmpty() && path.isDestination(toPosition))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당 위치로 이동할 수 없습니다."))
                .withoutLast();
    }

    private Set<List<Movement>> addPalaceMovementIfInPalace(final Position fromPosition, final Position toPosition) {
        Set<List<Movement>> combinedMovements = new HashSet<>(movementsByCountry());
        if (fromPosition.isCenterOfPalace() || toPosition.isCenterOfPalace()) {
            combinedMovements.addAll(palaceMovementsByCountry());
        }
        return combinedMovements;
    }

    private Set<List<Movement>> movementsByCountry() {
        return (this.getCountry() == Country.CHO) ? ChoPieceMovements : HanPieceMovements;
    }

    private Set<List<Movement>> palaceMovementsByCountry() {
        return (this.getCountry() == Country.CHO) ? ChoPalaceMovements : HanPalaceMovements;
    }

    @Override
    public void validatePath(final Path path, Board board) {
        path.validateNoObstacles(board);
    }
}
