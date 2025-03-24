package piece;

import static pieceProperty.PieceType.CHA;

import java.util.List;
import pieceProperty.Movement;
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
        int dRow = position.calculateDRow(destination);
        int dCol = position.calculateDCol(destination);

        int presentCol = getBoardPosition().getCol();
        int presentRow = getBoardPosition().getRow();

        if (Movement.isLeftward(dRow, dCol)) {
            addLeftwardRoute(dCol, route, presentRow, presentCol);
        }

        if (Movement.isRightward(dRow, dCol)) {
            addRightwardRoute(dCol, route, presentRow, presentCol);
        }

        if (Movement.isUpward(dRow, dCol)) {
            addUpwardRoute(dRow, route, presentRow, presentCol);
        }

        if (Movement.isDownward(dRow, dCol)) {
            addDownwardRoute(dRow, route, presentRow, presentCol);
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
        return CHA;
    }

    private boolean isInvalidChaMove(Position destination) {
        return !position.isSameRow(destination) && !position.isSameCol(destination);
    }

    private void addDownwardRoute(int dRow, Positions route, int presentRow, int presentCol) {
        for (int i = 1; i < Math.abs(dRow); i++) {
            route.addPosition(new Position(presentRow + i, presentCol));
        }
    }

    private void addUpwardRoute(int dRow, Positions route, int presentRow, int presentCol) {
        for (int i = 1; i < dRow; i++) {
            route.addPosition(new Position(presentRow - i, presentCol));
        }
    }

    private void addRightwardRoute(int dCol, Positions route, int presentRow, int presentCol) {
        for (int i = 1; i < Math.abs(dCol); i++) {
            route.addPosition(new Position(presentRow, presentCol + i));
        }
    }

    private void addLeftwardRoute(int dCol, Positions route, int presentRow, int presentCol) {
        for (int i = 1; i < dCol; i++) {
            route.addPosition(new Position(presentRow, presentCol - i));
        }
    }

}
