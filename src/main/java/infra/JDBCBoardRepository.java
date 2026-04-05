package infra;

import domain.board.Board;
import domain.piece.Piece;
import domain.piece.PieceType;
import domain.piece.Side;
import domain.position.Position;
import dto.BoardResponseDto;
import dto.PieceDto;
import repository.BoardRepository;

import java.sql.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class JDBCBoardRepository implements BoardRepository {

    public JDBCBoardRepository() {
    }

    @Override
    public void save(Long gameId, Board board) {
        String deleteSql = "DELETE FROM board WHERE game_id = ? ";
        String insertSql = """
                INSERT INTO board (game_id, row_value, column_value, piece_type, side)
                VALUES (?, ?, ?, ?, ?)
                """;

        try (Connection connection = DriverManager.getConnection(JDBCContext.URL, JDBCContext.USER, JDBCContext.PASSWORD)) {

            PreparedStatement deleteStatement = connection.prepareStatement(deleteSql);
            deleteStatement.setLong(1, gameId);
            deleteStatement.executeUpdate();

            PreparedStatement insertStatement = connection.prepareStatement(insertSql);
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
            throw new RuntimeException("DB 오류" + e);
        }
    }

    @Override
    public BoardResponseDto findByGameId(Long gameId) {
        String selectSql = "SELECT row_value, column_value, piece_type, side FROM board WHERE game_id = ?";

        try (Connection connection = DriverManager.getConnection(JDBCContext.URL, JDBCContext.USER, JDBCContext.PASSWORD);
             PreparedStatement statement = connection.prepareStatement(selectSql)) {

            statement.setLong(1, gameId);

            try (ResultSet resultSet = statement.executeQuery()) {
                Map<Position, PieceDto> state = new LinkedHashMap<>();
                while (resultSet.next()) {
                    int rowValue = resultSet.getInt("row_value");
                    int columnValue = resultSet.getInt("column_value");
                    String pieceTypeName = resultSet.getString("piece_type");
                    PieceType pieceType = PieceType.valueOf(pieceTypeName);
                    String sideName = resultSet.getString("side");
                    Side side = Side.valueOf(sideName);

                    Position position = Position.of(rowValue, columnValue);
                    PieceDto pieceDto = new PieceDto(pieceType.getName(), side);
                    state.put(position, pieceDto);
                }

                return new BoardResponseDto(state);
            }
        } catch (SQLException e) {
            throw new RuntimeException("DB 오류" + e);
        }
    }
}
