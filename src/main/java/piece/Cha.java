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
    public Positions makeRoute(final Position destination) {
        Positions route = new Positions(List.of());
        int dRow = getPosition().calculateDRow(destination);
        int dCol = getPosition().calculateDCol(destination);

        int presentCol = getBoardPosition().getCol();
        int presentRow = getBoardPosition().getRow();

        if (getPosition().isLeftward(dRow, dCol)) {
            addLeftwardRoute(dCol, route, presentRow, presentCol);
        }

        if (getPosition().isRightward(dRow, dCol)) {
            addRightwardRoute(dCol, route, presentRow, presentCol);
        }

        if (getPosition().isUpward(dRow, dCol)) {
            addUpwardRoute(dRow, route, presentRow, presentCol);
        }

        if (getPosition().isDownward(dRow, dCol)) {
            addDownwardRoute(dRow, route, presentRow, presentCol);
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
        return CHA;
    }

    private boolean isInvalidChaMove(final Position destination) {
        return !getPosition().isSameRow(destination) && !getPosition().isSameCol(destination);
    }

    private void addDownwardRoute(final int dRow, Positions route, final int presentRow, final int presentCol) {
        for (int i = 1; i < Math.abs(dRow); i++) {
            route.addPosition(new Position(presentRow + i, presentCol));
        }
    }

    private void addUpwardRoute(final int dRow, Positions route, final int presentRow, final int presentCol) {
        for (int i = 1; i < dRow; i++) {
            route.addPosition(new Position(presentRow - i, presentCol));
        }
    }

    private void addRightwardRoute(final int dCol, Positions route, final int presentRow, final int presentCol) {
        for (int i = 1; i < Math.abs(dCol); i++) {
            route.addPosition(new Position(presentRow, presentCol + i));
        }
    }

    private void addLeftwardRoute(final int dCol, Positions route, final int presentRow, final int presentCol) {
        for (int i = 1; i < dCol; i++) {
            route.addPosition(new Position(presentRow, presentCol - i));
        }
    }

}
