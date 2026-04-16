package repository;

import dao.GameDao;
import domain.board.JanggiBoard;
import domain.board.SavedPiecesGenerator;
import domain.game.Game;
import domain.game.GameScore;
import domain.game.MoveCommand;
import domain.intersection.Intersection;
import domain.team.Team;
import dto.dao.InitialGamePersistDto;
import dto.dao.MovePersistDto;
import entity.GameEntity;
import entity.PieceEntity;
import entity.ResumableGameEntity;
import java.util.List;
import java.util.Optional;
import transaction.TransactionTemplate;

public class GameRepository {
    private final GameDao gameDao;
    private final TransactionTemplate transactionTemplate;

    public GameRepository(GameDao gameDao, TransactionTemplate transactionTemplate) {
        this.gameDao = gameDao;
        this.transactionTemplate = transactionTemplate;
    }

    public Game findById(long gameId) {
        GameEntity state = gameDao.loadGameForResume(gameId);

        List<Intersection> intersections = state.pieces().stream()
                .map(PieceEntity::toIntersection)
                .toList();
        JanggiBoard board = new JanggiBoard(new SavedPiecesGenerator(intersections));

        return Game.restored(
                board,
                new GameScore(state.choScore(), state.hanScore()),
                Team.valueOf(state.turnTeam()),
                board.isGameRunning()
        );
    }

    public long saveNewGame(Game game) {
        return transactionTemplate.executeInTransaction(conn -> {
            return gameDao.insertInitialGameAndPieces(conn, InitialGamePersistDto.from(game));
        });
    }

    public void saveMove(Game game, long gameId, MoveCommand move, long movedPieceId, Long capturedPieceId) {
        transactionTemplate.executeInTransaction(conn -> {
            gameDao.persistMove(conn, MovePersistDto.afterTurn(game, gameId, move, movedPieceId, capturedPieceId));
            return null;
        });
    }

    public List<ResumableGameEntity> findResumableGames() {
        return gameDao.findResumableGames();
    }

    public Optional<Long> findPieceIdAt(long gameId, int y, int x) {
        return gameDao.findPieceIdAt(gameId, y, x);
    }
}
