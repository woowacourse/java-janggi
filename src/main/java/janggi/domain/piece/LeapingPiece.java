package janggi.domain.piece;

import janggi.domain.Board;
import janggi.domain.MovePath;
import janggi.domain.Position;
import janggi.domain.side.TeamType;

public abstract class LeapingPiece extends Piece{

    public LeapingPiece(TeamType teamType, PieceType pieceType) {
        super(teamType, pieceType);
    }

    @Override
    public void validateCanMove(Position start, Position end, Board board) {
        checkSamePosition(start, end);
        MovePath movePath = findMovePath(start, end);
        validatePieceInPath(movePath, start, end, board);
    }

    private MovePath findMovePath(Position start, Position end) {
        int dx = start.deltaX(end);
        int dy = start.deltaY(end);

        return getPaths().stream()
                .filter(path -> path.matches(dx, dy))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("이동할 수 없는 위치입니다."));
    }

    private void validatePieceInPath(MovePath movePath, Position start, Position end, Board board) {
        if (movePath.intermediatePositions(start, end).stream()
                .anyMatch(board::hasPiece)) {
            throw new IllegalArgumentException("이동 경로에 기물이 존재하여 이동할 수 없습니다.");
        }
    }
}
