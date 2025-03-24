package piece;

import static pieceProperty.PieceType.CHA;

import java.util.List;
import pieceProperty.PieceType;
import pieceProperty.Position;
import pieceProperty.Positions;
import view.ErrorMessage;

public class Cha extends Piece {

    public Cha(final Position position) {
        super(position);
    }

    @Override
    public void canMoveTo(Position destination) {
        if (isInvalidChaMove(destination)) {
            throw new IllegalArgumentException(ErrorMessage.formatMessage("차가 움직일 수 없는 위치 입니다."));

        }
    }

    @Override
    public Positions makeRoute(final Position position) {
        Positions route = new Positions(List.of());
        int dx = getBoardPosition().getRow() - position.getRow();
        int dy = getBoardPosition().getCol() - position.getCol();
        int presentCol = getBoardPosition().getCol();
        int presentRow = getBoardPosition().getRow();

        if (dx == 0 && dy > 0) {
            for (int i = 1; i < dy; i++) {
                route.addPosition(new Position(presentRow, presentCol - i));
            }
        }

        if (dx == 0 && dy < 0) {
            for (int i = 1; i < Math.abs(dy); i++) {
                route.addPosition(new Position(presentRow, presentCol + i));
            }
        }

        if (dx > 0 && dy == 0) {
            for (int i = 1; i < dx; i++) {
                route.addPosition(new Position(presentRow - i, presentCol));
            }
        }

        if (dx < 0 && dy == 0) {
            for (int i = 1; i < Math.abs(dx); i++) {
                route.addPosition(new Position(presentRow + i, presentCol));
            }
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
        return CHA;
    }

    private boolean isInvalidChaMove(Position destination) {
        return !position.isSameRow(destination) && !position.isSameCol(destination);
    }

}
