package janggi.domain.piece;

import janggi.domain.Delta;
import janggi.domain.Palace;
import janggi.domain.Position;
import janggi.domain.movepath.FixedMovePath;
import janggi.domain.movepath.MovePathStrategy;
import janggi.domain.team.TeamType;
import janggi.dto.MoveRoute;
import java.util.List;
import java.util.Optional;

public class Jol implements Piece {

    private final TeamType teamType;
    private final PieceType pieceType;
    private final List<MovePathStrategy> paths;
    private final Palace palace;

    public Jol(TeamType teamType) {
        this.teamType = teamType;
        pieceType = PieceType.JOL;
        paths = createPaths();
        palace = new Palace();
    }

    @Override
    public boolean isValidMovePattern(int startX, int startY, int endX, int endY) {
        return findMovePath(startX, startY, endX, endY).isPresent();
    }

    @Override
    public Optional<MovePathStrategy> findMovePath(int startX, int startY, int endX, int endY) {
        int dx = endX - startX;
        int dy = endY - startY;
        if (isSamePosition(dx, dy)) {
            return Optional.empty();
        }
        Optional<MovePathStrategy> normalPath = paths.stream()
            .filter(path -> path.matches(dx, dy))
            .findFirst();
        if (normalPath.isPresent()) {
            return normalPath;
        }
        return palace.findForwardDiagonalStepPath(
            new Position(startX, startY),
            new Position(endX, endY),
            teamType
        );
    }

    @Override
    public boolean canMove(MoveRoute moveRoute) {
        return true;
    }

    private boolean isSamePosition(int distanceX, int distanceY) {
        return distanceX == 0 && distanceY == 0;
    }

    private List<MovePathStrategy> createPaths() {
        if (teamType == TeamType.HAN) {
            return List.of(
                new FixedMovePath(List.of(Delta.createDown())),
                new FixedMovePath(List.of(Delta.createLeft())),
                new FixedMovePath(List.of(Delta.createRight()))
            );
        }
        return List.of(
            new FixedMovePath(List.of(Delta.createUp())),
            new FixedMovePath(List.of(Delta.createLeft())),
            new FixedMovePath(List.of(Delta.createRight()))
        );
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

    @Override
    public int getScore() {
        return pieceType.getScore();
    }
}
