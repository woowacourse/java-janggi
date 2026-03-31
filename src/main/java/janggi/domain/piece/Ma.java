package janggi.domain.piece;

import janggi.domain.Board;
import janggi.domain.Delta;
import janggi.domain.MovePath;
import janggi.domain.Position;
import janggi.domain.side.TeamType;
import java.util.List;
import java.util.Optional;

public class Ma implements Piece {

    private final TeamType teamType;
    private final PieceType pieceType;
    private final List<MovePath> paths;

    public Ma(TeamType teamType) {
        this.teamType = teamType;
        pieceType = PieceType.MA;
        this.paths = List.of(
            new MovePath(List.of(Delta.createUp(), Delta.createRightUp())),
            new MovePath(List.of(Delta.createUp(), Delta.createLeftUp())),
            new MovePath(List.of(Delta.createDown(), Delta.createRightDown())),
            new MovePath(List.of(Delta.createDown(), Delta.createLeftDown())),
            new MovePath(List.of(Delta.createLeft(), Delta.createLeftUp())),
            new MovePath(List.of(Delta.createLeft(), Delta.createLeftDown())),
            new MovePath(List.of(Delta.createRight(), Delta.createRightUp())),
            new MovePath(List.of(Delta.createRight(), Delta.createRightDown()))
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
    public boolean isValidMovePattern(Position start, Position end) {
        return findMovePath(start, end).isPresent();
    }

    @Override
    public Optional<MovePath> findMovePath(Position start, Position end) {
        int dx = end.getX() - start.getX();
        int dy = end.getY() - start.getY();
        return paths.stream()
            .filter(path -> path.matches(dx, dy))
            .findFirst();
    }

    public boolean isObstaclesNotExist(Position startPosition, Position endPosition, Board board) {
        Optional<MovePath> movePath = findMovePath(startPosition, endPosition);
        if (movePath.isEmpty()) {
            return false;
        }
        return movePath.get().intermediatePositions(startPosition, endPosition).stream()
            .noneMatch(board::hasPiece);
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
}
