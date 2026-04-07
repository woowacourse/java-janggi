package repository;

import domain.Position;
import domain.country.CountryType;
import domain.piece.PieceInfo;
import domain.piece.PieceInfos;
import dto.GameInfo;
import dto.PositionHistory;
import dto.PositionState;
import java.util.List;
import java.util.Map;

public interface JanggiRepository {
    // Game 관련
    List<GameInfo> findAllGameInfos();

    GameInfo findGameInfoById(int id);

    int saveGameInfo();

    void updateGameInfo(CountryType countryType, Map<CountryType, Double> scores, int id);

    void deleteGameInfo(int id);

    // Position 관련
    List<PositionState> findAllPositionStatesByGameInfoId(int gameInfoId);

    PositionState findPositionStateByPosition(Position position, int gameInfoId);

    void savePositionState(Position position, PieceInfo pieceInfo, int gameInfoId);

    void updatePositionState(Position position, PieceInfo pieceInfo, int gameInfoId);

    void deleteAllPositionStatesByGameInfoId(int gameInfoId);

    void deletePositionStateByPosition(Position position, int gameInfoId);

    // History 관련
    List<PositionHistory> findPositionHistoriesByGameInfoId(int gameInfoId);

    void savePositionHistory(PieceInfos pieceInfos, int gameInfoId, CountryType turn);

    void deletePositionHistoriesByGameInfoId(int gameInfoId);
}
