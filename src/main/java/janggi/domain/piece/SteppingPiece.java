package janggi.domain.piece;

import janggi.domain.Board;
import janggi.domain.MovePath;
import janggi.domain.Position;
import janggi.domain.side.TeamType;

import java.util.List;

public abstract class SteppingPiece extends Piece{

    public SteppingPiece(TeamType teamType, PieceType pieceType) {
        super(teamType, pieceType);
    }

    @Override
    public void validateCanMove(Position start, Position end, Board board) {
        checkSamePosition(start, end);
        checkMovePath(start, end);
    }

    private void checkMovePath(Position start, Position end) {
        int dx = start.deltaX(end);
        int dy = start.deltaY(end);

        getPaths().stream()
                .filter(path -> path.matches(dx, dy))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("이동할 수 없는 위치입니다."));
    }
}
