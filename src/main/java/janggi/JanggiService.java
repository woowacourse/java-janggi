package janggi;

import janggi.dao.GameRoom;
import janggi.domain.GameInfo;
import java.util.List;

public class JanggiService {
    private GameRoom gameRoom;

    public JanggiService(GameRoom gameRoom) {
        this.gameRoom = gameRoom;
    }

    public List<GameInfo> getEntireGame() {
        gameRoom.initTable();
        return gameRoom.findAllGames();
    }

    public void addGameData(String name, String createdDate, String lastUpdatedDate) {
        gameRoom.insertGame(name, createdDate, lastUpdatedDate);
    }

    public void removeGame(int id) {
        gameRoom.removeGame(id);
    }
}
