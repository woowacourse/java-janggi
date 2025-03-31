package janggi.dao;

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
    private static final String url = System.getenv("DB_URL");
    private static final String username = System.getenv("DB_USERNAME");
    private static final String password = System.getenv("DB_PASSWORD");

    public void saveAll(Map<Position, Piece> board) {
        String query = "insert into board_piece(game_id, column_value, row_value, piece_type, team) values(?, ?, ?, ?, ?);";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)
        ) {
            for (Entry<Position, Piece> entry : board.entrySet()) {
                save(entry, preparedStatement);
            }
        } catch (SQLException e) {
            throw new RuntimeException("DB 오류 발생");
        }
    }

    private void save(Entry<Position, Piece> entry, PreparedStatement preparedStatement) throws SQLException {
        Position position = entry.getKey();
        Piece piece = entry.getValue();
        PieceType pieceType = PieceType.from(piece);
        Row row = position.getRow();
        Column column = position.getColumn();
        preparedStatement.setInt(1, 1);
        preparedStatement.setInt(2, column.getValue());
        preparedStatement.setInt(3, row.getValue());
        preparedStatement.setString(4, pieceType.name());
        preparedStatement.setString(5, piece.getTeam().name());
        preparedStatement.executeUpdate();
    }

    public Board loadBoard() {
        String query = "select * from board_piece;";
        try (Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery()
        ) {
            Map<Position, Piece> board = new HashMap<>();
            while (resultSet.next()) {
                int column = resultSet.getInt("column_value");
                int row = resultSet.getInt("row_value");
                PieceType pieceType = PieceType.valueOf(resultSet.getString("piece_type"));
                Team team = Enum.valueOf(Team.class, resultSet.getString("team"));
                Position position = new Position(Column.from(column), Row.from(row));

                Piece piece = pieceType.toPiece(team);
                board.putIfAbsent(position, piece);
            }
            return new Board(board);
        } catch (SQLException e) {
            throw new RuntimeException("DB 오류 발생");
        }
    }

    public void delete(Position goalPosition) {
        String query = "delete from board_piece where column_value = ? and row_value = ?;";
        try (Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)
        ) {
            Column column = goalPosition.getColumn();
            Row row = goalPosition.getRow();
            preparedStatement.setInt(1, column.getValue());
            preparedStatement.setInt(2, row.getValue());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("DB 오류 발생");
        }
    }

    public void updatePiecePosition(Position startPosition, Position goalPosition) {
        String query = "update board_piece set column_value = ?, row_value = ? where column_value = ? and row_value = ?;";
        try (Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)
        ) {
            Column startColumn = startPosition.getColumn();
            Row startRow = startPosition.getRow();
            Column goalColumn = goalPosition.getColumn();
            Row goalRow = goalPosition.getRow();
            preparedStatement.setInt(1, goalColumn.getValue());
            preparedStatement.setInt(2, goalRow.getValue());
            preparedStatement.setInt(3, startColumn.getValue());
            preparedStatement.setInt(4, startRow.getValue());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("DB 오류 발생");
        }
    }

    public void deleteAll() {
        String query = "delete from board_piece where game_id = 1";
        try (Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)
        ) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("DB 오류 발생");
        }
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }
}
