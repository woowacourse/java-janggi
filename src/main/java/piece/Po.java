package piece;

import static pieceProperty.Movement.isDownward;
import static pieceProperty.Movement.isLeftward;
import static pieceProperty.Movement.isRightward;
import static pieceProperty.Movement.isUpward;
import static pieceProperty.PieceType.PO;

import java.util.List;
import pieceProperty.Movement;
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

        if (isLeftward(dRow, dCol)) {
            addLeftwardRoute(dCol, route, presentRow, presentCol);
        }

        if (isRightward(dRow, dCol)) {
            addRightwardRoute(dCol, route, presentRow, presentCol);
        }

        if (isUpward(dRow, dCol)) {
            addUpwardRoute(dRow, route, presentRow, presentCol);
        }

        if (isDownward(dRow, dCol)) {
            addDownwardRoute(dRow, route, presentRow, presentCol);
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
