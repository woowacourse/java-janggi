package piece;

import static pieceProperty.PieceType.MA;

import java.util.ArrayList;
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
    public boolean canMoveTo(final Position position) {
        int dx = getBoardPosition().getRow() - position.getRow();
        int dy = getBoardPosition().getCol() - position.getCol();

        if (dx == 2 && Math.abs(dy) == 1) {
            return true;
        }

        if (dy == 2 && Math.abs(dx) == 1) {
            return true;
        }

        if (dx == -2 && Math.abs(dy) == 1) {
            return true;
        }

        if (dy == -2 && Math.abs(dx) == 1) {
            return true;
        }

        throw new IllegalArgumentException(ErrorMessage.formatMessage("마가 움직일 수 없는 위치입니다."));
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
