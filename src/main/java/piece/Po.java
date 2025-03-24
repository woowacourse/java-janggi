package piece;

import static pieceProperty.PieceType.PO;

import java.util.ArrayList;
import java.util.List;
import pieceProperty.PieceType;
import pieceProperty.Position;
import pieceProperty.Positions;
import view.ErrorMessage;

public class Po extends Piece {

    public Po(final Position position) {
        super(position);
    }

    @Override
    public Positions makeRoute(final Position destination) {
        Positions route = new Positions(List.of());
        int dRow = position.calculateDRow(destination);
        int dCol = position.calculateDCol(destination);

        int presentCol = getBoardPosition().getCol();
        int presentRow = getBoardPosition().getRow();

        if (dRow == 0 && dCol > 0) {
            for (int i = 1; i < dCol; i++) {
                route.addPosition(new Position(presentRow, presentCol - i));
            }
        }

        if (dRow == 0 && dCol < 0) {
            for (int i = 1; i < Math.abs(dCol); i++) {
                route.addPosition(new Position(presentRow, presentCol + i));
            }
        }

        if (dRow > 0 && dCol == 0) {
            for (int i = 1; i < dRow; i++) {
                route.addPosition(new Position(presentRow - i, presentCol));
            }
        }

        if (dRow < 0 && dCol == 0) {
            for (int i = 1; i < Math.abs(dRow); i++) {
                route.addPosition(new Position(presentRow + i, presentCol));
            }
        }

        return route;
    }

    @Override
    public void canMoveTo(final Position destination) {
        if (isInvalidPoMove(destination)) {
            throw new IllegalArgumentException(ErrorMessage.formatMessage("포가 움직일 수 없는 위치입니다."));

        }
    }

    @Override
    public boolean isKing() {
        return false;
    }

    @Override
    public boolean isPo() {
        return true;
    }

    @Override
    public PieceType getPieceType() {
        return PO;
    }

    private boolean isInvalidPoMove(Position destination) {
        return !position.isSameCol(destination) && !position.isSameRow(destination);
    }

}
