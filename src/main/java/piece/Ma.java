package piece;

import static pieceProperty.PieceType.MA;

import java.util.List;
import pieceProperty.PieceType;
import pieceProperty.Position;
import pieceProperty.Positions;
import view.ErrorMessage;

public class Ma extends Piece {

    public Ma(final Position position) {
        super(position);
    }

    @Override
    public void canMoveTo(final Position destination) {
        if (isInvalidMaMove(destination)) {
            throw new IllegalArgumentException(ErrorMessage.formatMessage("마가 움직일 수 없는 위치입니다."));
        }
    }

    @Override
    public Positions makeRoute(final Position destination) {
        Positions route = new Positions(List.of());

        int dRow = getPosition().calculateDRow(destination);
        int dCol = getPosition().calculateDCol(destination);

        if (getPosition().isUpLeftUp(dRow, dCol)) {
            route.addPosition(getPosition().calculateUpMovement());
        }

        if (getPosition().isUpRightUp(dRow, dCol)) {
            route.addPosition(getPosition().calculateUpMovement());
        }

        if (getPosition().isRightRightUp(dRow, dCol)) {
            route.addPosition(getPosition().calculateRightMovement());
        }

        if (getPosition().isRightRightDown(dRow, dCol)) {
            route.addPosition(getPosition().calculateRightMovement());
        }

        if (getPosition().isDownRightDown(dRow, dCol)) {
            route.addPosition(getPosition().calculateDownMovement());
        }

        if (getPosition().isDownLeftDown(dRow, dCol)) {
            route.addPosition(getPosition().calculateDownMovement());
        }

        if (getPosition().isLeftLeftUp(dRow, dCol)) {
            route.addPosition(getPosition().calculateLeftMovement());
        }

        if (getPosition().isLeftLeftDown(dRow, dCol)) {
            route.addPosition(getPosition().calculateLeftMovement());
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
        return MA;
    }

    private boolean isInvalidMaMove(final Position destination) {
        return !getPosition().calculateUpRightUPMovement().equals(destination) &&
                !getPosition().calculateUpLeftUpMovement().equals(destination) &&
                !getPosition().calculateDownRightDownMovement().equals(destination) &&
                !getPosition().calculateDownLeftDownMovement().equals(destination) &&
                !getPosition().calculateRightRightUpMovement().equals(destination) &&
                !getPosition().calculateRightRightDownMovement().equals(destination) &&
                !getPosition().calculateLeftLeftUpMovement().equals(destination) &&
                !getPosition().calculateLeftLeftDownMovement().equals(destination);
    }

}
