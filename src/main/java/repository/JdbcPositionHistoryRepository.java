package repository;

import domain.Position;
import domain.piece.PieceInfo;
import domain.piece.PieceInfos;
import dto.PositionState;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcPositionHistoryRepository implements PositionHistoryRepository {
    @Override
    public List<PositionState> findPositionHistoriesByTurnHistoryId(int turnHistoryId, Connection connection) {
        List<PositionState> positionHistories = new ArrayList<>();
        String sql = "SELECT * FROM `position_history` WHERE `turn_history_id` = ?";
        try (
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {
            preparedStatement.setInt(1, turnHistoryId);
            ResultSet resultSet = preparedStatement.executeQuery();
            addSnapshots(resultSet, positionHistories);
            return positionHistories;
        } catch (SQLException e) {
            throw new IllegalStateException("[ERROR] 보드 스냅샷을 불러오는 데 실패했습니다.", e);
        }
    }

    private void addSnapshots(ResultSet resultSet, List<PositionState> positionHistories) throws SQLException {
        while (resultSet.next()) {
            int x = resultSet.getInt("position_x");
            int y = resultSet.getInt("position_y");
            String pieceType = resultSet.getString("piece_type");
            String pieceCountry = resultSet.getString("piece_country");
            PositionState positionHistory = new PositionState(x, y, pieceType, pieceCountry);
            positionHistories.add(positionHistory);
        }
    }

    @Override
    public void savePositionHistory(PieceInfos pieceInfos, int turnHistoryId, Connection connection) {
        String sql = "INSERT INTO `position_history` (`position_x`, `position_y`, `piece_type`, `piece_country`, `turn_history_id`) VALUES (?, ?, ?, ?, ?)";
        try (
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {
            for (Position position : pieceInfos.getKeys()) {
                PieceInfo pieceInfo = pieceInfos.get(position);
                preparedStatement.setInt(1, position.x());
                preparedStatement.setInt(2, position.y());
                preparedStatement.setString(3, pieceInfo.pieceType().name());
                preparedStatement.setString(4, pieceInfo.countryType().name());
                preparedStatement.setInt(5, turnHistoryId);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new IllegalStateException("[ERROR] 보드 스냅샷 생성에 실패했습니다.", e);
        }
    }

    @Override
    public void deletePositionHistoriesByTurnHistoryId(int turnHistoryId, Connection connection) {
        String sql = "DELETE FROM `position_history` WHERE `turn_history_id` = ?";
        try (
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {
            preparedStatement.setInt(1, turnHistoryId);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("[ERROR] 특정 보드의 모든 보드 스냅샷 삭제에 실패했습니다.", e);
        }
    }
}
