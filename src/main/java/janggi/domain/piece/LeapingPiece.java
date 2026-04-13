package janggi.domain.piece;

import janggi.domain.Delta;
import janggi.domain.MovePath;
import janggi.domain.Position;
import janggi.domain.team.TeamType;

import java.util.List;

public abstract class LeapingPiece extends Piece{

    public LeapingPiece(TeamType teamType, PieceType pieceType) {
        super(teamType, pieceType);
    }

    @Override
    public List<Position> getPiecePositionsInPath(Position start, Position end) {
        MovePath movePath = findMovePath(start, end);
        return movePath.intermediatePositions(start, end);
    }

    @Override
    public void validateCanMove(List<Piece> piecesInPath) {
        if (!piecesInPath.isEmpty()) {
            throw new IllegalArgumentException("이동 경로에 기물이 존재하여 이동할 수 없습니다.");
        }
    }

    private MovePath findMovePath(Position start, Position end) {
        Delta dxDelta = start.calculateDelta(end);

        return getPaths().stream()
                .filter(path -> path.matches(dxDelta))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("이동할 수 없는 위치입니다."));
    }
}
