import domain.board.Board;
import domain.board.BoardFactory;
import domain.board.Formation;
import domain.board.Team;
import domain.game.Game;
import domain.game.MoveResult;
import domain.game.Status;
import domain.vo.Position;
import repository.BoardDao;
import repository.GameDao;
import repository.TransactionTemplate;
import repository.dto.GameDto;

import java.sql.Connection;
import java.util.List;

public class JanggiService {

    private final GameDao gameDao;
    private final BoardDao boardDao;

    public JanggiService(GameDao gameDao, BoardDao boardDao) {
        this.gameDao = gameDao;
        this.boardDao = boardDao;
    }

    public List<GameDto> findAllGames() {
        return gameDao.findAll();
    }

    public Game loadGame(Long gameId) {
        Board board = boardDao.findByGameId(gameId);
        return gameDao.findById(gameId, board);
    }

    public Game createAndSaveGame(Formation hanFormation, Formation chuFormation) {
        return new TransactionTemplate<Game>() {
            @Override
            protected Game doInTransaction(Connection con) {
                Board board = BoardFactory.setUp(hanFormation, chuFormation);
                Game game = Game.of(board);

                Game savedGame = gameDao.save(con, game);
                boardDao.saveBoard(con, savedGame.getId(), game.getBoard());
                return savedGame;
            }
        }.execute();
    }

    public Game move(Long gameId, Position from, Position to) {
        return new TransactionTemplate<Game>() {
            @Override
            protected Game doInTransaction(Connection con) {
                Game game = loadGame(gameId);
                MoveResult moveResult = game.validateMove(from, to);

                persistMoveResult(con, gameId, moveResult);
                game.applyMoveResult(moveResult);

                return game;
            }
        }.execute();
    }

    public Game forfeit(Long gameId, String turnName) {
        return new TransactionTemplate<Game>() {
            @Override
            protected Game doInTransaction(Connection con) {
                Game game = loadGame(gameId);

                Status newStatus = Status.CHU_WIN;
                if (turnName.equals(Team.CHU.getName())) {
                    newStatus = Status.HAN_WIN;
                }

                gameDao.update(con, gameId, game.getCurrentTeam().name(), newStatus.toString());
                game.lose(turnName);

                return game;
            }
        }.execute();
    }

    private void persistMoveResult(Connection con, Long gameId, MoveResult moveResult) {
        if (moveResult.captured()) {
            boardDao.deleteByPosition(con, gameId,
                    moveResult.to().getRow(),
                    moveResult.to().getCol());
        }

        boardDao.updatePosition(con, gameId,
                moveResult.from().getRow(), moveResult.from().getCol(),
                moveResult.to().getRow(), moveResult.to().getCol());

        gameDao.update(con, gameId,
                moveResult.nextTurn().name(),
                moveResult.status().toString());
    }
}
