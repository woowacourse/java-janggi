package janggi.domain.piece;

import janggi.domain.Delta;
import janggi.domain.movepath.FixedMovePath;
import janggi.domain.movepath.MovePathStrategy;
import janggi.domain.team.TeamType;
import janggi.dto.MoveRoute;
import java.util.List;
import java.util.Optional;

public class Sang implements Piece {

    private final TeamType teamType;
    private final PieceType pieceType;
    private final List<MovePathStrategy> paths;

    public Sang(TeamType teamType) {
        this.teamType = teamType;
        pieceType = PieceType.SANG;
        paths = List.of(
            new FixedMovePath(List.of(Delta.createUp(), Delta.createRightUp(), Delta.createRightUp())),
            new FixedMovePath(List.of(Delta.createUp(), Delta.createLeftUp(), Delta.createLeftUp())),
            new FixedMovePath(List.of(Delta.createDown(), Delta.createRightDown(), Delta.createRightDown())),
            new FixedMovePath(List.of(Delta.createDown(), Delta.createLeftDown(), Delta.createLeftDown())),
            new FixedMovePath(List.of(Delta.createLeft(), Delta.createLeftUp(), Delta.createLeftUp())),
            new FixedMovePath(List.of(Delta.createLeft(), Delta.createLeftDown(), Delta.createLeftDown())),
            new FixedMovePath(List.of(Delta.createRight(), Delta.createRightUp(), Delta.createRightUp())),
            new FixedMovePath(List.of(Delta.createRight(), Delta.createRightDown(), Delta.createRightDown()))
        );
    }

    @Override
    public boolean isValidMovePattern(int startX, int startY, int endX, int endY) {
        return findMovePath(startX, startY, endX, endY).isPresent();
    }

    @Override
    public Optional<MovePathStrategy> findMovePath(int startX, int startY, int endX, int endY) {
        int dx = endX - startX;
        int dy = endY - startY;
        return paths.stream()
            .filter(path -> path.matches(dx, dy))
            .findFirst();
    }

    @Override
    public boolean canMove(MoveRoute moveRoute) {
        return moveRoute.intermediatePieceTypes().isEmpty();
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
