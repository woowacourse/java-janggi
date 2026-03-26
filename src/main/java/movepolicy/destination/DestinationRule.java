package movepolicy.destination;

import pieces.Piece;

public interface DestinationRule {
    boolean validateDestination(Piece departurePiece, Piece destinationPiece);
}
