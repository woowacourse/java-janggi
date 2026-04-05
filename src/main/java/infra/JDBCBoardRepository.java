package infra;

import domain.board.Board;
import domain.piece.Piece;
import domain.piece.PieceType;
import domain.piece.Side;
import domain.position.Position;
import dto.BoardResponseDto;
import dto.PieceDto;
import repository.BoardRepository;

import java.security.Key;
import java.sql.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class JDBCBoardRepository implements BoardRepository {

    public JDBCBoardRepository() {}

    @Override
    public void save(Board board) {

        String deleteSql = "DELETE FROM board";
        String insertSql = """
                INSERT INTO board (row_value, column_value, piece_type, side)
                VALUES (?, ?, ?, ?)
                """;

        try (Connection connection = DriverManager.getConnection(JDBCContext.URL, JDBCContext.USER, JDBCContext.PASSWORD)) {

            PreparedStatement deleteStatement = connection.prepareStatement(deleteSql);
            deleteStatement.executeUpdate();

            PreparedStatement insertStatement = connection.prepareStatement(insertSql);

            Map<Position, Piece> state = board.getState();
            for (Map.Entry<Position, Piece> entry : state.entrySet()) {
                Position position = entry.getKey();
                Piece piece = entry.getValue();

                insertStatement.setInt(1, position.getRow());
                insertStatement.setInt(2, position.getColumn());
                insertStatement.setString(3, piece.getPieceType().name());
                insertStatement.setString(4, piece.getSide().name());

                insertStatement.addBatch();
            }
            insertStatement.executeBatch();
        } catch (SQLException e) {
            throw new RuntimeException("DB 오류" + e);
        }
    }

    @Override
    public BoardResponseDto findAll() {
        String selectSql = "SELECT (row_value, column_value, piece_type, side) FROM board";

        try (Connection connection = DriverManager.getConnection(JDBCContext.URL, JDBCContext.USER, JDBCContext.PASSWORD);
             PreparedStatement statement = connection.prepareStatement(selectSql);
             ResultSet resultSet = statement.executeQuery()) {

            Map<Position, PieceDto> state = new LinkedHashMap<>();

            if (resultSet.next()) {
                int rowValue = resultSet.getInt("row_value");
                int columnValue = resultSet.getInt("column_value");
                String pieceTypeName = resultSet.getString("piece_type");
                String sideName = resultSet.getString("side");

                Position position = Position.of(rowValue, columnValue);
                Side side = Side.valueOf(sideName);
                PieceDto pieceDto = new PieceDto(pieceTypeName, side);
                state.put(position, pieceDto);
            }

            return new BoardResponseDto(state);
        } catch (SQLException e) {
            throw new RuntimeException("DB 오류" + e);
        }
    }
}
