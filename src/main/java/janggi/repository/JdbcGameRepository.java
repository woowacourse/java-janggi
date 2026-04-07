package janggi.repository;

import janggi.config.ConnectionManager;
import janggi.dao.BoardPieceDao;
import janggi.dao.GameRoomDao;
import janggi.domain.Game;
import janggi.dto.BoardPiece;
import janggi.dto.GameRoom;
import janggi.dto.NewGameRoom;
import janggi.dto.TurnDto;
import janggi.mapper.GamePersistenceMapper;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class JdbcGameRepository implements GameRepository {
    private static final String GAME_ROOM_NOT_FOUND_MESSAGE = "존재하지 않는 게임방입니다.";
    private static final String CREATE_GAME_FAIL_MESSAGE = "게임 생성 저장 중 오류가 발생했습니다.";
    private static final String ENTER_GAME_FAIL_MESSAGE = "게임 복원 중 오류가 발생했습니다.";
    private static final String MOVE_SAVE_FAIL_MESSAGE = "게임 이동 저장 중 오류가 발생했습니다.";

    private final ConnectionManager connectionManager;
    private final GameRoomDao gameRoomDao;
    private final BoardPieceDao boardPieceDao;
    private final GamePersistenceMapper gamePersistenceMapper;

    public JdbcGameRepository(
            ConnectionManager connectionManager,
            GameRoomDao gameRoomDao,
            BoardPieceDao boardPieceDao,
            GamePersistenceMapper gamePersistenceMapper
    ) {
        this.connectionManager = connectionManager;
        this.gameRoomDao = gameRoomDao;
        this.boardPieceDao = boardPieceDao;
        this.gamePersistenceMapper = gamePersistenceMapper;
    }

    @Override
    public long saveNewGame(Game game) {
        try (Connection connection = connectionManager.getConnection()) {
            connection.setAutoCommit(false);
            try {
                NewGameRoom newGameRoom = gamePersistenceMapper.toNewGameRoom(game);
                long roomId = gameRoomDao.save(newGameRoom, connection);
                List<BoardPiece> boardPieces = gamePersistenceMapper.toBoardPieces(roomId, game);
                boardPieceDao.insertAll(boardPieces, connection);

                connection.commit();
                return roomId;
            } catch (SQLException | RuntimeException e) {
                rollback(connection);
                throw new IllegalStateException(CREATE_GAME_FAIL_MESSAGE, e);
            }
        } catch (SQLException e) {
            throw new IllegalStateException(CREATE_GAME_FAIL_MESSAGE, e);
        }
    }

    @Override
    public Game enterGame(long roomId) {
        try (Connection connection = connectionManager.getConnection()) {
            GameRoom gameRoom = gameRoomDao.findById(roomId, connection)
                    .orElseThrow(() -> new IllegalArgumentException(GAME_ROOM_NOT_FOUND_MESSAGE));
            List<BoardPiece> boardPieces = boardPieceDao.findAllByGameRoomId(roomId, connection);

            return gamePersistenceMapper.restore(gameRoom, boardPieces);
        } catch (SQLException e) {
            throw new IllegalStateException(ENTER_GAME_FAIL_MESSAGE, e);
        }
    }

    @Override
    public void saveMove(long roomId, Game game, TurnDto turnDto) {
        try (Connection connection = connectionManager.getConnection()) {
            connection.setAutoCommit(false);
            try {
                gameRoomDao.update(gamePersistenceMapper.toGameRoom(roomId, game), connection);
                boardPieceDao.deleteByPosition(roomId, turnDto.endRow(), turnDto.endCol(), connection);
                boardPieceDao.updatePosition(roomId,
                        turnDto.startRow(),
                        turnDto.startCol(),
                        turnDto.endRow(),
                        turnDto.endCol(),
                        connection);

                connection.commit();
            } catch (SQLException | RuntimeException e){
                rollback(connection);
                throw new IllegalStateException(MOVE_SAVE_FAIL_MESSAGE, e);
            }
        } catch (SQLException e) {
            throw new IllegalStateException(MOVE_SAVE_FAIL_MESSAGE, e);
        }
    }

    private void rollback(Connection connection) {
        try {
            connection.rollback();
        } catch (SQLException ignored) {
        }
    }
}
