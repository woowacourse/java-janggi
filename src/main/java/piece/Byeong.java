package piece;

import static pieceProperty.PieceType.BYEONG;

import java.util.List;
import pieceProperty.PieceType;
import pieceProperty.Position;
import pieceProperty.Positions;
import view.ErrorMessage;

public class Byeong extends Piece {

    public Byeong(final Position position) {
        super(position);
    }

    @Override
    public void canMoveTo(final Position destination) {
        if (isInvalidByeongMove(destination)) {
            throw new IllegalArgumentException(ErrorMessage.formatMessage("병이 움직일 수 없는 위치 입니다."));
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
        return BYEONG;
    }

    private boolean isInvalidByeongMove(final Position destination) {
        return !getPosition().calculateDownMovement().equals(destination)
                && !getPosition().calculateLeftMovement().equals(destination)
                && !getPosition().calculateRightMovement().equals(destination);
    }

}
