package piece;

import game.Board;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import position.Movement;
import position.Position;

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

    public List<Position> findPathForMove(Position fromPosition, Position toPosition) {
        Set<List<Movement>> pieceMovements = movementsByCountry();
        List<Position> path = pieceMovements.stream()
                .map(fromPosition::findMovablePositions)
                .filter(findPathByDestination(toPosition))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당 위치로 이동할 수 없습니다."));
        return path.subList(0, path.size() - 1);
    }

    private Set<List<Movement>> movementsByCountry() {
        Set<List<Movement>> pieceMovements;
        if (this.getCountry() == Country.CHO) {
            pieceMovements = ChoPieceMovements;
        } else {
            pieceMovements = HanPieceMovements;
        }
        return pieceMovements;
    }

    private static Predicate<List<Position>> findPathByDestination(final Position toPosition) {
        return path -> !path.isEmpty() && path.getLast().equals(toPosition);
    }

    @Override
    public void validatePath(final List<Position> positions, Board board) {
        if (positions.stream()
                .anyMatch(board::hasPieceAt)) {
            throw new IllegalArgumentException("중간에 기물이 있어 갈 수 없습니다.");
        }
    }

}
