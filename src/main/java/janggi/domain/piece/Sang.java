package janggi.domain.piece;

import janggi.domain.Board;
import janggi.domain.Delta;
import janggi.domain.MovePath;
import janggi.domain.Position;
import janggi.domain.side.TeamType;
import java.util.List;
import java.util.Optional;

public class Sang implements Piece {

    private final TeamType teamType;
    private final PieceType pieceType;
    private final List<MovePath> paths;

    public Sang(TeamType teamType) {
        this.teamType = teamType;
        pieceType = PieceType.SANG;
        paths = List.of(
            new MovePath(List.of(Delta.createUp(), Delta.createRightUp(), Delta.createRightUp())),
            new MovePath(List.of(Delta.createUp(), Delta.createLeftUp(), Delta.createLeftUp())),
            new MovePath(List.of(Delta.createDown(), Delta.createRightDown(), Delta.createRightDown())),
            new MovePath(List.of(Delta.createDown(), Delta.createLeftDown(), Delta.createLeftDown())),
            new MovePath(List.of(Delta.createLeft(), Delta.createLeftUp(), Delta.createLeftUp())),
            new MovePath(List.of(Delta.createLeft(), Delta.createLeftDown(), Delta.createLeftDown())),
            new MovePath(List.of(Delta.createRight(), Delta.createRightUp(), Delta.createRightUp())),
            new MovePath(List.of(Delta.createRight(), Delta.createRightDown(), Delta.createRightDown()))
        );
    }

    @Override
    public void validateCanMove(Position start, Position end, Board board) {
        if (!isValidMovePattern(start, end)) {
            throw new IllegalArgumentException("이동할 수 없는 위치입니다.");
        }

        if (!isObstaclesNotExist(start, end, board)) {
            throw new IllegalArgumentException("이동 경로에 기물이 존재하여 이동할 수 없습니다.");
        }
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
        int dx = end.getX() - start.getX();
        int dy = end.getY() - start.getY();
        return paths.stream()
            .filter(path -> path.matches(dx, dy))
            .findFirst();
    }

    private boolean isObstaclesNotExist(Position start, Position end, Board board) {
        Optional<MovePath> movePath = findMovePath(start, end);
        if (movePath.isEmpty()) {
            return false;
        }
        return movePath.get().intermediatePositions(start, end).stream()
            .noneMatch(board::hasPiece);
    }
}
