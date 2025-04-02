package domain.board;

import domain.piece.coordiante.Coordinate;
import domain.piece.Country;
import domain.piece.PieceType;

public interface ReadableBoard {

    boolean hasPiece(Coordinate to);

    PieceType findPieceTypeByCoordinate(Coordinate to);

    boolean isMyTeam(Country country, Coordinate to);
}
