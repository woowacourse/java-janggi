package database.repository.piece;

import object.coordinate.Coordinate;
import java.util.Map;
import object.piece.Piece;

public record PieceDto(
        Map<Coordinate, Piece> hanPieces,
        Map<Coordinate, Piece> choPieces
) {
}
