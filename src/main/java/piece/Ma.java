package piece;

import static pieceProperty.PieceType.MA;

import java.util.ArrayList;
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

    private boolean isInvalidMaMove(Position destination) {
        return !Movement.upRightUPMovement(position).equals(destination)
                && !Movement.upLeftUpMovement(position).equals(destination)
                && !Movement.downRightDownMovement(position).equals(destination)
                && !Movement.downLeftDownMovement(position).equals(destination)
                && !Movement.rightRightUpMovement(position).equals(destination)
                && !Movement.rightRightDownMovement(position).equals(destination)
                && !Movement.leftLeftUpMovement(position).equals(destination)
                && !Movement.leftLeftDownMovement(position).equals(destination);
    }

    @Override
    public Positions makeRoute(final Position position) {
        Positions route = new Positions(List.of());

        int dx = getBoardPosition().getRow() - position.getRow();
        int dy = getBoardPosition().getCol() - position.getCol();
        int presentCol = getBoardPosition().getCol();
        int presentRow = getBoardPosition().getRow();

        if (dx == 2 && dy == 1) {
            route.addPosition(new Position(presentRow - 1, presentCol));
        }

        if (dx == 2 && dy == -1) {
            route.addPosition(new Position(presentRow - 1, presentCol));
        }

        if (dx == 1 && dy == -2) {
            route.addPosition(new Position(presentRow, presentCol + 1));
        }

        if (dx == -1 && dy == -2) {
            route.addPosition(new Position(presentRow, presentCol + 1));
        }

        if (dx == -2 && dy == -1) {
            route.addPosition(new Position(presentRow + 1, presentCol));
        }

        if (dx == -2 && dy == 1) {
            route.addPosition(new Position(presentRow + 1, presentCol));
        }

        if (dx == 1 && dy == 2) {
            route.addPosition(new Position(presentRow, presentCol - 1));
        }

        if (dx == -1 && dy == 2) {
            route.addPosition(new Position(presentRow, presentCol - 1));
        }

        return route;
    }

    @Override
    public void updateChessPiecePositionBy(Position position) {
        this.position = position;
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

}
