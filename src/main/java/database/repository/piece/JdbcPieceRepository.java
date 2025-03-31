package database.repository.piece;

import java.util.Map;
import object.coordinate.Coordinate;
import object.piece.Piece;

public interface JdbcPieceRepository {

    void initializeTable();

    PieceDto loadPieces();

    void savePieces(Map<Coordinate, Piece> pieces);
}
