package domain.movement.path;

import domain.Coordinate;
import domain.movement.InfiniteMoveVector;
import domain.movement.MoveUnit;
import domain.board.PieceSearcher;
import domain.Piece;
import java.util.Set;

public class PoMovement extends PathMovement {

    public PoMovement() {
        super(Set.of(
            new InfiniteMoveVector(MoveUnit.LEFT),
            new InfiniteMoveVector(MoveUnit.RIGHT),
            new InfiniteMoveVector(MoveUnit.UP),
            new InfiniteMoveVector(MoveUnit.DOWN)
        ));
    }

    @Override
    public boolean canMove(
        final Coordinate departure,
        final Coordinate arrival,
        final PieceSearcher pieceSearcher
    ) {
        final var path = findPath(departure, arrival);

        final var piecesInPath = pieceSearcher.findPiecesIn(path.coordinates());
        if (piecesInPath.size() != 1) {
            return false;
        }

        final var podari = piecesInPath.getFirst();
        if (podari.isPo()) {
            return false;
        }

        final var isArrivalPo = pieceSearcher.findAt(arrival)
            .map(Piece::isPo)
            .orElse(false);
        return !isArrivalPo;
    }
}
