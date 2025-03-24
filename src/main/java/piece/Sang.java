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

    private boolean isInvalidSangMove(Position destination) {
        return !Movement.upRightUpRightUpMovement(position).equals(destination)
                && !Movement.upLeftUpLeftUpMovement(position).equals(destination)
                && !Movement.rightRightUpRightUpMovement(position).equals(destination)
                && !Movement.rightRightDownRightDownMovement(position).equals(destination)
                && !Movement.downRightDownRightDownMovement(position).equals(destination)
                && !Movement.downLeftDownLeftDownMovement(position).equals(destination)
                && !Movement.leftLeftUpLeftUpMovement(position).equals(destination)
                && !Movement.leftLeftDownLeftDownMovement(position).isSameRow(destination);
    }

    @Override
    public Positions makeRoute(final Position position) {
        Positions route = new Positions(List.of());

        int dx = getBoardPosition().getRow() - position.getRow();
        int dy = getBoardPosition().getCol() - position.getCol();
        int presentRow = getBoardPosition().getRow();
        int presentCol = getBoardPosition().getCol();

        if (dx == 3 && dy == 2) {
            route.addPosition(new Position(presentRow - 1, presentCol));
            route.addPosition(new Position(presentRow - 2, presentCol - 1));
        }

        if (dx == 3 && dy == -2) {
            route.addPosition(new Position(presentRow - 1, presentCol));
            route.addPosition(new Position(presentRow - 2, presentCol + 1));
        }

        if (dx == 2 && dy == -3) {
            route.addPosition(new Position(presentRow, presentCol + 1));
            route.addPosition(new Position(presentRow - 1, presentCol + 2));
        }

        if (dx == -2 && dy == -3) {
            route.addPosition(new Position(presentRow, presentCol + 1));
            route.addPosition(new Position(presentRow + 1, presentCol + 2));
        }

        if (dx == -3 && dy == 2) {
            route.addPosition(new Position(presentRow + 1, presentCol));
            route.addPosition(new Position(presentRow + 2, presentCol - 1));
        }

        if (dx == -3 && dy == -2) {
            route.addPosition(new Position(presentRow + 1, presentCol));
            route.addPosition(new Position(presentRow + 2, presentCol + 1));
        }

        if (dx == 2 && dy == 3) {
            route.addPosition(new Position(presentRow, presentCol - 1));
            route.addPosition(new Position(presentRow - 1, presentCol - 2));
        }

        if (dx == -2 && dy == 3) {
            route.addPosition(new Position(presentRow, presentCol - 1));
            route.addPosition(new Position(presentRow + 1, presentCol - 2));
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
        return SANG;
    }
}
