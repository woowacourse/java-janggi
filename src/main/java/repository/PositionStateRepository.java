package repository;

import domain.Position;
import domain.piece.PieceInfo;
import dto.PositionState;
import java.sql.Connection;
import java.util.List;

public interface PositionStateRepository {
    List<PositionState> findAllPositionStatesByGameInfoId(int gameInfoId, Connection connection);

    PositionState findPositionStateByPosition(Position position, int gameInfoId, Connection connection);

    void savePositionState(Position position, PieceInfo pieceInfo, int gameInfoId, Connection connection);

    void updatePositionState(Position position, PieceInfo pieceInfo, int gameInfoId, Connection connection);

    void deleteAllPositionStatesByGameInfoId(int gameInfoId, Connection connection);

    void deletePositionStateByPosition(Position position, int gameInfoId, Connection connection);
}
