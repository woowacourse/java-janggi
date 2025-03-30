package piece;

import game.Board;
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

    public Soldier(final Country country) {
        super(PieceType.SOLDIER, country);
    }

    @Override
    public Path findPathForMove(Position fromPosition, Position toPosition) {
        Set<List<Movement>> pieceMovements = movementsByCountry();
        return pieceMovements.stream()
                .map(fromPosition::findMovablePath)
                .filter(path -> !path.isEmpty() && path.isDestination(toPosition))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당 위치로 이동할 수 없습니다."))
                .withoutLast();
    }

    private Set<List<Movement>> movementsByCountry() {
        return (this.getCountry() == Country.CHO) ? ChoPieceMovements : HanPieceMovements;
    }

    @Override
    public void validatePath(final Path path, Board board) {
        path.validateNoObstacles(board);
    }
}
