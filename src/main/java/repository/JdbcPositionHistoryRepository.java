package repository;

import domain.Position;
import domain.country.CountryType;
import domain.piece.PieceInfo;
import domain.piece.PieceInfos;
import dto.PositionHistory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcPositionHistoryRepository implements PositionHistoryRepository {
    @Override
    public List<PositionHistory> findPositionHistoriesByGameInfoId(int gameInfoId, Connection connection) {
        List<PositionHistory> positionHistories = new ArrayList<>();
        String sql = "SELECT * FROM `position_history` WHERE `id` = ? AND `game_info_id` = ?";
        try (
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {
            for (int id : getBoardSnapshotIds(connection)) {
                preparedStatement.setInt(1, id);
                preparedStatement.setInt(2, gameInfoId);
                ResultSet resultSet = preparedStatement.executeQuery();
                addSnapshots(resultSet, positionHistories);
            }
            return positionHistories;
        } catch (SQLException e) {
            throw new IllegalStateException("[ERROR] 보드 스냅샷을 불러오는 데 실패했습니다.", e);
        }
    }

    private void addSnapshots(ResultSet resultSet, List<PositionHistory> positionHistories) throws SQLException {
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            int x = resultSet.getInt("position_x");
            int y = resultSet.getInt("position_y");
            String pieceType = resultSet.getString("piece_type");
            String pieceCountry = resultSet.getString("piece_country");
            String turn = resultSet.getString("turn");
            PositionHistory positionHistory = new PositionHistory(id, x, y, pieceType, pieceCountry, turn);
            positionHistories.add(positionHistory);
        }
    }

    private List<Integer> getBoardSnapshotIds(Connection connection) {
        String sql = "SELECT DISTINCT `id` FROM `position_history`";
        try (
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {
            ResultSet resultSet = preparedStatement.executeQuery();

            List<Integer> ids = new ArrayList<>();
            while (resultSet.next()) {
                ids.add(resultSet.getInt("id"));
            }
            return ids;
        } catch (SQLException e) {
            throw new IllegalStateException("[ERROR] 보드 스냅샷 ID를 불러오는 데 실패했습니다.");
        }
    }

    @Override
    public void savePositionHistory(PieceInfos pieceInfos, int gameInfoId, CountryType turn, Connection connection) {
        String sql = "INSERT INTO `position_history` VALUES (?, ?, ?, ?, ?, ?, ?)";
        int snapshotId = getNextSnapshotId(connection);
        try (
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {
            for (Position position : pieceInfos.getKeys()) {
                PieceInfo pieceInfo = pieceInfos.get(position);
                preparedStatement.setInt(1, snapshotId);
                preparedStatement.setInt(2, position.x());
                preparedStatement.setInt(3, position.y());
                preparedStatement.setString(4, pieceInfo.pieceType().toString());
                preparedStatement.setString(5, pieceInfo.countryType().toString());
                preparedStatement.setInt(6, gameInfoId);
                preparedStatement.setString(7, turn.toString());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new IllegalStateException("[ERROR] 보드 스냅샷 생성에 실패했습니다.", e);
        }
    }

    private int getNextSnapshotId(Connection connection) {
        String sql = "SELECT MAX(id) FROM `position_history`";
        try (
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                ResultSet resultSet = preparedStatement.executeQuery()
        ) {
            if (resultSet.next()) {
                int maxId = resultSet.getInt(1);
                return maxId + 1;
            }
        } catch (SQLException e) {
            throw new IllegalStateException("[ERROR] 보드 스냅샷의 모든 ID를 불러오는 데 실패했습니다.", e);
        }
        return 1;
    }

    @Override
    public void deletePositionHistoriesByGameInfoId(int gameInfoId, Connection connection) {
        String sql = "DELETE FROM `position_history` WHERE `game_info_id` = ?";
        try (
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {
            preparedStatement.setInt(1, gameInfoId);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("[ERROR] 특정 보드의 모든 보드 스냅샷 삭제에 실패했습니다.", e);
        }
    }
}
