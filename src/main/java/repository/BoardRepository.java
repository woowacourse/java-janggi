package repository;

import dto.PieceDto;
import dto.PieceSnapshot;

import java.sql.Connection;
import java.util.List;

public interface BoardRepository {

    void createTable(Connection connection);

    List<PieceDto> findPiecesByGameId(Connection connection, int gameId);

    void save(Connection connection, int gameId, List<PieceSnapshot> pieceSnapshots);

    void updateFrom(Connection connection, int gameId, List<Integer> from);

    PieceDto findPieceByPosition(Connection connection, int gameId, List<Integer> from);

    void updateTo(Connection connection, int gameId, List<Integer> to, String pieceType, String team);
}
