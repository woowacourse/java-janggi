package piece;

import static pieceProperty.PieceType.JANGGUN;

import java.util.List;
import pieceProperty.Movement;
import pieceProperty.PieceType;
import pieceProperty.Position;
import pieceProperty.Positions;
import view.ErrorMessage;

public class Janggun extends Piece {

    public Janggun(final Position position) {
        super(position);
    }

    @Override
    public void canMoveTo(final Position destination) {
        if (!Movement.upMovement(position).equals(destination) && !Movement.rightMovement(position).equals(destination)
        && !Movement.leftMovement(position).equals(destination) && !Movement.downMovement(position).equals(destination)) {
            throw new IllegalArgumentException(ErrorMessage.formatMessage("왕이 움직일 수 없는 위치 입니다."));

        }
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
        return true;
    }

    @Override
    public boolean isPo() {
        return false;
    }

    @Override
    public PieceType getPieceType() {
        return JANGGUN;
    }

}
