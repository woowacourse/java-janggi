package repository;

import domain.country.CountryType;
import dto.GameInfo;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameInfoTestRepository implements GameInfoRepository {
    private final Map<Integer, GameInfo> gameInfos = new HashMap<>();
    private int idSequence = 0;

    @Override
    public List<GameInfo> findAllGameInfos(Connection connection) {
        return gameInfos.values().stream()
                .toList();
    }

    @Override
    public GameInfo findGameInfoById(int id, Connection connection) {
        return gameInfos.get(id);
    }

    @Override
    public int saveGameInfo(Connection connection) {
        idSequence += 1;
        GameInfo gameInfo = new GameInfo(idSequence, CountryType.CHO.name());
        gameInfos.put(idSequence, gameInfo);
        return idSequence;
    }

    @Override
    public void updateGameInfo(CountryType countryType, int id, Connection connection) {
        if (!gameInfos.containsKey(id)) {
            return;
        }
        GameInfo gameInfo = new GameInfo(id, countryType.name());
        gameInfos.put(id, gameInfo);
    }

    @Override
    public void deleteGameInfo(int id, Connection connection) {
        gameInfos.remove(id);
    }
}
