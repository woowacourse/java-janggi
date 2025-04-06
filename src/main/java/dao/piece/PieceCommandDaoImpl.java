package dao.piece;

import dao.converter.PieceDto;
import domain.point.Point;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;
import java.util.List;

public class PieceCommandDaoImpl implements PieceCommandDao {

    public void insert(Connection connection, PieceDto piece) {
        String sql = """
                INSERT INTO piece (row_index, column_index, piece_type, team, game_room_name)
                VALUES (?, ?, ?, ?, ?);
                """;
        executeQuery(connection, sql,
                List.of(piece.rowIndex(), piece.columnIndex(),
                        piece.pieceType().name(), piece.team().name(), piece.gameRoomName()));
    }

    public void insertAll(Connection connection, List<PieceDto> pieceDtos) {
        pieceDtos.forEach(pieceDto -> insert(connection, pieceDto));
    }

    public void updatePointByGameRoomNameAndPoint(Connection connection,
                                                  String gameRoomName, Point oldPoint, Point newPoint) {
        String sql = """
                UPDATE piece
                SET row_index = ?, column_index = ?
                WHERE row_index = ? AND column_index = ? AND game_room_name = ?
                """;
        executeQuery(connection, sql,
                List.of(newPoint.row(), newPoint.column(), oldPoint.row(), oldPoint.column(), gameRoomName));
    }

    public void deleteByGameRoomNameAndPoint(Connection connection,
                                             String gameRoomName, Point point) {
        String sql = """
                DELETE FROM piece
                WHERE game_room_name = ? AND row_index = ? AND column_index = ?
                """;
        executeQuery(connection, sql, List.of(gameRoomName, point.row(), point.column()));
    }

    private void executeQuery(Connection connection, String sql, List<Object> params) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            for (int i = 0; i < params.size(); i++) {
                preparedStatement.setObject(i + 1, params.get(i));
            }
            preparedStatement.executeUpdate();
        } catch (SQLSyntaxErrorException e) {
            throw new IllegalArgumentException("[ERROR]: 잘못된 형식의 쿼리문입니다. " + sql);
        } catch (SQLException e) {
            throw new IllegalStateException("[ERROR] DB 연결이 끊어졌습니다.");
        }
    }
}
