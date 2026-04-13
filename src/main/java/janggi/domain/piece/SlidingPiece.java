package janggi.domain.piece;

import janggi.domain.Delta;
import janggi.domain.MovePath;
import janggi.domain.Palace;
import janggi.domain.Position;
import janggi.domain.team.TeamType;

import java.util.List;
import java.util.stream.Stream;

public abstract class SlidingPiece extends Piece {

    public SlidingPiece(TeamType teamType, PieceType pieceType) {
        super(teamType, pieceType);
    }

    @Override
    public List<Position> getPiecePositionsInPath(Position start, Position end) {
        MovePath movePath = findMovePath(start, end);
        return movePath.intermediatePositions(start, end);
    }

    private MovePath findMovePath(Position start, Position end) {
        Delta dxDelta = start.calculateDelta(end);

        List<MovePath> pieceOriginPaths = getPaths();
        List<MovePath> palacePaths = Palace.findPossiblePaths(start);

        List<MovePath> allPossiblePaths = Stream.concat(pieceOriginPaths.stream(), palacePaths.stream())
                .toList();

        MovePath movePath = allPossiblePaths.stream()
                .filter(path -> path.matchesDirection(dxDelta))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("이동할 수 없는 위치입니다."));

        if (movePath.isDiagonal()) {
            validateDiagonalDistance(start, dxDelta);
        }

        return movePath;
    }

    private void validateDiagonalDistance(Position start, Delta dxDelta) {
        if (Palace.isCenter(start)) {
            if (dxDelta.isDiagonalOverOneStep()) {
                throw new IllegalArgumentException("이동할 수 없는 위치입니다.");
            }
        }

        if (Palace.isCorner(start)) {
            if (dxDelta.isDiagonalOverTwoSteps()) {
                throw new IllegalArgumentException("이동할 수 없는 위치입니다.");
            }
        }
    }
}
