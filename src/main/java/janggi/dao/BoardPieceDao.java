package janggi.dao;

import janggi.piece.PieceCreator;
import janggi.PieceType;
import janggi.Team;
import janggi.board.Board;
import janggi.board.position.Column;
import janggi.board.position.Position;
import janggi.board.position.Row;
import janggi.piece.Piece;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class BoardPieceDao {
    private static final String url = "jdbc:mysql://localhost:13306/chess?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String username = "root";
    private static final String password = "root";

    public void saveAll(int gameId, Map<Position, Piece> board) {
        try (Connection connection = getConnection()) {
            connection.setAutoCommit(false);
            for (Entry<Position, Piece> entry : board.entrySet()) {
                save(connection, gameId, entry);
            }
            connection.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void save(Connection connection, int gameId, Entry<Position, Piece> entry) throws SQLException {
        String query = "insert into board_piece(game_id, column_value, row_value, piece_type, team) values(?, ?, ?, ?, ?);";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            Position position = entry.getKey();
            Piece piece = entry.getValue();
            preparedStatement.setInt(1, gameId);
            preparedStatement.setInt(2, position.getColumn().getValue());
            preparedStatement.setInt(3, position.getRow().getValue());
            preparedStatement.setString(4, piece.getType().name());
            preparedStatement.setString(5, piece.getTeam().name());
            preparedStatement.executeUpdate();
        }
    }

    public Board loadBoard(int gameId) {
        String query = "select * from board_piece where game_id = ?;";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
        ) {
            preparedStatement.setInt(1, gameId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                Map<Position, Piece> board = new HashMap<>();
                while (resultSet.next()) {
                    int column = resultSet.getInt("column_value");
                    int row = resultSet.getInt("row_value");
                    PieceType pieceType = PieceType.valueOf(resultSet.getString("piece_type"));
                    Team team = Enum.valueOf(Team.class, resultSet.getString("team"));
                    Position position = new Position(Column.from(column), Row.from(row));
                    Piece piece = PieceCreator.create(team, pieceType);
                    board.putIfAbsent(position, piece);
                }
                return new Board(board);
            }
        } catch (SQLException e) {
            throw new RuntimeException("DB 오류 발생");
        }
    }

    public void delete(int gameId, Position position) {
        String query = "delete from board_piece where game_id = ? and column_value = ? and row_value = ?;";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)
        ) {
            preparedStatement.setInt(1, gameId);
            preparedStatement.setInt(2, position.getColumn().getValue());
            preparedStatement.setInt(3, position.getRow().getValue());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("DB 오류 발생");
        }
    }

    public void updatePiecePosition(int gameId, Position before, Position after) {
        String query = "update board_piece set column_value = ?, row_value = ? where game_id = ? and column_value = ? and row_value = ?;";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)
        ) {
            preparedStatement.setInt(1, after.getColumn().getValue());
            preparedStatement.setInt(2, after.getRow().getValue());
            preparedStatement.setInt(3, gameId);
            preparedStatement.setInt(4, before.getColumn().getValue());
            preparedStatement.setInt(5, before.getRow().getValue());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("DB 오류 발생");
        }
    }

    public void deleteAll(int gameId) {
        String query = "delete from board_piece where game_id = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)
        ) {
            preparedStatement.setInt(1, gameId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("DB 오류 발생");
        }
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }
}
