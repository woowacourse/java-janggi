package piece;

import static pieceProperty.Movement.calculateDownLeftDownLeftDownMovement;
import static pieceProperty.Movement.calculateDownRightDownRightDownMovement;
import static pieceProperty.Movement.calculateLeftLeftDownLeftDownMovement;
import static pieceProperty.Movement.calculateLeftLeftUpLeftUpMovement;
import static pieceProperty.Movement.calculateRightRightDownRightDownMovement;
import static pieceProperty.Movement.calculateRightRightUpRightUpMovement;
import static pieceProperty.Movement.calculateUpLeftUpLeftUpMovement;
import static pieceProperty.Movement.calculateUpRightUpRightUpMovement;
import static pieceProperty.Movement.isDownLeftDownLeftDown;
import static pieceProperty.Movement.isDownRightDownRightDown;
import static pieceProperty.Movement.isLeftLeftDownLeftDown;
import static pieceProperty.Movement.isLeftLeftUpLeftUp;
import static pieceProperty.Movement.isRightRightDownRightDown;
import static pieceProperty.Movement.isRightUpRightUpRight;
import static pieceProperty.Movement.isUpLeftUpLeftUp;
import static pieceProperty.Movement.isUpRightUpRightUp;
import static pieceProperty.PieceType.SANG;

import java.util.List;
import pieceProperty.Movement;
import pieceProperty.PieceType;
import pieceProperty.Position;
import pieceProperty.Positions;
import view.ErrorMessage;

public class Sang extends Piece {

    public Sang(final Position position) {
        super(position);
    }

    @Override
    public void canMoveTo(final Position destination) {
        if (isInvalidSangMove(destination)) {
            throw new IllegalArgumentException(ErrorMessage.formatMessage("상이 움직일 수 없는 위치입니다."));
        }
    }

    @Override
    public Positions makeRoute(final Position destination) {
        Positions route = new Positions(List.of());

        int dRow = getPosition().calculateDRow(destination);
        int dCol = getPosition().calculateDCol(destination);

        if (isUpLeftUpLeftUp(dRow, dCol)) {
            route.addPosition(Movement.calculateUpMovement(getPosition()));
            route.addPosition(Movement.calculateUpLeftUpMovement(getPosition()));
        }

        if (isUpRightUpRightUp(dRow, dCol)) {
            route.addPosition(Movement.calculateUpMovement(getPosition()));
            route.addPosition(Movement.calculateUpRightUPMovement(getPosition()));
        }

        if (isRightUpRightUpRight(dRow, dCol)) {
            route.addPosition(Movement.calculateRightMovement(getPosition()));
            route.addPosition(Movement.calculateRightRightUpMovement(getPosition()));
        }

        if (isRightRightDownRightDown(dRow, dCol)) {
            route.addPosition(Movement.calculateRightMovement(getPosition()));
            route.addPosition(Movement.calculateRightRightDownMovement(getPosition()));
        }

        if (isDownLeftDownLeftDown(dRow, dCol)) {
            route.addPosition(Movement.calculateDownMovement(getPosition()));
            route.addPosition(Movement.calculateDownLeftDownMovement(getPosition()));
        }

        if (isDownRightDownRightDown(dRow, dCol)) {
            route.addPosition(Movement.calculateDownMovement(getPosition()));
            route.addPosition(Movement.calculateDownRightDownMovement(getPosition()));
        }

        if (isLeftLeftUpLeftUp(dRow, dCol)) {
            route.addPosition(Movement.calculateLeftMovement(getPosition()));
            route.addPosition(Movement.calculateLeftLeftUpMovement(getPosition()));
        }

        if (isLeftLeftDownLeftDown(dRow, dCol)) {
            route.addPosition(Movement.calculateLeftMovement(getPosition()));
            route.addPosition(Movement.calculateLeftLeftDownMovement(getPosition()));
        }

        return route;
    }

    @Override
    public boolean isKing() {
        return false;
    }

    @Override
    public boolean isPo() {
        return false;
    }

    @Override
    public PieceType getPieceType() {
        return SANG;
    }

    private boolean isInvalidSangMove(final Position destination) {
        return !calculateUpRightUpRightUpMovement(getPosition()).equals(destination)
                && !calculateUpLeftUpLeftUpMovement(getPosition()).equals(destination)
                && !calculateRightRightUpRightUpMovement(getPosition()).equals(destination)
                && !calculateRightRightDownRightDownMovement(getPosition()).equals(destination)
                && !calculateDownRightDownRightDownMovement(getPosition()).equals(destination)
                && !calculateDownLeftDownLeftDownMovement(getPosition()).equals(destination)
                && !calculateLeftLeftUpLeftUpMovement(getPosition()).equals(destination)
                && !calculateLeftLeftDownLeftDownMovement(getPosition()).isSameRow(destination);
    }
}
