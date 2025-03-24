package piece;

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

        int dRow = position.calculateDRow(destination);
        int dCol = position.calculateDCol(destination);

        if (dRow == 2 && dCol == 1) {
            route.addPosition(Movement.calculateUpMovement(position));
        }

        if (dRow == 2 && dCol == -1) {
            route.addPosition(Movement.calculateUpMovement(position));
        }

        if (dRow == 1 && dCol == -2) {
            route.addPosition(Movement.calculateRightMovement(position));
        }

        if (dRow == -1 && dCol == -2) {
            route.addPosition(Movement.calculateRightMovement(position));
        }

        if (dRow == -2 && dCol == -1) {
            route.addPosition(Movement.calculateDownMovement(position));
        }

        if (dRow == -2 && dCol == 1) {
            route.addPosition(Movement.calculateDownMovement(position));
        }

        if (dRow == 1 && dCol == 2) {
            route.addPosition(Movement.calculateLeftMovement(position));
        }

        if (dRow == -1 && dCol == 2) {
            route.addPosition(Movement.calculateLeftMovement(position));
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

    private boolean isInvalidMaMove(Position destination) {
        return !Movement.calculateUpRightUPMovement(position).equals(destination)
                && !Movement.calculateUpLeftUpMovement(position).equals(destination)
                && !Movement.calculateDownRightDownMovement(position).equals(destination)
                && !Movement.calculateDownLeftDownMovement(position).equals(destination)
                && !Movement.calculateRightRightUpMovement(position).equals(destination)
                && !Movement.calculateRightRightDownMovement(position).equals(destination)
                && !Movement.calculateLeftLeftUpMovement(position).equals(destination)
                && !Movement.calculateLeftLeftDownMovement(position).equals(destination);
    }

}
