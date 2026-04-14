import domain.board.Board;
import domain.board.BoardFactory;
import domain.board.Formation;
import domain.game.Game;
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
                Game findGame = loadGame(gameId);

                boolean hasTargetPiece = findGame.getBoard().findPieceByPosition(to).isPresent();
                findGame.tryToMove(from, to);
                if (findGame.getStatus() == Status.PLAYING) {
                    findGame.changeTurn();
                }

                updateGameState(findGame, from, to, hasTargetPiece, con);

                return findGame;
            }
        }.execute();
    }

    public Game forfeit(Long gameId, String turnName) {
        return new TransactionTemplate<Game>() {
            @Override
            protected Game doInTransaction(Connection con) {
                Game findGame = loadGame(gameId);

                findGame.lose(turnName);
                gameDao.update(con, findGame.getId(), findGame.getCurrentTeam().name(), findGame.getStatus().name());

                return findGame;
            }
        }.execute();
    }

    private void updateGameState(Game game, Position from, Position to, boolean hasTargetPiece, Connection con) {
        if (hasTargetPiece) {
            boardDao.deleteByPosition(con, game.getId(), to.getRow(), to.getCol());
        }
        boardDao.updatePosition(con, game.getId(), from.getRow(), from.getCol(), to.getRow(), to.getCol());

        gameDao.update(con, game.getId(), game.getCurrentTeam().name(), game.getStatus().toString());
    }
}
