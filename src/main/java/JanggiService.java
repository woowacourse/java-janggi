import domain.board.Board;
import domain.board.BoardFactory;
import domain.board.Formation;
import domain.board.Team;
import domain.game.Game;
import domain.game.MoveResult;
import domain.game.Status;
import domain.vo.Position;
import repository.BoardDao;
import repository.DBConnectionUtil;
import repository.GameDao;
import repository.dto.GameDto;

import java.sql.Connection;
import java.sql.SQLException;
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
        try (Connection con = DBConnectionUtil.getConnection()) {
            con.setAutoCommit(false);

            try {
                Board board = BoardFactory.setUp(hanFormation, chuFormation);
                Game game = Game.of(board);

                Game savedGame = gameDao.save(con, game);
                boardDao.saveBoard(con, savedGame.getId(), game.getBoard());

                con.commit();
                return savedGame;
            } catch (Exception e) {
                con.rollback();
                throw new RuntimeException("게임 생성 실패", e);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public void move(Game game, Position from, Position to) {
        try (Connection con = DBConnectionUtil.getConnection()) {
            con.setAutoCommit(false);

            try {
                MoveResult moveResult = game.validateMove(from, to);

                persistMoveResult(con, game.getId(), moveResult);
                con.commit();

                game.applyMoveResult(moveResult);
            } catch (IllegalArgumentException e) {
                throw e;
            } catch (Exception e) {
                con.rollback();
                throw new RuntimeException("이동 처리 실패", e);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public void forfeit(Game game, String turnName) {
        try (Connection con = DBConnectionUtil.getConnection()) {
            con.setAutoCommit(false);

            try {
                Status newStatus = Status.CHU_WIN;
                if (turnName.equals(Team.CHU.getName())) {
                    newStatus = Status.HAN_WIN;
                }

                gameDao.update(con, game.getId(), game.getCurrentTeam().name(), newStatus.toString());
                con.commit();

                game.lose(turnName);
            } catch (Exception e) {
                con.rollback();
                throw new RuntimeException("기권 처리 실패", e);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
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
