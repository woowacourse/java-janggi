package janggi.domain.piece;

import janggi.domain.Delta;
import janggi.domain.Palace;
import janggi.domain.Position;
import janggi.domain.movepath.DirectionalMovePath;
import janggi.domain.movepath.MovePathStrategy;
import janggi.domain.team.TeamType;
import janggi.dto.MoveRoute;
import java.util.List;
import java.util.Optional;

public class Cha implements Piece {

    private final TeamType teamType;
    private final PieceType pieceType;
    private final List<MovePathStrategy> paths;
    private final Palace palace;

    public Cha(TeamType teamType) {
        this.teamType = teamType;
        pieceType = PieceType.CHA;
        paths = List.of(
            new DirectionalMovePath(List.of(Delta.createUp())),
            new DirectionalMovePath(List.of(Delta.createDown())),
            new DirectionalMovePath(List.of(Delta.createLeft())),
            new DirectionalMovePath(List.of(Delta.createRight()))
        );
        palace = new Palace();
    }

    @Override
    public boolean isValidMovePattern(int startX, int startY, int endX, int endY) {
        return findMovePath(startX, startY, endX, endY).isPresent();
    }

    @Override
    public Optional<MovePathStrategy> findMovePath(int startX, int startY, int endX, int endY) {
        if (isSamePosition(startX, startY, endX, endY)) {
            return Optional.empty();
        }
        if (isStraightDirection(startX, startY, endX, endY)) {
            int dx = endX - startX;
            int dy = endY - startY;
            return paths.stream()
                .filter(path -> path.matches(dx, dy))
                .findFirst();
        }

        return palace.findDiagonalMovePath(
            new Position(startX, startY),
            new Position(endX, endY)
        );
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

    private boolean isSamePosition(int startX, int startY, int endX, int endY) {
        return startX == endX && startY == endY;
    }

    private boolean isStraightDirection(int startX, int startY, int endX, int endY) {
        return startX == endX || startY == endY;
    }
}
