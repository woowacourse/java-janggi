package domain.piece;

import domain.board.Board;

import java.sql.Connection;

public interface PieceRepository {

    Board findByGameId(Connection connection, long gameId);

    void saveAll(Connection connection, long gameId, Board board);

    void deleteAllByGameId(Connection connection, long gameId);
}
