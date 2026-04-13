package domain.piece;

import repository.entity.PieceEntity;

import java.sql.Connection;
import java.util.List;

public interface PieceRepository {

    List<PieceEntity> findByGameId(Connection connection, long gameId);

    void move(Connection connection, long gameId, int fromRow, int fromColumn, int toRow, int toColumn);

    void delete(Connection connection, long gameId, int row, int column);

    void saveAll(Connection connection, long gameId, List<PieceEntity> pieces);
}
