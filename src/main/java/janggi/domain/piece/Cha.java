package janggi.domain.piece;

import janggi.domain.Board;
import janggi.domain.Delta;
import janggi.domain.MovePath;
import janggi.domain.Position;
import janggi.domain.side.TeamType;
import java.util.List;
import java.util.Optional;

public class Cha implements Piece {

    private final TeamType teamType;
    private final PieceType pieceType;
    private final List<MovePath> paths;

    public Cha(TeamType teamType) {
        this.teamType = teamType;
        pieceType = PieceType.CHA;
        paths = List.of(
            new MovePath(List.of(Delta.createUp())),
            new MovePath(List.of(Delta.createDown())),
            new MovePath(List.of(Delta.createLeft())),
            new MovePath(List.of(Delta.createRight()))
        );
    }

    @Override
    public boolean isValidMovePattern(int startX, int startY, int endX, int endY) {
        return findMovePath(startX, startY, endX, endY).isPresent();
    }

    @Override
    public Optional<MovePath> findMovePath(int startX, int startY, int endX, int endY) {
        if (isSamePosition(startX, startY, endX, endY)) {
            return Optional.empty();
        }
        if (!isStraightDirection(startX, startY, endX, endY)) {
            return Optional.empty();
        }
        int dx = endX - startX;
        int dy = endY - startY;
        return paths.stream()
            .filter(path -> path.matchesDirection(dx, dy))
            .findFirst();
    }

    @Override
    public boolean isObstaclesNotExist(Position start, Position end, Board board) {
        Optional<MovePath> movePath = findMovePath(start.getX(), start.getY(), end.getX(), end.getY());
        if (movePath.isEmpty()) {
            return false;
        }
        return movePath.get().intermediatePositions(start, end).stream()
            .noneMatch(board::hasPiece);
    }

    private boolean isSamePosition(int startX, int startY, int endX, int endY) {
        return startX == endX && startY == endY;
    }

    private boolean isStraightDirection(int startX, int startY, int endX, int endY) {
        return startX == endX || startY == endY;
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
