package movepolicy.destination;

import pieces.FullPiece;

public class PoDestinationRule implements DestinationRule {
    @Override
    public void validateDestination(FullPiece departurePiece, FullPiece destinationPiece) {
        if (departurePiece == null) {
            throw new IllegalArgumentException("출발지의 기물이 없습니다.");
        }
        if (departurePiece.isSameSide(destinationPiece)) {
            throw new IllegalArgumentException("같은 진영의 말은 공격할 수 없습니다.");
        }
        if (departurePiece.isPo() && destinationPiece.isPo()) {
            throw new IllegalArgumentException("포는 포를 공격할 수 없습니다.");
        }
    }
}
