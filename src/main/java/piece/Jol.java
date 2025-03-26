package piece;

import static pieceProperty.PieceType.JOL;

import java.util.List;
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

    @Override
    public Positions makeRoute(final Position position) {
        return new Positions(List.of());
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
        return JOL;
    }

    private boolean isInvalidJolMove(final Position destination) {
        return !getPosition().calculateUpMovement().equals(destination)
                && !getPosition().calculateLeftMovement().equals(destination)
                && !getPosition().calculateRightMovement().equals(destination);
    }

}
