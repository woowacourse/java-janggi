package janggi.domain.piece;

import janggi.domain.Board;
import janggi.domain.Delta;
import janggi.domain.MovePath;
import janggi.domain.Position;
import janggi.domain.team.TeamType;
import java.util.List;
import java.util.Optional;

public class Sa implements Piece {

    private final TeamType teamType;
    private final PieceType pieceType;
    private final List<MovePath> paths;

    public Sa(TeamType teamType) {
        this.teamType = teamType;
        pieceType = PieceType.SA;
        paths = List.of(
            new MovePath(List.of(Delta.createUp())),
            new MovePath(List.of(Delta.createDown())),
            new MovePath(List.of(Delta.createLeft())),
            new MovePath(List.of(Delta.createRight())),
            new MovePath(List.of(Delta.createRightUp())),
            new MovePath(List.of(Delta.createRightDown())),
            new MovePath(List.of(Delta.createLeftUp())),
            new MovePath(List.of(Delta.createLeftDown()))
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
        int distanceX = Math.abs(dx);
        int distanceY = Math.abs(dy);
        if (isSamePosition(distanceX, distanceY)) {
            return Optional.empty();
        }
        if (!isOneStep(distanceX, distanceY)) {
            return Optional.empty();
        }
        return paths.stream()
            .filter(path -> path.matches(dx, dy))
            .findFirst();
    }

    @Override
    public boolean isObstaclesNotExist(Position start, Position end, Board board) {
        return true;
    }

    private boolean isSamePosition(int distanceX, int distanceY) {
        return distanceX == 0 && distanceY == 0;
    }

    private boolean isOneStep(int distanceX, int distanceY) {
        return distanceX <= 1 && distanceY <= 1;
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
