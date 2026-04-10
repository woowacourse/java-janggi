package infra;

import domain.board.Board;
import domain.piece.Piece;
import domain.piece.PieceType;
import domain.piece.Side;
import domain.position.Position;
import repository.BoardRepository;

import java.sql.*;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

public class JDBCBoardRepository implements BoardRepository {

    public JDBCBoardRepository() {
    }

    @Override
    public void save(Connection connection, Long gameId, Board board) {
        String deleteSql = "DELETE FROM board WHERE game_id = ? ";
        String insertSql = """
                INSERT INTO board (game_id, row_value, column_value, piece_type, side)
                VALUES (?, ?, ?, ?, ?)
                """;

        try (
            PreparedStatement deleteStatement = connection.prepareStatement(deleteSql);
            PreparedStatement insertStatement = connection.prepareStatement(insertSql);
        ) {
            deleteStatement.setLong(1, gameId);
            deleteStatement.executeUpdate();

            Map<Position, Piece> state = board.getState();
            for (Map.Entry<Position, Piece> entry : state.entrySet()) {
                Position position = entry.getKey();
                Piece piece = entry.getValue();

                insertStatement.setLong(1, gameId);
                insertStatement.setInt(2, position.getRow());
                insertStatement.setInt(3, position.getColumn());
                insertStatement.setString(4, piece.getPieceType().name());
                insertStatement.setString(5, piece.getSide().name());

                insertStatement.addBatch();
            }
            insertStatement.executeBatch();
        } catch (SQLException e) {
            throw new RuntimeException("DB 오류", e);
        }
    }

    @Override
    public Optional<Board> findByGameId(Long gameId) {
        String selectSql = "SELECT row_value, column_value, piece_type, side FROM board WHERE game_id = ?";

        try (Connection connection = JDBCContext.getConnection();
             PreparedStatement statement = connection.prepareStatement(selectSql)) {

            statement.setLong(1, gameId);

            try (ResultSet resultSet = statement.executeQuery()) {
                Map<Position, Piece> state = new LinkedHashMap<>();
                while (resultSet.next()) {
                    int rowValue = resultSet.getInt("row_value");
                    int columnValue = resultSet.getInt("column_value");
                    PieceType pieceType = PieceType.valueOf(resultSet.getString("piece_type"));
                    Side side = Side.valueOf(resultSet.getString("side"));

                    Position position = Position.of(rowValue, columnValue);
                    Piece piece = Piece.of(side, pieceType);
                    state.put(position, piece);
                }

                return Optional.of(Board.of(state));
            }
        } catch (SQLException e) {
            throw new RuntimeException("DB 오류", e);
        }
    }
}
