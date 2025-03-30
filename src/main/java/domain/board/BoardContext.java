package domain.board;

import domain.Coordinate;
import domain.piece.Country;
import domain.piece.PieceType;

public interface BoardContext {

    boolean hasPiece(Coordinate to);

    PieceType findPieceTypeByCoordinate(Coordinate to);

    boolean isMyTeam(Country country, Coordinate to);

}
