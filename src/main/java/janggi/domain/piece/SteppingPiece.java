package janggi.domain.piece;

import janggi.domain.Delta;
import janggi.domain.Position;
import janggi.domain.side.TeamType;

import java.util.Collections;
import java.util.List;

public abstract class SteppingPiece extends Piece{

    public SteppingPiece(TeamType teamType, PieceType pieceType) {
        super(teamType, pieceType);
    }

    @Override
    public void validateCanMove(List<Piece> piecesInPath) {
        if (!piecesInPath.isEmpty()) {
            throw new IllegalArgumentException("이동 경로에 기물이 존재하여 이동할 수 없습니다.");
        }
    }

    @Override
    public List<Position> getPiecePositionsInPath(Position start, Position end) {
        checkMovePath(start, end);
        return Collections.emptyList();
    }

    private void checkMovePath(Position start, Position end) {
        Delta dxDelta = start.calculateDelta(end);

        getPaths().stream()
                .filter(path -> path.matches(dxDelta))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("이동할 수 없는 위치입니다."));
    }
}
