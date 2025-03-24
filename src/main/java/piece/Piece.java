package piece;

import direction.Point;
import move.MovementRule;

public class Piece {

    private final String nickname;
    private final MovementRule movementRule;
    private Point currentPosition;

    public Piece(String nickname, Point currentPosition, MovementRule movementRule) {
        this.nickname = nickname;
        this.movementRule = movementRule;
        this.currentPosition = currentPosition;
    }

    public String getNickName() {
        return nickname;
    }

    public Point getPosition() {
        return currentPosition;
    }

    public boolean isEqualPositionWith(Point targetPoint) {
        return currentPosition.equals(targetPoint);
    }

    public void move(Pieces allPieces, Point to) {
        movementRule.validateDestination(currentPosition, to);
        movementRule.checkPaths(allPieces, currentPosition, to);
        currentPosition = to;
    }

    public boolean isSameType(String targetNickname) {
        return nickname.equalsIgnoreCase(targetNickname);
    }
}
