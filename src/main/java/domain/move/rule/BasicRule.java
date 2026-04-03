package domain.move.rule;

import domain.board.Intersection;
import domain.game.Side;
import domain.piece.AlivePieces;
import domain.move.Path;
import java.util.List;

public final class BasicRule implements MoveRule {

    public List<Intersection> movableDestinations(
            Side side,
            List<Path> candidatePaths,
            AlivePieces alivePieces
    ) {
        return candidatePaths.stream()
                .filter(alivePieces::isPassable)
                .map(Path::destination)
                .filter(destination -> alivePieces.placedNotSameSide(destination, side))
                .toList();
    }
}
