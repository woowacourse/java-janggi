package database;

import database.dao.GameDao;
import database.dao.PieceDao;
import database.dto.GameDto;
import domain.game.Team;
import domain.piece.Piece;
import domain.position.Position;
import java.util.Map;
import java.util.Optional;

public class GameRepository {
    private final GameDao gameDao;
    private final PieceDao pieceDao;

    public GameRepository(GameDao gameDao, PieceDao pieceDao) {
        this.gameDao = gameDao;
        this.pieceDao = pieceDao;
    }

    public Optional<GameDto> findLatestGame() {
        return gameDao.findLatestPlaying();
    }

    public int startNewGame(Team initialTurn, Map<Position, Piece> pieces) {
        int gameId = gameDao.createGame(initialTurn);

        pieceDao.saveAll(gameId, pieces);
        return gameId;
    }

    public Map<Position, Piece> loadPieces(int gameId) {
        return pieceDao.findAll(gameId);
    }

    public void saveMove(int gameId, Position src, Position dest, boolean isCapture, Team nextTurn) {
        if (isCapture) {
            pieceDao.delete(gameId, dest);
        }
        pieceDao.updatePosition(gameId, src, dest);
        gameDao.updateTurn(gameId, nextTurn);
    }

    public void deleteGame(int gameId) {
        gameDao.deleteById(gameId);
    }
}
