package janggi.domain.piece;

import janggi.domain.Delta;
import janggi.domain.MovePath;
import janggi.domain.side.TeamType;
import java.util.List;

public class Jol implements Piece {

    private final TeamType teamType;
    private final PieceType pieceType;
    private final List<MovePath> paths;

    public Jol(TeamType teamType) {
        this.teamType = teamType;
        pieceType = PieceType.JOL;
        paths = createPaths();
    }

    @Override
    public boolean canMove(int startX, int startY, int endX, int endY) {
        int distanceX = endX - startX;
        int distanceY = endY - startY;
        if (isSamePosition(distanceX, distanceY)) {
            return false;
        }
        if (isHorizontalMove(distanceX, distanceY)) {
            return true;
        }
        return isForwardMove(distanceX, distanceY);
    }

    private boolean isSamePosition(int distanceX, int distanceY) {
        return distanceX == 0 && distanceY == 0;
    }

    private boolean isHorizontalMove(int distanceX, int distanceY) {
        return Math.abs(distanceX) == 1 && distanceY == 0;
    }

    private boolean isForwardMove(int distanceX, int distanceY) {
        if (distanceX != 0) {
            return false;
        }
        if (teamType == TeamType.HAN) {
            return distanceY == -1;
        }
        return distanceY == 1;
    }

    private List<MovePath> createPaths() {
        if (teamType == TeamType.HAN) {
            return List.of(
                new MovePath(List.of(Delta.createDown())),
                new MovePath(List.of(Delta.createLeft())),
                new MovePath(List.of(Delta.createRight()))
            );
        }
        return List.of(
            new MovePath(List.of(Delta.createUp())),
            new MovePath(List.of(Delta.createLeft())),
            new MovePath(List.of(Delta.createRight()))
        );
    }

    @Override
    public String nickname() {
        return pieceType.getNickname();
    }
}
