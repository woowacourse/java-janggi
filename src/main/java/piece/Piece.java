package piece;

import direction.Point;
import move.MovementRule;

public class Piece {

    private final String nickname;
    private final MovementRule movementRule;
    private Point point;

    public Piece(String nickname, Point point, MovementRule movementRule) {
        this.nickname = nickname;
        this.movementRule = movementRule;
        this.point = point;
    }

    public String getName() {
        return nickname;
    }

    public Point getPosition() {
        return point;
    }

    public void move(Pieces allPieces, Point to) {
        point = movementRule.move(allPieces, point, to);
    }

    public boolean isSameType(String nickname) {
        return this.nickname.equalsIgnoreCase(nickname);
    }
}
