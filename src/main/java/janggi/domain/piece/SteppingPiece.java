package janggi.domain.piece;

import janggi.domain.Delta;
import janggi.domain.MovePath;
import janggi.domain.Palace;
import janggi.domain.Position;
import janggi.domain.team.TeamType;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

public abstract class SteppingPiece extends Piece{

    public SteppingPiece(TeamType teamType, PieceType pieceType) {
        super(teamType, pieceType);
    }

    @Override
    public List<Position> getPiecePositionsInPath(Position start, Position end) {
        checkMovePath(start, end);
        return Collections.emptyList();
    }

    @Override
    public void validateCanMove(List<Piece> piecesInPath) {
        if (!piecesInPath.isEmpty()) {
            throw new IllegalArgumentException("이동 경로에 기물이 존재하여 이동할 수 없습니다.");
        }
    }

    private void checkMovePath(Position start, Position end) {
        Delta dxDelta = start.calculateDelta(end);

        List<MovePath> pieceOriginPaths = getPaths();
        List<MovePath> palacePaths = Palace.findPossiblePaths(start);

        List<MovePath> allPossiblePaths = Stream.concat(pieceOriginPaths.stream(), palacePaths.stream())
                .toList();

        MovePath movePath = allPossiblePaths.stream()
                .filter(path -> path.matches(dxDelta))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("이동할 수 없는 위치입니다."));

        if (movePath.isDiagonal()) {
            validateDiagonalDirection(dxDelta);
        }
    }

    protected abstract void validateDiagonalDirection(Delta dxDelta);
}
