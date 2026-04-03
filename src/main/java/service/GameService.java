package service;

import infra.DBExecutor;
import domain.place.Place;
import domain.place.piece.Side;
import domain.position.Position;
import dto.GameRoomDto;
import java.util.List;
import java.util.Map;
import repository.BoardRepository;
import repository.GameRoomRepository;

public class GameService {

    private final BoardRepository boardRepository;
    private final GameRoomRepository gameRoomRepository;
    private final DBExecutor dbExecutor;

    public GameService(BoardRepository boardRepository,
                       GameRoomRepository gameRoomRepository,
                       DBExecutor dbExecutor) {
        this.boardRepository = boardRepository;
        this.gameRoomRepository = gameRoomRepository;
        this.dbExecutor = dbExecutor;
    }

    public void saveGame(Map<Position, Place> board, String name, Side side) {
        dbExecutor.transaction(connection -> {
            long roomId = gameRoomRepository.save(name, side.getName(),connection);
            boardRepository.saveBoard(roomId, board, connection);
        });
    }

    public Map<Position, Place> findBoardByRoomId(long roomId) {
        return dbExecutor.transaction(connection -> {
            findGameRoomByRoomId(roomId);
            return boardRepository.findBoard(roomId, connection);
        });
    }

    public GameRoomDto findGameRoomByRoomId(long roomId){
        return dbExecutor.transaction(connection -> {
            return gameRoomRepository.findById(roomId, connection)
                    .orElseThrow(() -> new IllegalArgumentException("[ERROR] 해당 방은 없습니다."));
        });
    }

    public List<GameRoomDto> findGameRoomAll() {
            return dbExecutor.transaction(gameRoomRepository::findAll);
    }


}
