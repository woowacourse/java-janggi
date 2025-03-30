package domain;

import domain.dao.GamesDao;
import domain.piece.Team;
import java.util.List;

public class GameRooms {

    private final GamesDao gamesDao;

    public GameRooms(GamesDao gamesDao) {
        this.gamesDao = gamesDao;
    }

    public JanggiGame createRoom(String roomName) {
        return gamesDao.add(roomName, Team.getFirstTeam());
    }

    public JanggiGame findByName(String roomName) {
        return gamesDao.findByName(roomName);
    }

    public List<String> findAllRoomNames() {
        return gamesDao.findAllName();
    }

    public boolean isEmpty() {
        return gamesDao.countAll().equals(0L);
    }
}
