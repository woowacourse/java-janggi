package movepolicy.destination;

import pieces.FullPiece;

public interface DestinationRule {
    void validateDestination(FullPiece departurePiece, FullPiece destinationPiece);
}
