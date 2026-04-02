package domain.piece;

import domain.position.Position;
import java.util.List;

public interface Piece {
    boolean canMove(Position source, Position target);

    List<Position> calculateRoute(Position source, Position target);

    void validateRoute(List<Piece> piecesOnRoute, Piece destinationPiece);

    boolean isNotEmpty();

    boolean isAlly(Piece other);

    String display(PieceAppearance colorizer);
}
