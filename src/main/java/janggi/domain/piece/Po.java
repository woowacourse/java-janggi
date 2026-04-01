package janggi.domain.piece;

import janggi.domain.Board;
import janggi.domain.Delta;
import janggi.domain.MovePath;
import janggi.domain.Position;
import janggi.domain.side.TeamType;
import java.util.List;
import java.util.Optional;

public class Po implements Piece {

    private final TeamType teamType;
    private final PieceType pieceType;
    private final List<MovePath> paths;

    public Po(TeamType teamType) {
        this.teamType = teamType;
        pieceType = PieceType.PO;
        paths = List.of(
            new MovePath(List.of(Delta.createUp())),
            new MovePath(List.of(Delta.createDown())),
            new MovePath(List.of(Delta.createLeft())),
            new MovePath(List.of(Delta.createRight()))
        );
    }

    @Override
    public void validateCanMove(Position start, Position end, Board board) {
        if (!isValidMovePattern(start, end)) {
            throw new IllegalArgumentException("이동할 수 없는 위치입니다.");
        }

        validateObstacles(start, end, board);
    }

    @Override
    public String name() {
        return pieceType.getName();
    }

    @Override
    public PieceType getPieceType() {
        return pieceType;
    }

    @Override
    public TeamType getTeamType() {
        return teamType;
    }

    private boolean isValidMovePattern(Position start, Position end) {
        return findMovePath(start, end).isPresent();
    }

    private Optional<MovePath> findMovePath(Position start, Position end) {
        if (isSamePosition(start, end)) {
            return Optional.empty();
        }

        int startX = start.getX();
        int startY = start.getY();
        int endX = end.getX();
        int endY = end.getY();

        int dx = endX - startX;
        int dy = endY - startY;

        return paths.stream()
            .filter(path -> path.matchesDirection(dx, dy))
            .findFirst();
    }

    private void validateObstacles(Position start, Position end, Board board) {
        Optional<MovePath> movePath = findMovePath(start, end);
        if (movePath.isEmpty()) {
            throw new IllegalArgumentException("이동할 수 없는 위치입니다.");
        }
        List<Position> intermediatePositions = movePath.get().intermediatePositions(start, end);
        List<Piece> obstacles = intermediatePositions.stream()
                .map(board::findPiece)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
        if (obstacles.isEmpty()) {
            throw new IllegalArgumentException("이동 경로에 기물이 존재하지 않아 이동할 수 없습니다.");
        }
        if (obstacles.size() > 1) {
            throw new IllegalArgumentException("이동 경로에 기물이 1개 이상 존재합니다.");
        }
        if (obstacles.getFirst().getPieceType() == PieceType.PO) {
            throw new IllegalArgumentException("포는 포를 넘을 수 없습니다.");
        }
    }

    private boolean isSamePosition(Position start, Position end) {
        return start.isSamePosition(end);
    }
}
