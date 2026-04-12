import domain.board.Board;
import domain.game.Game;
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

    public void moveAndSave(Game game, Position from, Position to) {
        try (Connection con = DBConnectionUtil.getConnection()) {
            con.setAutoCommit(false);

            try {
                boolean hasTargetPiece = game.getBoard().findPieceByPosition(to).isPresent();
                game.tryToMove(from, to);
                if (game.getStatus() == Status.PLAYING) {
                    game.changeTurn();
                }

                updateGameState(game, from, to, hasTargetPiece, con);

                con.commit();
            } catch (Exception e) {
                con.rollback();
                throw new RuntimeException("게임 저장 실패", e);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public Game saveGame(Game game) {
        try (Connection con = DBConnectionUtil.getConnection()) {
            con.setAutoCommit(false);

            try {
                Game savedGame = gameDao.save(con, game);
                boardDao.saveBoard(con, savedGame.getId(),game.getBoard());

                con.commit();
                return savedGame;
            } catch (Exception e) {
                con.rollback();
                throw new RuntimeException("게임 저장 실패", e);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private void updateGameState(Game game, Position from, Position to, boolean hasTargetPiece, Connection con) {
        if (hasTargetPiece) {
            boardDao.deleteByPosition(con, game.getId(), to.getRow(), to.getCol());
        }
        boardDao.updatePosition(con, game.getId(), from.getRow(), from.getCol(), to.getRow(), to.getCol());

        gameDao.update(con, game.getId(), game.getCurrentTeam().name(), game.getStatus().toString());
    }
}
