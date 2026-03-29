package domain.piece;

import domain.ErrorMessage;
import domain.Offset;
import domain.Path;
import domain.board.Position;

import java.util.List;

public class GuardStrategy implements MoveStrategy {
    @Override
    public List<Offset> getPathPositions(Offset offset) {

        int dx = offset.dx();
        int dy = offset.dy();

        if (!((Math.abs(dx) == 1 && Math.abs(dy) == 0) || (Math.abs(dx) == 0 && Math.abs(dy) == 1))) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_MOVE_RULE.getMessage());
        }
        return List.of();
    }

    @Override
    public void canMove(List<Path> paths, Piece to) {
        if (!paths.isEmpty()) {
            throw new IllegalArgumentException(ErrorMessage.PATH_BLOCKED.getMessage());
        }
    }
}
