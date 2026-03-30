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
    public boolean isValidMovePattern(int startX, int startY, int endX, int endY) {
        return findMovePath(startX, startY, endX, endY).isPresent();
    }

    @Override
    public Optional<MovePath> findMovePath(int startX, int startY, int endX, int endY) {
        int dx = endX - startX;
        int dy = endY - startY;
        return paths.stream()
            .filter(path -> path.matches(dx, dy))
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

    @Override
    public String nickname() {
        return pieceType.getNickname();
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
