package piece;

import game.Board;
import java.util.List;
import java.util.Set;
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

    public List<Position> getPathForMoving(Position fromPosition, Position toPosition) {
        Set<List<Movement>> pieceMovements;
        if (this.getCountry() == Country.Cho) {
            pieceMovements = ChoPieceMovements;
        } else {
            pieceMovements = HanPieceMovements;
        }
        List<Position> pathPosition = pieceMovements.stream()
                .map(route -> fromPosition.findMovablePositions(route))
                .filter(path -> !path.isEmpty() && path.getLast().equals(toPosition))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당 위치로 이동할 수 없습니다."));
        return pathPosition.subList(0, pathPosition.size() - 1);
    }


    @Override
    public void validateRoute(final List<Position> positions, Board board) {
        if (positions.stream()
                .anyMatch(position -> board.getBoard().containsKey(position))) {
            throw new IllegalArgumentException("중간에 기물이 있어 갈 수 없습니다.");
        }
    }
}
