package domain.movepolicy.destination;

import domain.pieces.Piece;

public class PoDestinationRule implements DestinationRule {

    @Override
    public void validateDestination(Piece departurePiece, Piece destinationPiece) {
        if (destinationPiece.isEmpty()) {
            return;
        }
        if (departurePiece.isSameSide(destinationPiece)) {
            throw new IllegalArgumentException("같은 진영의 말은 공격할 수 없습니다.");
        }
        if (departurePiece.isPo() && destinationPiece.isPo()) {
            throw new IllegalArgumentException("포는 포를 공격할 수 없습니다.");
        }
    }
}
