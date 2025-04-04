package service;

import dao.PieceDao;
import dao.RoomDao;
import domain.GameState;
import domain.Janggi;
import domain.position.Position;
import domain.unit.Team;
import entity.Piece;
import entity.Room;
import java.util.List;

public class JanggiService {
    private final PieceDao pieceDao;
    private final RoomDao roomDao;

    public JanggiService(PieceDao pieceDao, RoomDao roomDao) {
        this.pieceDao = pieceDao;
        this.roomDao = roomDao;
    }

    public List<Room> findAllPlayingRoom() {
        return roomDao.findAllPlayingRoom();
    }

    public boolean existsRoom(String roomId) {
        return roomDao.existsByRoomId(roomId);
    }

    public void createJanggiRoom(String roomId, Janggi janggi) {
        roomDao.save(new Room(roomId, GameState.PLAYING, Team.CHO));
        List<Piece> pieces = Piece.from(janggi);
        for (Piece piece : pieces) {
            pieceDao.save(piece, roomId);
        }
    }

    public Janggi loadJanggiGame(String roomId) {
        List<Piece> boardsByRoomId = pieceDao.findBoardsByRoomId(roomId);
        Room room = roomDao.findRoomById(roomId);
        return Piece.toDomain(room, boardsByRoomId);
    }

    public void movePiece(String roomId, Position before, Position after) {
        Piece source = pieceDao.findBoardByPosition(roomId, before);
        Piece destination = pieceDao.findBoardByPosition(roomId, after);
        if (destination != null && destination.team().getOpposite() == source.team()) {
            pieceDao.delete(destination.pieceId());
        }
        pieceDao.updatePosition(source.pieceId(), after);
        Room room = roomDao.findRoomById(roomId);
        roomDao.updateTurn(roomId, room.turn().getOpposite());
    }

    public void endGame(String roomId, Team winner) {
        GameState status = GameState.CHO_WIN;
        if (winner == Team.HAN) {
            status = GameState.HAN_WIN;
        }
        roomDao.updateStatus(roomId, status);
    }
}
