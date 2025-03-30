package domain.piece;

import database.DbConnection;
import domain.Position;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

public class PieceService {

    private final PieceDao pieceDao;

    public PieceService() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        this.pieceDao = new PieceDao(connection);
    }

    public void saveAllPieces(Map<Position, Piece> pieces) {
        for (Map.Entry<Position, Piece> entry : pieces.entrySet()) {
            // Map에서 가져온 Position과 Piece를 사용하여 savePiece 호출
            savePiece(entry.getKey(), entry.getValue());
        }
    }

    public void savePiece(Position position, Piece piece) {
        // Piece의 정보를 DB에 저장
        pieceDao.insertPiece(piece.getPlayer().getId(), piece.getClass().getSimpleName(), position.getColumn(),
                position.getRow());
    }
}
