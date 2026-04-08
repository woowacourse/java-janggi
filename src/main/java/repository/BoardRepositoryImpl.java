package repository;

import domain.board.Country;
import domain.board.Position;
import domain.piece.PieceInfo;
import domain.piece.PieceType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

public class BoardRepositoryImpl implements BoardRepository {
    @Override
    public void saveAll(Connection connection, Long gameId, Map<Position, PieceInfo> pieceInfos) {
        String sql = "INSERT INTO board(x, y, piece_type, country, game_id) VALUES (?, ?, ?, ?, ?)";

        try (
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            for (Map.Entry<Position, PieceInfo> pieceInfo : pieceInfos.entrySet()) {
                statement.setInt(1, pieceInfo.getKey().x());
                statement.setInt(2, pieceInfo.getKey().y());
                statement.setString(3, pieceInfo.getValue().pieceType().getDbValue());
                statement.setString(4, pieceInfo.getValue().country().getDbValue());
                statement.setLong(5, gameId);

                statement.addBatch();
            }
            statement.executeBatch();
        } catch (SQLException exception) {
            throw new IllegalStateException("[ERROR] DB에 보드 데이터를 저장하는 도중, 오류가 발생했습니다.", exception);
        }
    }

    @Override
    public void save(Connection connection, Long gameId, Position to, PieceInfo pieceInfo) {
        String sql = "INSERT INTO board(x, y, piece_type, country, game_id) VALUES (?, ?, ?, ?, ?)";

        try (
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setInt(1, to.x());
            statement.setInt(2, to.y());
            statement.setString(3, pieceInfo.pieceType().getDbValue());
            statement.setString(4, pieceInfo.country().getDbValue());
            statement.setLong(5, gameId);
            statement.executeUpdate();
        } catch (SQLException exception) {
            throw new IllegalStateException("[ERROR] DB에 보드 데이터를 저장하는 도중, 오류가 발생했습니다.", exception);
        }
    }

    @Override
    public void delete(Connection connection, Long gameId, Position from) {
        String sql = "DELETE FROM board WHERE game_id = ? AND x = ? AND y = ?";

        try (
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setLong(1, gameId);
            statement.setInt(2, from.x());
            statement.setInt(3, from.y());
            statement.executeUpdate();
        } catch (SQLException exception) {
            throw new IllegalStateException("[ERROR] DB에서 보드 데이터를 삭제하는 도중, 오류가 발생했습니다.", exception);
        }
    }

    @Override
    public Map<Position, PieceInfo> findAllByGameId(Connection connection, Long gameId) {
        String sql = "SELECT  * FROM board WHERE game_id = ?";
        Map<Position, PieceInfo> pieceInfos = new LinkedHashMap<>();

        try (
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setLong(1, gameId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Position position = new Position(resultSet.getInt("x"), resultSet.getInt("y"));
                PieceType pieceType = PieceType.valueOf(resultSet.getString("piece_type"));
                Country country = Country.valueOf(resultSet.getString("country"));

                pieceInfos.put(position, new PieceInfo(pieceType, country));
            }
        } catch (SQLException exception) {
            throw new IllegalStateException("[ERROR] DB에서 모든 보드 데이터를 조회하는 도중, 오류가 발생했습니다.", exception);
        }
        return pieceInfos;
    }
}
