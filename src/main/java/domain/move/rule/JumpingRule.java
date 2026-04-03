package domain.move.rule;

import domain.board.Intersection;
import domain.game.Side;
import domain.piece.AlivePieces;
import domain.move.Path;
import domain.piece.PieceType;
import java.util.List;

public final class JumpingRule implements MoveRule {

    public List<Intersection> movableDestinations(
            Side side,
            List<Path> candidatePaths,
            AlivePieces alivePieces
    ) {
        return candidatePaths.stream()
                .filter(path -> alivePieces.hasScreenExcept(path, PieceType.CANNON))
                .map(Path::destination)
                .filter(destination -> alivePieces.isEmpty(destination)
                        || (alivePieces.placedNotSameSide(destination, side)
                        && alivePieces.placedAt(destination).isNotSameType(PieceType.CANNON))
                )
                .toList();
    }
}
