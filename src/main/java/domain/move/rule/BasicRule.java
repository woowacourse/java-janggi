package domain.move.rule;

import domain.board.Intersection;
import domain.game.Side;
import domain.piece.AlivePieces;
import domain.move.Path;
import java.util.List;

public final class BasicRule implements MoveRule {

    // TODO 필터링 메서드를 유틸로 분리해서 가독성 좋게 네이밍 고민
    public List<Intersection> movableDestinations(Side side, List<Path> candidatePaths, AlivePieces alivePieces) {
        return candidatePaths.stream()
                .filter(path -> path.passingIntersections()
                        .stream()
                        .allMatch(alivePieces::isEmpty))
                .map(Path::destination)
                .filter(destination -> alivePieces.placedNotSameSide(destination, side))
                .toList();
    }
}
