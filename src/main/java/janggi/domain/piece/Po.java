package janggi.domain.piece;

import janggi.domain.Board;
import janggi.domain.Delta;
import janggi.domain.MovePath;
import janggi.domain.Position;
import janggi.domain.side.TeamType;
import java.util.List;
import java.util.Optional;

public class Po extends Piece {

    private static final List<MovePath> PATHS = List.of(
            new MovePath(List.of(Delta.UP)),
            new MovePath(List.of(Delta.DOWN)),
            new MovePath(List.of(Delta.LEFT)),
            new MovePath(List.of(Delta.RIGHT))
    );

    public Po(TeamType teamType) {
        super(teamType, PieceType.PO);
    }

    @Override
    public void validateCanMove(Position start, Position end, Board board) {
        MovePath movePath = findMovePath(start, end);
        validateObstacles(movePath, start, end, board);
    }

    private MovePath findMovePath(Position start, Position end) {
        if (isSamePosition(start, end)) {
            throw new IllegalArgumentException("출발지와 목적지가 동일합니다.");
        }

        int dx = start.deltaX(end);
        int dy = start.deltaY(end);

        return PATHS.stream()
                .filter(path -> path.matchesDirection(dx, dy))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("이동할 수 없는 위치입니다."));
    }

    private void validateObstacles(MovePath movePath, Position start, Position end, Board board) {
        List<Position> intermediatePositions = movePath.intermediatePositions(start, end);
        List<Piece> obstacles = intermediatePositions.stream()
                .map(board::findPiece)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
        if (obstacles.isEmpty()) {
            throw new IllegalArgumentException("이동 경로에 기물이 존재하지 않아 이동할 수 없습니다.");
        }
        if (obstacles.size() > 1) {
            throw new IllegalArgumentException("이동 경로에 기물이 2개 이상 존재합니다.");
        }
        if (obstacles.getFirst().getPieceType() == PieceType.PO) {
            throw new IllegalArgumentException("포는 포를 넘을 수 없습니다.");
        }
    }
}
