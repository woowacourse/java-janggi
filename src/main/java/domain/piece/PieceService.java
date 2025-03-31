package domain.piece;

import database.DbConnection;
import domain.Position;
import domain.player.Player;
import domain.player.Players;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class PieceService {

    private final PieceDao pieceDao;

    public PieceService() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        this.pieceDao = new PieceDao(connection);
    }

    public void saveAllPieces(Map<Position, Piece> pieces, int gameId) {
        for (Map.Entry<Position, Piece> entry : pieces.entrySet()) {
            savePiece(entry.getKey(), entry.getValue(), gameId);
        }
    }

    public void savePiece(Position position, Piece piece, int gameId) {
        pieceDao.insertPiece(piece.getPlayer().getId(), piece.getClass().getSimpleName(), position.getColumn(),
                position.getRow(), piece.getPoint(), gameId);
    }


    public void removeTargetPiece(Piece targetPiece, Position targetPosition, int gameId) {
        pieceDao.deleteTargetPiece(targetPiece, targetPosition, gameId);
    }

    // 피스 업데이트 (이동한 피스를 데이터베이스에 업데이트)
    public void updateMovingPiece(Piece startPiece, Position startPosition, Position targetPosition, int gameId) {
        pieceDao.updatePiecePosition(startPiece, startPosition, targetPosition, gameId);
    }
    

    public Map<Position, Piece> getAllPiecesByGameId(int gameId, Players players) {
        Player player1 = players.getBluePlayer();
        Player player2 = players.getRedPlayer();

        Map<Position, Piece> piecesMap1 = pieceDao.selectAllPieceById(gameId, player1); // Blue player's pieces
        Map<Position, Piece> piecesMap2 = pieceDao.selectAllPieceById(gameId, player2); // Red player's pieces

        Map<Position, Piece> allPiecesMap = new HashMap<>();
        allPiecesMap.putAll(piecesMap1);
        allPiecesMap.putAll(piecesMap2);

        return allPiecesMap;
    }

}
