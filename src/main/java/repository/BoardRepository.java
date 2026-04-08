package repository;

import dto.BoardRowDetail;
import dto.BoardRowDetails;

import java.sql.Connection;
import java.util.List;

public interface BoardRepository {

    void createTable(Connection connection);

    List<BoardRowDetail> findPiecesByGameId(Connection connection, int gameId);

    void save(Connection connection, int gameId, BoardRowDetails boardRowDetails);

    void updateFrom(Connection connection, int gameId, List<Integer> from);

    BoardRowDetail findPieceByPosition(Connection connection, int gameId, List<Integer> from);

    void updateTo(Connection connection, int gameId, List<Integer> to, String pieceType, String team);
}
