package janggi;

import janggi.dao.GameRoom;
import janggi.dao.Piece;
import janggi.db.SQLManager;
import janggi.domain.GameInfo;
import janggi.domain.PieceInitInfo;
import janggi.domain.Position;
import janggi.domain.Side;
import janggi.domain.piece.PieceType;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class JanggiService {
    private final SQLManager sqlManager;
    private final GameRoom gameRoom;
    private final Piece piece;

    public JanggiService(SQLManager sqlManager, GameRoom gameRoom, Piece piece) {
        this.sqlManager = sqlManager;
        this.gameRoom = gameRoom;
        this.piece = piece;
    }

    public List<GameInfo> getEntireGame() {
        gameRoom.initTable();
        return gameRoom.findAllGames();
    }

    public int addGameData(String name, String createdDate, String lastUpdatedDate, List<PieceInitInfo> pieceInitInfos) {
        return gameRoom.insertGame(name, createdDate, lastUpdatedDate);
    }

    public void removeGame(int id) {
        gameRoom.removeGame(id);
    }

    public List<PieceInitInfo> getPieceInitInfos(int gameId) {
        return piece.getAllPieces(gameId).stream()
                .map(pieceDto -> new PieceInitInfo(new Position(pieceDto.x(), pieceDto.y()), Side.from(pieceDto.side()), PieceType.from(pieceDto.pieceType())))
                .toList();
    }

    public void movePiece(int gameId, Position start, Position end, Side side, PieceType pieceType) {
        Connection connection = sqlManager.ensureConnection();
        try {
            piece.deletePiece(connection, gameId, start.getX(), start.getY());
            piece.updatePiece(connection, gameId, end.getX(), end.getY(), side.name(), pieceType.name());

            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
            throw new RuntimeException("기물 이동 중 오류가 발생하여 롤백되었습니다.", e);
        }
    }
}
