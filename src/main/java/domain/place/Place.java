package domain.place;

import domain.board.BoardView;
import domain.place.piece.PieceSymbol;
import domain.place.piece.Side;
import domain.position.Position;
import java.util.Optional;

public interface Place {

    boolean isEmpty();

    boolean isSameSide(Place place);

    boolean hasSide(Side side);

    boolean isSameSymbol(PieceSymbol pieceSymbol);

    String getFormat();

    Optional<Side> getSide();

    boolean canMove(BoardView board, Position from, Position to);
}
