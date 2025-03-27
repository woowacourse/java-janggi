package piece;

import java.util.List;
import java.util.Objects;
import pieceProperty.PieceType;
import pieceProperty.Position;
import pieceProperty.Positions;
import view.ErrorMessage;

public abstract sealed class OmniDirectionalMover
        extends Piece
        permits Sa, Janggun{

    private Position position;

    public OmniDirectionalMover(Position position) {
        this.position = position;
    }

    @Override
    public boolean isSamePosition(final Position position) {
        return position.equals(this.position);
    }

    @Override
    public void updateChessPiecePositionBy(final Position position) {
        this.position = position;
    }

    @Override
    public void canMoveTo(Position destination) {
        if (isOmniDirectionalMover(destination)) {
            throw new IllegalArgumentException(ErrorMessage.formatMessage("기물이 움직일 수 없는 위치 입니다."));
        }
    }

    @Override
    public Positions makeRoute(Position destination) {
        return new Positions(List.of());
    }

    @Override
    public abstract boolean isJanggun();

    @Override
    public abstract boolean isPo();

    private boolean isOmniDirectionalMover(Position destination) {
        return !position.calculateUpMovement().equals(destination)
                && !position.calculateRightMovement().equals(destination)
                && !position.calculateLeftMovement().equals(destination)
                && !position.calculateDownMovement().equals(destination);
    }

    @Override
    public Position currentPosition() {
        return position;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        OmniDirectionalMover that = (OmniDirectionalMover) o;
        return Objects.equals(position, that.position);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(position);
    }

    @Override
    public abstract PieceType getPieceType();

}
