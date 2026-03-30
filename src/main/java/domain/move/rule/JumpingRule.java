package domain.move.rule;

import domain.board.Intersection;
import domain.game.Side;
import domain.piece.AlivePieces;
import domain.piece.Piece;
import domain.move.Path;
import domain.piece.PieceType;
import java.util.List;

public final class JumpingRule implements MoveRule {

    // TODO 필터링 메서드를 유틸로 분리해서 가독성 좋게 네이밍 고민
    public List<Intersection> movableDestinations(
            Side side,
            List<Path> candidatePaths,
            AlivePieces alivePieces
    ) {
        return candidatePaths.stream()
                .filter(path -> {
                    List<Piece> screens = path.passingIntersections()
                            .stream()
                            .filter(alivePieces::isNotEmpty)
                            .map(alivePieces::placedAt)
                            .toList();

                    return screens.size() == 1 && screens.getFirst().isNotSameType(PieceType.CANNON);
                })
                .map(Path::destination)
                .filter(destination -> {
                    if (alivePieces.isEmpty(destination)) {
                        return true;
                    }

                    Piece placedAt = alivePieces.placedAt(destination);

                    return placedAt.hasDifferentSide(side) && placedAt.isNotSameType(PieceType.CANNON);
                })
                .toList();
    }
}
