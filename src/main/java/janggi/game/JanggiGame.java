package janggi.game;

import janggi.board.Board;
import janggi.board.TableOption;
import janggi.board.Turn;
import janggi.dao.BoardDao;
import janggi.dao.GameRoomDao;
import janggi.piece.Piece;
import janggi.piece.PieceGenerator;
import janggi.position.Position;
import janggi.team.Team;

import java.util.List;
import java.util.Map;

public class JanggiGame {
    private final BoardDao boardDao;
    private final GameRoomDao gameRoomDao;

    public JanggiGame(BoardDao boardDao, GameRoomDao gameRoomDao) {
        this.boardDao = boardDao;
        this.gameRoomDao = gameRoomDao;
    }

    public List<String> findAllGameRoom() {
        return gameRoomDao.findAllGameRoom();
    }

    public GameRoom selectGameRoom(String roomName) {
        GameRoom gameRoom;
        if (gameRoomDao.existsGameRoom(roomName)) {
            gameRoom = gameRoomDao.findByRoomName(roomName);
        } else {
            gameRoom = new GameRoom(roomName, new Turn(Team.CHO));
            gameRoomDao.saveGameRoom(gameRoom);
        }
        return gameRoom;
    }

    public boolean existsBoardPieceByRoomName(GameRoom gameRoom) {
        return boardDao.existsBoardPieceByRoomName(gameRoom.getRoomName());
    }

    public Board makeSavedBoard(GameRoom gameRoom) {
        Map<Position, Piece> initialPieces;
        initialPieces = boardDao.findAllBoardPieceByRoomName(gameRoom.getRoomName());
        return new Board(initialPieces);
    }

    public Board makeInitialBoard(GameRoom gameRoom, TableOption choTableOption, TableOption hanTableOption) {
        Map<Position, Piece> initialPieces;
        initialPieces = new PieceGenerator().generateInitialPieces(hanTableOption, choTableOption);
        boardDao.saveAllBoardPiece(initialPieces, gameRoom);
        return new Board(initialPieces);
    }
}
