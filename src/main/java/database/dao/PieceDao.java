package database.dao;

import database.dto.PieceDto;
import domain.position.Position;
import java.sql.Connection;
import java.util.List;

public interface PieceDao {
    void saveAll(int gameId, List<PieceDto> pieces);

    List<PieceDto> findAll(int gameId);

    void updatePosition(Connection connection, int gameId, Position src, Position dest);

    void delete(Connection connection, int gameId, Position position);
}
