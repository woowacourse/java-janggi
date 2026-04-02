package janggi.domain.piece;

import janggi.domain.Board;
import janggi.domain.Delta;
import janggi.domain.MovePath;
import janggi.domain.Position;
import janggi.domain.side.TeamType;

import java.util.List;

public class Sa extends Piece {

    private static final List<MovePath> PATHS = List.of(
            new MovePath(List.of(Delta.UP)),
            new MovePath(List.of(Delta.DOWN)),
            new MovePath(List.of(Delta.LEFT)),
            new MovePath(List.of(Delta.RIGHT)),
            new MovePath(List.of(Delta.RIGHT_UP)),
            new MovePath(List.of(Delta.RIGHT_DOWN)),
            new MovePath(List.of(Delta.LEFT_UP)),
            new MovePath(List.of(Delta.LEFT_DOWN))
    );

    public Sa(TeamType teamType) {
        super(teamType, PieceType.SA);
    }

    @Override
    public void validateCanMove(Position start, Position end, Board board) {
        checkSamePosition(start, end);
        checkMovePath(start, end);
    }

    private void checkMovePath(Position start, Position end) {
        int dx = start.deltaX(end);
        int dy = start.deltaY(end);

        PATHS.stream()
                .filter(path -> path.matches(dx, dy))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("이동할 수 없는 위치입니다."));
    }
}
