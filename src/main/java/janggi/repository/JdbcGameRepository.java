package janggi.repository;

import janggi.dao.BoardPieceDao;
import janggi.dao.GameRoomDao;
import janggi.domain.Game;
import janggi.dto.BoardPiece;
import janggi.dto.GameRoom;
import janggi.dto.NewGameRoom;
import janggi.dto.TurnDto;
import janggi.mapper.GamePersistenceMapper;

import java.sql.Connection;
import java.util.List;

public class JdbcGameRepository implements GameRepository {
    private static final String GAME_ROOM_NOT_FOUND_MESSAGE = "존재하지 않는 게임방입니다.";

    private final GameRoomDao gameRoomDao;
    private final BoardPieceDao boardPieceDao;
    private final GamePersistenceMapper gamePersistenceMapper;

    public JdbcGameRepository(
            GameRoomDao gameRoomDao,
            BoardPieceDao boardPieceDao,
            GamePersistenceMapper gamePersistenceMapper
    ) {
        this.gameRoomDao = gameRoomDao;
        this.boardPieceDao = boardPieceDao;
        this.gamePersistenceMapper = gamePersistenceMapper;
    }

    @Override
    public long saveNewGame(Game game, Connection connection) {
        NewGameRoom newGameRoom = gamePersistenceMapper.toNewGameRoom(game);
        long roomId = gameRoomDao.save(newGameRoom, connection);
        List<BoardPiece> boardPieces = gamePersistenceMapper.toBoardPieces(roomId, game);
        boardPieceDao.insertAll(boardPieces, connection);
        return roomId;
    }

    @Override
    public Game enterGame(long roomId, Connection connection) {
        GameRoom gameRoom = gameRoomDao.findById(roomId, connection)
                .orElseThrow(() -> new IllegalArgumentException(GAME_ROOM_NOT_FOUND_MESSAGE));
        List<BoardPiece> boardPieces = boardPieceDao.findAllByGameRoomId(roomId, connection);
        return gamePersistenceMapper.restore(gameRoom, boardPieces);
    }

    @Override
    public void saveMove(long roomId, Game game, TurnDto turnDto, Connection connection) {
                gameRoomDao.update(gamePersistenceMapper.toGameRoom(roomId, game), connection);
                boardPieceDao.deleteByPosition(roomId, turnDto.endRow(), turnDto.endCol(), connection);
                boardPieceDao.updatePosition(roomId,
                        turnDto.startRow(),
                        turnDto.startCol(),
                        turnDto.endRow(),
                        turnDto.endCol(),
                        connection);
    }
}
