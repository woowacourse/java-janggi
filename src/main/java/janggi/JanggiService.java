package janggi;

import janggi.dao.GameRoomDao;
import janggi.dao.PieceDao;
import janggi.db.TransactionManager;
import janggi.domain.Position;
import janggi.domain.Side;
import janggi.domain.piece.PieceType;
import janggi.dto.GameDto;
import janggi.dto.GameResponseDto;
import janggi.dto.PieceDto;
import janggi.dto.TurnDto;
import java.util.List;

public class JanggiService {
    private final TransactionManager transactionManager;
    private final GameRoomDao gameRoomDao;
    private final PieceDao pieceDao;

    public JanggiService(TransactionManager transactionManager, GameRoomDao gameRoomDao, PieceDao pieceDao) {
        this.transactionManager = transactionManager;
        this.gameRoomDao = gameRoomDao;
        this.pieceDao = pieceDao;
    }

    public List<GameResponseDto> getEntireGame() {
        return gameRoomDao.findAllGames();
    }

    public int addGameData(GameDto gameDto, List<PieceDto> pieceDtos) {
        return transactionManager.sync((connection) -> {
            int gameId = gameRoomDao.insertGame(connection, gameDto);
            pieceDao.updatePieces(connection, gameId, pieceDtos);

            return gameId;
        });
    }

    public void removeGame(int id) {
        transactionManager.sync((connection) -> {
            gameRoomDao.removeGame(connection, id);
        });
    }

    public List<PieceDto> getPieceInitInfos(int gameId) {
        return pieceDao.getAllPieces(gameId);
    }

    public void movePiece(int gameId, Position start, Position end, Side side, PieceType pieceType, TurnDto turnDto) {
        transactionManager.sync(connection -> {
            gameRoomDao.updateGameTurn(connection, gameId, turnDto);
            pieceDao.deletePiece(connection, gameId, start.getX(), start.getY());

            PieceDto pieceDto = new PieceDto(end.getX(), end.getY(), pieceType.getName(), side.getName());
            pieceDao.updatePiece(connection, gameId, pieceDto);
        });
    }
}
