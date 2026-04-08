package repository;

import domain.country.CountryType;
import dto.GameInfo;
import java.sql.Connection;
import java.util.List;
import java.util.Map;

public interface GameInfoRepository {
    List<GameInfo> findAllGameInfos(Connection connection);

    GameInfo findGameInfoById(int id, Connection connection);

    int saveGameInfo(Connection connection);

    void updateGameInfo(CountryType countryType, Map<CountryType, Double> scores, int id, Connection connection);

    void deleteGameInfo(int id, Connection connection);
}
