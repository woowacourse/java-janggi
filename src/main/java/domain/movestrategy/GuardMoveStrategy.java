package domain.movestrategy;

import domain.board.Board;
import domain.board.Direction;
import domain.board.Position;
import java.util.List;
import java.util.stream.Stream;

public class GuardMoveStrategy implements MoveStrategy {

    @Override
    public List<Position> getMovablePositions(final Board board, final Position from) {
        List<Position> orthogonalPositions = Direction.ORTHOGONAL_DIRECTIONS.stream()
                .map(from::move)
                .toList();

        List<Position> diagonalPositions = from.getDiagonalPositions();

        return Stream.concat(orthogonalPositions.stream(), diagonalPositions.stream())
                .filter(Position::isInsidePalace)
                .filter(position -> board.isEmptyOrOpposite(from, position))
                .toList();
    }
}
