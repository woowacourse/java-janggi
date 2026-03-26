package movepolicy.destination;

import pieces.Piece;

public class BasicDestinationRule implements DestinationRule {
    @Override
    public boolean validateDestination(Piece departurePiece, Piece destinationPiece) {
        if (departurePiece == null) {
            throw new IllegalArgumentException("출발지의 기물이 없습니다.");
        }
        if (departurePiece.isSameSide(destinationPiece)) {
            throw new IllegalArgumentException("같은 진영의 말은 공격할 수 없습니다.");
        }
        return true;
    }
}
