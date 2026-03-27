package movepolicy.destination;

import pieces.FullPiece;
import pieces.Piece;

public class PoDestinationRule implements DestinationRule {

    @Override
    public void validateDestination(FullPiece departurePiece, Piece destinationPiece) {
        if (destinationPiece.isEmpty()) {
            return;
        }
        FullPiece targetPiece = (FullPiece) destinationPiece;
        if (departurePiece.isSameSide(targetPiece)) {
            throw new IllegalArgumentException("같은 진영의 말은 공격할 수 없습니다.");
        }
        if (departurePiece.isPo() && targetPiece.isPo()) {
            throw new IllegalArgumentException("포는 포를 공격할 수 없습니다.");
        }
    }
}
