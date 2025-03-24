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

        int dRow = position.calculateDRow(destination);
        int dCol = position.calculateDCol(destination);

        if (isUpLeftUpLeftUp(dRow, dCol)) {
            route.addPosition(Movement.calculateUpMovement(position));
            route.addPosition(Movement.calculateUpLeftUpMovement(position));
        }

        if (isUpRightUpRightUp(dRow, dCol)) {
            route.addPosition(Movement.calculateUpMovement(position));
            route.addPosition(Movement.calculateUpRightUPMovement(position));
        }

        if (isRightUpRightUpRight(dRow, dCol)) {
            route.addPosition(Movement.calculateRightMovement(position));
            route.addPosition(Movement.calculateRightRightUpMovement(position));
        }

        if (isRightRightDownRightDown(dRow, dCol)) {
            route.addPosition(Movement.calculateRightMovement(position));
            route.addPosition(Movement.calculateRightRightDownMovement(position));
        }

        if (isDownLeftDownLeftDown(dRow, dCol)) {
            route.addPosition(Movement.calculateDownMovement(position));
            route.addPosition(Movement.calculateDownLeftDownMovement(position));
        }

        if (isDownRightDownRightDown(dRow, dCol)) {
            route.addPosition(Movement.calculateDownMovement(position));
            route.addPosition(Movement.calculateDownRightDownMovement(position));
        }

        if (isLeftLeftUpLeftUp(dRow, dCol)) {
            route.addPosition(Movement.calculateLeftMovement(position));
            route.addPosition(Movement.calculateLeftLeftUpMovement(position));
        }

        if (isLeftLeftDownLeftDown(dRow, dCol)) {
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

    private boolean isInvalidSangMove(final Position destination) {
        return !calculateUpRightUpRightUpMovement(position).equals(destination)
                && !calculateUpLeftUpLeftUpMovement(position).equals(destination)
                && !calculateRightRightUpRightUpMovement(position).equals(destination)
                && !calculateRightRightDownRightDownMovement(position).equals(destination)
                && !calculateDownRightDownRightDownMovement(position).equals(destination)
                && !calculateDownLeftDownLeftDownMovement(position).equals(destination)
                && !calculateLeftLeftUpLeftUpMovement(position).equals(destination)
                && !calculateLeftLeftDownLeftDownMovement(position).isSameRow(destination);
    }
}
