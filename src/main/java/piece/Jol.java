package piece;

import static pieceProperty.PieceType.JOL;

import java.util.List;
import pieceProperty.Movement;
import pieceProperty.PieceType;
import pieceProperty.Position;
import pieceProperty.Positions;
import view.ErrorMessage;

public class Jol extends Piece {

    public Jol(final Position position) {
        super(position);
    }

    @Override
    public void canMoveTo(final Position destination) {
        if (isInvalidJolMove(destination)) {
            throw new IllegalArgumentException(ErrorMessage.formatMessage("졸이 움직일 수 없는 위치입니다."));
        }
    }

    private boolean isInvalidJolMove(Position destination) {
        return !Movement.calculateUpMovement(position).equals(destination)
                && !Movement.calculateLeftMovement(position).equals(destination)
                && !Movement.calculateRightMovement(position).equals(destination);
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
        return JOL;
    }

}
