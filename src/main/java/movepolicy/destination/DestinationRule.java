package movepolicy.destination;

import pieces.FullPiece;
import pieces.Piece;

public interface DestinationRule {

    void validateDestination(FullPiece departurePiece, Piece destinationPiece);
}
