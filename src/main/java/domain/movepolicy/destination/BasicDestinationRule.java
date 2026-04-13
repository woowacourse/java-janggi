package domain.movepolicy.destination;

import domain.movepolicy.exception.InvalidDestinationException;
import domain.movepolicy.exception.MovePolicyErrorMessage;
import domain.pieces.Piece;

public class BasicDestinationRule implements DestinationRule {

    @Override
    public void validateDestination(Piece departurePiece, Piece destinationPiece) {
        if (destinationPiece.isEmpty()) {
            return;
        }
        if (departurePiece.isSameSide(destinationPiece)) {
            throw new InvalidDestinationException(MovePolicyErrorMessage.SAME_SIDE_ATTACK);
        }
    }
}
