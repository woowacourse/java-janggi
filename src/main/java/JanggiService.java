import domain.board.Board;
import domain.board.Piece;
import domain.board.Team;
import domain.board.Type;
import domain.game.Game;
import domain.game.Status;
import domain.vo.Position;
import entity.BoardEntity;
import repository.DBConnectionUtil;
import repository.GameDao;
import repository.BoardDao;
import repository.dto.GameDto;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        List<BoardEntity> savedPieces = boardDao.findAllByGameId(gameId);
        Board board = convertPieceEntitiesToBoard(savedPieces);

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
                throw new RuntimeException("[ERROR] 게임 저장 실패", e);
            }
        } catch (SQLException e) {
            throw new RuntimeException("[ERROR] " + e.getMessage());
        }
    }

    public Game saveGame(Game game) {
        try (Connection con = DBConnectionUtil.getConnection()) {
            con.setAutoCommit(false);

            try {
                Game savedGame = gameDao.save(con, game);

                List<BoardEntity> boards = convertBoardToPieceEntities(savedGame.getId(), game.getBoard());
                boardDao.saveAll(con, boards);

                con.commit();
                return savedGame;
            } catch (Exception e) {
                con.rollback();
                throw new RuntimeException("[ERROR] 게임 저장 실패", e);
            }
        } catch (SQLException e) {
            throw new RuntimeException("[ERROR] " + e.getMessage());
        }
    }

    private void updateGameState(Game game, Position from, Position to, boolean hasTargetPiece, Connection con) {
        if (hasTargetPiece) {
            boardDao.deleteByPosition(con, game.getId(), to.getRow(), to.getCol());
        }
        boardDao.updatePosition(con, game.getId(), from.getRow(), from.getCol(), to.getRow(), to.getCol());

        gameDao.update(con, game.getId(), game.getCurrentTeam().name(), game.getStatus().toString());
    }

    private Board convertPieceEntitiesToBoard(List<BoardEntity> findPieces) {
        Map<Position, Piece> board = new HashMap<>();
        for (BoardEntity piece : findPieces) {
            Position position = Position.of(piece.getPositionRow(), piece.getPositionCol());
            Type type = Type.valueOf(piece.getPieceType());
            board.put(position, Piece.of(Team.valueOf(piece.getTeam()), type, type.createStrategy()));
        }
        return Board.of(board);
    }

    private List<BoardEntity> convertBoardToPieceEntities(Long gameId, Board board) {
        List<BoardEntity> boards = new ArrayList<>();
        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 9; col++) {
                Position position = Position.of(row, col);
                board.findPieceByPosition(position)
                        .ifPresent(b -> boards.add(BoardEntity.from(gameId, b, position)));
            }
        }
        return boards;
    }
}
