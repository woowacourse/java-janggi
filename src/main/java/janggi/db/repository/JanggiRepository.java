package janggi.db.repository;

import janggi.db.dao.GameDao;
import janggi.db.dao.PieceDao;
import janggi.db.entity.GameEntity;
import janggi.db.entity.PieceEntity;
import janggi.domain.board.Board;
import janggi.domain.common.Position;
import janggi.domain.common.Team;
import janggi.domain.piece.Piece;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JanggiRepository {
    private final GameDao gameDao;
    private final PieceDao pieceDao;

    public JanggiRepository(GameDao gameDao, PieceDao pieceDao) {
        this.gameDao = gameDao;
        this.pieceDao = pieceDao;
    }

    public Long save(Long gameId, Board board, Team turn, boolean isFinished) {
        gameId = saveOrUpdate(gameId, turn, isFinished);

        pieceDao.deleteAllByGameId(gameId);

        List<PieceEntity> pieceEntities = new ArrayList<>();
        for (Map.Entry<Position, Piece> entry : board.getBoard().entrySet()) {
            pieceEntities.add(PieceEntity.from(gameId, entry.getKey(), entry.getValue()));
        }

        pieceDao.saveAll(pieceEntities);

        return gameId;
    }

    private Long saveOrUpdate(Long gameId, Team turn, boolean isFinished) {
        if (gameId == null) {
            return gameDao.save(new GameEntity(null, turn, isFinished));
        }
        gameDao.update(new GameEntity(gameId, turn, isFinished));
        return gameId;
    }

    public List<GameEntity> findOngoingGames() {
        return gameDao.findOngoingGames();
    }

    public GameEntity findGameById(Long gameId) {
        return gameDao.findById(gameId)
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 해당 ID의 게임이 없습니다."));
    }

    public Board loadBoard(Long gameId) {
        List<PieceEntity> pieceEntities = pieceDao.findAllByGameId(gameId);

        Board loadedBoard = new Board();

        for (PieceEntity pieceEntity : pieceEntities) {
            Position position = new Position(pieceEntity.getX(), pieceEntity.getY());
            Piece piece = new Piece(pieceEntity.getTeam(), pieceEntity.getPieceType());
            loadedBoard.place(position, piece);
        }

        return loadedBoard;
    }
}
