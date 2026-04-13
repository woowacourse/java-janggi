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

            @Override
            protected void afterCommit(Game savedGame) {}
        }.execute();
    }

    public void move(Game game, Position from, Position to) {
        new TransactionTemplate<MoveResult>() {
            @Override
            protected MoveResult doInTransaction(Connection con) {
                MoveResult moveResult = game.validateMove(from, to);

                persistMoveResult(con, game.getId(), moveResult);
                return moveResult;
            }

            @Override
            protected void afterCommit(MoveResult moveResult) {
                game.applyMoveResult(moveResult);
            }
        }.execute();
    }

    public void forfeit(Game game, String turnName) {
        new TransactionTemplate<Void>() {
            @Override
            protected Void doInTransaction(Connection con) {
                Status newStatus = Status.CHU_WIN;
                if (turnName.equals(Team.CHU.getName())) {
                    newStatus = Status.HAN_WIN;
                }

                gameDao.update(con, game.getId(), game.getCurrentTeam().name(), newStatus.toString());
                return null;
            }

            @Override
            protected void afterCommit(Void result) {
                game.lose(turnName);
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
