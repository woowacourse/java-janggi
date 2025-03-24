package piece;

import static pieceProperty.PieceType.SA;

import java.util.List;
import pieceProperty.Movement;
import pieceProperty.PieceType;
import pieceProperty.Position;
import pieceProperty.Positions;
import view.ErrorMessage;

public class Sa extends Piece {

    public Sa(final Position position) {
        super(position);
    }

    @Override
    public void canMoveTo(final Position destination) {
        if (isInvalidSaMove(destination)) {
            throw new IllegalArgumentException(ErrorMessage.formatMessage("사가 움직일 수 없는 위치 입니다."));
        }
    }

    private boolean isInvalidSaMove(Position destination) {
        return !Movement.calculateDownMovement(position).equals(destination)
                && !Movement.calculateUpMovement(position).equals(destination)
                && !Movement.calculateLeftMovement(position).equals(destination)
                && !Movement.calculateRightMovement(position).equals(destination);
    }

    @Override
    public Positions makeRoute(final Position position) {
        return new Positions(List.of());
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
        return SA;
    }

}
