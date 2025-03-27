package piece;

import java.util.List;
import java.util.Objects;
import pieceProperty.PieceType;
import pieceProperty.Position;
import pieceProperty.Positions;
import view.ErrorMessage;

public abstract sealed class LinearMover
        implements Piece
        permits Po, Cha{

    private Position position;

    public LinearMover(Position position) {
        this.position = position;
    }

    @Override
    public boolean isSamePosition(final Position startPosition) {
        return startPosition.equals(position);
    }

    @Override
    public void updateChessPiecePositionBy(final Position destination) {
        position = destination;
    }

    @Override
    public void canMoveTo(Position destination) {
        if (isInvalidLinearMove(destination)) {
            throw new IllegalArgumentException(ErrorMessage.formatMessage("기물이 움직일 수 없는 위치입니다."));
        }
    }

    @Override
    public Positions makeRoute(Position destination) {
        Positions route = new Positions(List.of());
        int dRow = position.calculateDRow(destination);
        int dCol = position.calculateDCol(destination);

        int presentCol = position.getCol();
        int presentRow = position.getRow();

        if (position.isLeftward(dRow, dCol)) {
            addLeftwardRoute(dCol, route, presentRow, presentCol);
        }

        if (position.isRightward(dRow, dCol)) {
            addRightwardRoute(dCol, route, presentRow, presentCol);
        }

        if (position.isUpward(dRow, dCol)) {
            addUpwardRoute(dRow, route, presentRow, presentCol);
        }

        if (position.isDownward(dRow, dCol)) {
            addDownwardRoute(dRow, route, presentRow, presentCol);
        }

        return route;
    }

    private boolean isInvalidLinearMove(final Position destination) {
        return !position.isSameRow(destination) && !position.isSameCol(destination);
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

    @Override
    public Position currentPosition() {
        return position;
    }

    @Override
    public abstract boolean isJanggun();

    @Override
    public abstract boolean isPo();

    @Override
    public abstract PieceType getPieceType();

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        LinearMover that = (LinearMover) o;
        return Objects.equals(position, that.position);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(position);
    }
}
