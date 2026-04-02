package janggi.domain.piece;

import janggi.domain.Board;
import janggi.domain.Delta;
import janggi.domain.MovePath;
import janggi.domain.Position;
import janggi.domain.side.TeamType;

import java.util.List;

public class Cha extends Piece {

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
    public void validateCanMove(Position start, Position end, Board board) {
        checkSamePosition(start, end);
        MovePath movePath = findMovePath(start, end);
        validatePieceInPath(movePath, start, end, board);
    }

    private MovePath findMovePath(Position start, Position end) {
        int dx = start.deltaX(end); // deltaX 말고 더 알아듣기 쉬운 메서드명으로 수정 필요
        int dy = start.deltaY(end);

        return PATHS.stream()
                .filter(path -> path.matchesDirection(dx, dy))
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
