package domain.movepolicy.destination;

import domain.pieces.Piece;

public interface DestinationRule {

    void validateDestination(Piece departurePiece, Piece destinationPiece);
}
