package repository;

import domain.Position;
import domain.piece.PieceInfo;
import dto.PositionState;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcPositionStateRepository implements PositionStateRepository {
    private static final String FAIL_TO_LOAD_BOARD_STATE = "[ERROR] 보드 상태를 불러오는 데 실패했습니다.";
    private static final String FAIL_TO_LOAD_POSITION_STATE = "[ERROR] 포지션 상태를 불러오는 데 실패했습니다.";
    private static final String FAIL_TO_CREATE_POSITION_STATE = "[ERROR] 포지션 상태 생성에 실패했습니다.";
    private static final String FAIL_TO_UPDATE_POSITION_STATE = "[ERROR] 포지션 상태 업데이트에 실패했습니다.";
    private static final String FAIL_TO_DELETE_POSITION_STATE = "[ERROR] 포지션 상태 삭제에 실패했습니다.";
    private static final String FAIL_TO_DELETE_BOARD_STATE = "[ERROR] 보드 상태 삭제에 실패했습니다.";

    @Override
    public List<PositionState> findAllPositionStatesByGameInfoId(int gameInfoId, Connection connection) {
        String sql = "SELECT * FROM `position_state` WHERE `game_info_id` = ?";
        try (
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {
            preparedStatement.setInt(1, gameInfoId);
            ResultSet resultSet = preparedStatement.executeQuery();

            List<PositionState> positionStates = new ArrayList<>();

            while (resultSet.next()) {
                int x = resultSet.getInt("position_x");
                int y = resultSet.getInt("position_y");
                String pieceType = resultSet.getString("piece_type");
                String pieceCountry = resultSet.getString("piece_country");
                PositionState positionState = new PositionState(x, y, pieceType, pieceCountry);
                positionStates.add(positionState);
            }
            return positionStates;
        } catch (SQLException exception) {
            throw new IllegalStateException(FAIL_TO_LOAD_BOARD_STATE, exception);
        }
    }

    @Override
    public PositionState findPositionStateByPosition(Position position, int gameInfoId, Connection connection) {
        String sql = "SELECT * FROM `position_state` WHERE `position_x` = ? AND `position_y` = ? AND `game_info_id` = ?";
        try (
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {
            preparedStatement.setInt(1, position.x());
            preparedStatement.setInt(2, position.y());
            preparedStatement.setInt(3, gameInfoId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String pieceType = resultSet.getString("piece_type");
                String pieceCountry = resultSet.getString("piece_country");
                return new PositionState(position.x(), position.y(), pieceType, pieceCountry);
            }
            return null;
        } catch (SQLException exception) {
            throw new IllegalStateException(FAIL_TO_LOAD_POSITION_STATE, exception);
        }
    }

    @Override
    public void savePositionState(Position position, PieceInfo pieceInfo, int gameInfoId, Connection connection) {
        String sql = "INSERT INTO `position_state` (`position_x`, `position_y`, `piece_type`, `piece_country`, `game_info_id`) VALUES (?, ?, ?, ?, ?)";
        try (
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {
            preparedStatement.setInt(1, position.x());
            preparedStatement.setInt(2, position.y());
            preparedStatement.setString(3, pieceInfo.pieceType().toString());
            preparedStatement.setString(4, pieceInfo.countryType().toString());
            preparedStatement.setInt(5, gameInfoId);

            preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            throw new IllegalStateException(FAIL_TO_CREATE_POSITION_STATE, exception);
        }
    }

    @Override
    public void updatePositionState(Position position, PieceInfo pieceInfo, int gameInfoId, Connection connection) {
        String sql = "UPDATE `position_state` SET `piece_type` = ?, `piece_country` = ? WHERE `position_x` = ? AND `position_y` = ? AND `game_info_id` = ?";
        try (
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {
            preparedStatement.setString(1, pieceInfo.pieceType().toString());
            preparedStatement.setString(2, pieceInfo.countryType().toString());
            preparedStatement.setInt(3, position.x());
            preparedStatement.setInt(4, position.y());
            preparedStatement.setInt(5, gameInfoId);

            preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            throw new IllegalStateException(FAIL_TO_UPDATE_POSITION_STATE, exception);
        }
    }

    @Override
    public void deletePositionStateByPosition(Position position, int gameInfoId, Connection connection) {
        String sql = "DELETE FROM `position_state` WHERE `position_x` = ? AND `position_y` = ? AND `game_info_id` = ?";
        try (
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {
            preparedStatement.setInt(1, position.x());
            preparedStatement.setInt(2, position.y());
            preparedStatement.setInt(3, gameInfoId);

            preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            throw new IllegalStateException(FAIL_TO_DELETE_POSITION_STATE, exception);
        }
    }

    @Override
    public void deleteAllPositionStatesByGameInfoId(int gameInfoId, Connection connection) {
        String sql = "DELETE FROM `position_state` WHERE `game_info_id` = ?";
        try (
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {
            preparedStatement.setInt(1, gameInfoId);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException(FAIL_TO_DELETE_BOARD_STATE, e);
        }
    }
}
