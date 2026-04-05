package repository;

import dto.PieceDto;
import dto.PieceSnapshot;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface JanggiRepository {

    void createTable(Connection connection) throws SQLException;

    List<PieceDto> findPiecesByGameId(Connection connection, int gameId) throws SQLException;

    void save(Connection connection, int gameId, List<PieceSnapshot> pieceSnapshots) throws SQLException;

    void updateFrom(Connection connection, int gameId, List<Integer> from) throws SQLException;

    PieceDto findPieceByPosition(Connection connection, int gameId, List<Integer> from) throws SQLException;

    void updateTo(Connection connection, int gameId, List<Integer> to, String pieceType, String team) throws SQLException;
}
