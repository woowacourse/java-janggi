package domain;

public interface DestinationRule {
    boolean validateDestination(Piece departurePiece, Piece destinationPiece);
}
