package domain.piece.noPathPiece;

import domain.Coordinate;
import domain.Movement;
import domain.Team;
import domain.board.PieceSearcher;
import domain.piece.Piece;
import java.util.HashSet;
import java.util.Set;

public class Byeong extends NoPathPiece {

    public Byeong(Coordinate coordinate) {
        super(
            Team.HAN,
            coordinate,
            Set.of(Movement.DOWN, Movement.LEFT, Movement.RIGHT)
        );
    }

    @Override
    public boolean canMove(final Coordinate arrival, final PieceSearcher pieceSearcher) {
        final var movements = new HashSet<>(movements());
        movements.removeIf(Movement::isUpDirection);

        return movements.stream()
            .filter(coordinate::canMove)
            .map(coordinate::move)
            .anyMatch(arrival::equals);
    }

    @Override
    public Piece moveTo(final Coordinate arrival) {
        return new Byeong(arrival);
    }
}
