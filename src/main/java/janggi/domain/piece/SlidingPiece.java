package janggi.domain.piece;

import janggi.domain.Delta;
import janggi.domain.MovePath;
import janggi.domain.Position;
import janggi.domain.side.TeamType;

import java.util.List;

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

        return getPaths().stream()
                .filter(path -> path.matchesDirection(dxDelta))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("이동할 수 없는 위치입니다."));
    }
}
