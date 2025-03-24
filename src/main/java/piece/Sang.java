package piece;

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

        int dRow = position.calculateDRow(destination);
        int dCol = position.calculateDCol(destination);

        if (Movement.isUpLeftUpLeftUp(dRow, dCol)) {
            route.addPosition(Movement.calculateUpMovement(position));
            route.addPosition(Movement.calculateUpLeftUpMovement(position));
        }

        if (Movement.isUpRightUpRightUp(dRow, dCol)) {
            route.addPosition(Movement.calculateUpMovement(position));
            route.addPosition(Movement.calculateUpRightUPMovement(position));
        }

        if (Movement.isRightUpRightUpRight(dRow, dCol)) {
            route.addPosition(Movement.calculateRightMovement(position));
            route.addPosition(Movement.calculateRightRightUpMovement(position));
        }

        if (Movement.isRightRightDownRightDown(dRow, dCol)) {
            route.addPosition(Movement.calculateRightMovement(position));
            route.addPosition(Movement.calculateRightRightDownMovement(position));
        }

        if (Movement.isDownLeftDownLeftDown(dRow, dCol)) {
            route.addPosition(Movement.calculateDownMovement(position));
            route.addPosition(Movement.calculateDownLeftDownMovement(position));
        }

        if (Movement.isDownRightDownRightDown(dRow, dCol)) {
            route.addPosition(Movement.calculateDownMovement(position));
            route.addPosition(Movement.calculateDownRightDownMovement(position));
        }

        if (Movement.isLeftLeftUpLeftUp(dRow, dCol)) {
            route.addPosition(Movement.calculateLeftMovement(position));
            route.addPosition(Movement.calculateLeftLeftUpMovement(position));
        }

        if (Movement.isLeftLeftDownLeftDown(dRow, dCol)) {
            route.addPosition(Movement.calculateLeftMovement(position));
            route.addPosition(Movement.calculateLeftLeftDownMovement(position));
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

    private boolean isInvalidSangMove(Position destination) {
        return !Movement.calculateUpRightUpRightUpMovement(position).equals(destination)
                && !Movement.calculateUpLeftUpLeftUpMovement(position).equals(destination)
                && !Movement.calculateRightRightUpRightUpMovement(position).equals(destination)
                && !Movement.calculateRightRightDownRightDownMovement(position).equals(destination)
                && !Movement.calculateDownRightDownRightDownMovement(position).equals(destination)
                && !Movement.calculateDownLeftDownLeftDownMovement(position).equals(destination)
                && !Movement.calculateLeftLeftUpLeftUpMovement(position).equals(destination)
                && !Movement.calculateLeftLeftDownLeftDownMovement(position).isSameRow(destination);
    }
}
