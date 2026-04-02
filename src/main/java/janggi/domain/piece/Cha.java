package janggi.domain.piece;

import janggi.domain.Board;
import janggi.domain.Delta;
import janggi.domain.MovePath;
import janggi.domain.Position;
import janggi.domain.side.TeamType;

import java.util.List;

public class Cha extends SlidingPiece {

    private static final List<MovePath> PATHS = List.of(
            new MovePath(List.of(Delta.UP)),
            new MovePath(List.of(Delta.DOWN)),
            new MovePath(List.of(Delta.LEFT)),
            new MovePath(List.of(Delta.RIGHT))
    );

    public Cha(TeamType teamType) {
        super(teamType, PieceType.CHA);
    }

    @Override
    protected List<MovePath> getPaths() {
        return PATHS;
    }

    @Override
    protected void validatePieceInPath(MovePath movePath, Position start, Position end, Board board) {
        if (movePath.intermediatePositions(start, end).stream()
                .anyMatch(board::hasPiece)) {
            throw new IllegalArgumentException("이동 경로에 기물이 존재하여 이동할 수 없습니다.");
        }
    }
}
