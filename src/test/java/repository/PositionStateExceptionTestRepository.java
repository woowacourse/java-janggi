package repository;

import domain.Position;
import domain.piece.PieceInfo;
import dto.PositionState;
import java.sql.Connection;
import java.util.List;

public class PositionStateExceptionTestRepository implements PositionStateRepository {

    @Override
    public List<PositionState> findAllPositionStatesByGameInfoId(int gameInfoId, Connection connection) {
        throw new IllegalStateException();
    }

    @Override
    public PositionState findPositionStateByPosition(Position position, int gameInfoId, Connection connection) {
        throw new IllegalStateException();
    }

    @Override
    public void savePositionState(Position position, PieceInfo pieceInfo, int gameInfoId, Connection connection) {
        throw new IllegalStateException();
    }

    @Override
    public void updatePositionState(Position position, PieceInfo pieceInfo, int gameInfoId, Connection connection) {
        throw new IllegalStateException();
    }

    @Override
    public void deleteAllPositionStatesByGameInfoId(int gameInfoId, Connection connection) {
        throw new IllegalStateException();
    }

    @Override
    public void deletePositionStateByPosition(Position position, int gameInfoId, Connection connection) {
        throw new IllegalStateException();
    }
}
