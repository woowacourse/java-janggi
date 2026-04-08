package repository;

import domain.Position;
import domain.country.CountryType;
import domain.piece.PieceInfo;
import domain.piece.PieceInfos;
import dto.GameInfo;
import dto.PositionHistory;
import dto.PositionState;
import java.sql.Connection;
import java.util.List;
import java.util.Map;

public interface JanggiRepository {
    // Game 관련
    List<GameInfo> findAllGameInfos(Connection connection);

    GameInfo findGameInfoById(int id, Connection connection);

    int saveGameInfo(Connection connection);

    void updateGameInfo(CountryType countryType, Map<CountryType, Double> scores, int id, Connection connection);

    void deleteGameInfo(int id, Connection connection);

    // Position 관련
    List<PositionState> findAllPositionStatesByGameInfoId(int gameInfoId, Connection connection);

    PositionState findPositionStateByPosition(Position position, int gameInfoId, Connection connection);

    void savePositionState(Position position, PieceInfo pieceInfo, int gameInfoId, Connection connection);

    void updatePositionState(Position position, PieceInfo pieceInfo, int gameInfoId, Connection connection);

    void deleteAllPositionStatesByGameInfoId(int gameInfoId, Connection connection);

    void deletePositionStateByPosition(Position position, int gameInfoId, Connection connection);

    // History 관련
    List<PositionHistory> findPositionHistoriesByGameInfoId(int gameInfoId, Connection connection);

    void savePositionHistory(PieceInfos pieceInfos, int gameInfoId, CountryType turn, Connection connection);

    void deletePositionHistoriesByGameInfoId(int gameInfoId, Connection connection);
}
