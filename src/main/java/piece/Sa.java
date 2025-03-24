package piece;

import static pieceProperty.Movement.calculateDownMovement;
import static pieceProperty.Movement.calculateLeftMovement;
import static pieceProperty.Movement.calculateRightMovement;
import static pieceProperty.Movement.calculateUpMovement;
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

    @Override
    public Positions makeRoute(final Position position) {
        return new Positions(List.of());
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

    private boolean isInvalidSaMove(final Position destination) {
        return !calculateDownMovement(position).equals(destination)
                && !calculateUpMovement(position).equals(destination)
                && !calculateLeftMovement(position).equals(destination)
                && !calculateRightMovement(position).equals(destination);
    }

}
