package piece;

import static pieceProperty.Movement.calculateDownLeftDownMovement;
import static pieceProperty.Movement.calculateDownRightDownMovement;
import static pieceProperty.Movement.calculateLeftLeftDownMovement;
import static pieceProperty.Movement.calculateLeftLeftUpMovement;
import static pieceProperty.Movement.calculateRightRightDownMovement;
import static pieceProperty.Movement.calculateRightRightUpMovement;
import static pieceProperty.Movement.calculateUpLeftUpMovement;
import static pieceProperty.Movement.calculateUpRightUPMovement;
import static pieceProperty.Movement.isDownLeftDown;
import static pieceProperty.Movement.isDownRightDown;
import static pieceProperty.Movement.isLeftLeftDown;
import static pieceProperty.Movement.isLeftLeftUp;
import static pieceProperty.Movement.isRightRightDown;
import static pieceProperty.Movement.isRightRightUp;
import static pieceProperty.Movement.isUpLeftUp;
import static pieceProperty.Movement.isUpRightUp;
import static pieceProperty.PieceType.MA;

import java.util.List;
import pieceProperty.Movement;
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

        if (isUpLeftUp(dRow, dCol)) {
            route.addPosition(Movement.calculateUpMovement(getPosition()));
        }

        if (isUpRightUp(dRow, dCol)) {
            route.addPosition(Movement.calculateUpMovement(getPosition()));
        }

        if (isRightRightUp(dRow, dCol)) {
            route.addPosition(Movement.calculateRightMovement(getPosition()));
        }

        if (isRightRightDown(dRow, dCol)) {
            route.addPosition(Movement.calculateRightMovement(getPosition()));
        }

        if (isDownRightDown(dRow, dCol)) {
            route.addPosition(Movement.calculateDownMovement(getPosition()));
        }

        if (isDownLeftDown(dRow, dCol)) {
            route.addPosition(Movement.calculateDownMovement(getPosition()));
        }

        if (isLeftLeftUp(dRow, dCol)) {
            route.addPosition(Movement.calculateLeftMovement(getPosition()));
        }

        if (isLeftLeftDown(dRow, dCol)) {
            route.addPosition(Movement.calculateLeftMovement(getPosition()));
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
        return MA;
    }

    private boolean isInvalidMaMove(final Position destination) {
        return !calculateUpRightUPMovement(getPosition()).equals(destination)
                && !calculateUpLeftUpMovement(getPosition()).equals(destination)
                && !calculateDownRightDownMovement(getPosition()).equals(destination)
                && !calculateDownLeftDownMovement(getPosition()).equals(destination)
                && !calculateRightRightUpMovement(getPosition()).equals(destination)
                && !calculateRightRightDownMovement(getPosition()).equals(destination)
                && !calculateLeftLeftUpMovement(getPosition()).equals(destination)
                && !calculateLeftLeftDownMovement(getPosition()).equals(destination);
    }

}
