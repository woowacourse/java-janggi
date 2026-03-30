package movepolicy.destination;

import pieces.Piece;

public interface DestinationRule {

    void validateDestination(Piece departurePiece, Piece destinationPiece);
}
