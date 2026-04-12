package domain.move.rule;

import domain.board.Intersection;
import domain.board.Palace;
import domain.game.Side;
import domain.move.Path;
import domain.piece.AlivePieces;
import java.util.List;

public final class PalaceRule implements MoveRule {

    private final MoveRule baseRule = new BasicRule();

    @Override
    public List<Intersection> movableDestinations(
            Side side,
            List<Path> candidatePaths,
            AlivePieces alivePieces
    ) {
        return baseRule.movableDestinations(side, candidatePaths, alivePieces)
                .stream()
                .filter(intersection -> Palace.contains(intersection, side))
                .toList();
    }
}
