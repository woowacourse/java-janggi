package service;

import dao.PieceDao;
import dao.RoomDao;
import domain.GameState;
import domain.Janggi;
import domain.position.Position;
import domain.position.Routes;
import domain.unit.Team;
import domain.unit.Unit;
import domain.unit.UnitType;
import domain.unit.Units;
import entity.Piece;
import entity.Room;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public void createJanggiGame(String roomId) {
        roomDao.save(new Room(roomId, GameState.PLAYING, Team.CHO));
        Janggi janggi = initGame();
        List<Piece> pieces = Piece.from(janggi);
        for (Piece piece : pieces) {
            pieceDao.save(piece, roomId);
        }
    }

    private Janggi initGame() {
        Map<Position, Unit> hanUnits = settingUnits(Team.HAN);
        Map<Position, Unit> choUnits = settingUnits(Team.CHO);
        Units totalUnits = Units.of(hanUnits, choUnits);
        return Janggi.of(totalUnits);
    }

    private Map<Position, Unit> settingUnits(Team team) {
        Map<Position, Unit> units = new HashMap<>();
        for (UnitType value : UnitType.values()) {
            units.putAll(UnitType.createDefaultUnits(value, team));
        }
        return units;
    }

    public Janggi loadJanggiGame(String roomId) {
        List<Piece> boardsByRoomId = pieceDao.findBoardsByRoomId(roomId);
        return Piece.toDomain(boardsByRoomId);
    }

    public void moveTo(String roomId, Position before, Position after) {
        Piece source = pieceDao.findBoardByPosition(roomId, before);
        Piece destination = pieceDao.findBoardByPosition(roomId, after);
        if (destination != null && destination.team().getOpposite() == source.team()) {
            pieceDao.delete(destination.boardId());
        }
        pieceDao.updatePosition(source.boardId(), after);
        Room room = roomDao.findRoomById(roomId);
        roomDao.updateTurn(roomId, room.turn().getOpposite());
    }

    public Routes findAllRoute(String roomId, int positionX, int positionY) {
        Janggi janggi = loadJanggiGame(roomId);
        return janggi.findMovableRoutesFrom(Position.of(positionX, positionY));
    }

    public void surrender(String roomId, Team winner) {
        GameState status = GameState.CHO_WIN;
        if (winner == Team.HAN) {
            status = GameState.HAN_WIN;
        }
        roomDao.updateStatus(roomId, status);
    }

    public boolean isGameEnd(String roomId) {
        Room room = roomDao.findRoomById(roomId);
        return room.status() != GameState.PLAYING;
    }

    public Team getWinner(String roomId) {
        Room room = roomDao.findRoomById(roomId);
        if (room.status() == GameState.PLAYING) {
            throw new IllegalStateException();
        }
        if (room.status() == GameState.CHO_WIN) {
            return Team.CHO;
        }
        return Team.HAN;
    }

    public double calculateScoreOf(String roomId, Team team) {
        List<Piece> pieces = pieceDao.findBoardsByRoomId(roomId);
        Janggi janggi = Piece.toDomain(pieces);
        return janggi.getScoreOf(team);
    }
}
