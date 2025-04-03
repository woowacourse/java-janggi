package dao.piece;

import dao.converter.PieceDto;
import domain.piece.character.PieceType;
import domain.piece.character.Team;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PieceQueryDaoImpl implements PieceQueryDao {

    public List<PieceDto> findByGameRoomName(Connection connection, String gameRoomName) {
        String sql = """               
                SELECT row_index, column_index, piece_type, team, game_room_name
                FROM piece p
                WHERE p.game_room_name = ?;
                """;
        List<PieceDto> pieces = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, gameRoomName);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    pieces.add(new PieceDto(
                            null,
                            resultSet.getInt("row_index"),
                            resultSet.getInt("column_index"),
                            PieceType.valueOf(resultSet.getString("piece_type")),
                            Team.valueOf(resultSet.getString("team")),
                            resultSet.getString("game_room_name")
                    ));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("[ERROR] DB 조회에 실패했습니다 : " + e.getMessage());
        }
        return pieces;
    }
}
