package piece;

import static pieceProperty.PieceType.SANG;

import java.util.List;
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

        if (getPosition().isUpLeftUpLeftUp(dRow, dCol)) {
            route.addPosition(getPosition().calculateUpMovement());
            route.addPosition(getPosition().calculateUpLeftUpMovement());
        }

        if (getPosition().isUpRightUpRightUp(dRow, dCol)) {
            route.addPosition(getPosition().calculateUpMovement());
            route.addPosition(getPosition().calculateUpRightUPMovement());
        }

        if (getPosition().isRightUpRightUpRight(dRow, dCol)) {
            route.addPosition(getPosition().calculateRightMovement());
            route.addPosition(getPosition().calculateRightRightUpMovement());
        }

        if (getPosition().isRightRightDownRightDown(dRow, dCol)) {
            route.addPosition(getPosition().calculateRightMovement());
            route.addPosition(getPosition().calculateRightRightDownMovement());
        }

        if (getPosition().isDownLeftDownLeftDown(dRow, dCol)) {
            route.addPosition(getPosition().calculateDownMovement());
            route.addPosition(getPosition().calculateDownLeftDownMovement());
        }

        if (getPosition().isDownRightDownRightDown(dRow, dCol)) {
            route.addPosition(getPosition().calculateDownMovement());
            route.addPosition(getPosition().calculateDownRightDownMovement());
        }

        if (getPosition().isLeftLeftUpLeftUp(dRow, dCol)) {
            route.addPosition(getPosition().calculateLeftMovement());
            route.addPosition(getPosition().calculateLeftLeftUpMovement());
        }

        if (getPosition().isLeftLeftDownLeftDown(dRow, dCol)) {
            route.addPosition(getPosition().calculateLeftMovement());
            route.addPosition(getPosition().calculateLeftLeftDownMovement());
        }

        return route;
    }

    @Override
    public boolean isJanggun() {
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
        return !getPosition().calculateUpRightUpRightUpMovement().equals(destination) &&
                !getPosition().calculateUpLeftUpLeftUpMovement().equals(destination) &&
                !getPosition().calculateRightRightUpRightUpMovement().equals(destination) &&
                !getPosition().calculateRightRightDownRightDownMovement().equals(destination) &&
                !getPosition().calculateDownRightDownRightDownMovement().equals(destination) &&
                !getPosition().calculateDownLeftDownLeftDownMovement().equals(destination) &&
                !getPosition().calculateLeftLeftUpLeftUpMovement().equals(destination) &&
                !getPosition().calculateLeftLeftDownLeftDownMovement().equals(destination);
    }
}
