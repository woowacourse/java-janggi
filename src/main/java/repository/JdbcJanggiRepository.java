package repository;

import domain.Position;
import domain.country.CountryType;
import domain.piece.PieceInfo;
import domain.piece.PieceInfos;
import dto.GameInfo;
import dto.PositionHistory;
import dto.PositionState;
import infrastructure.JdbcConnectionManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JdbcJanggiRepository implements JanggiRepository {
    private final JdbcConnectionManager jdbcConnectionManager;

    public JdbcJanggiRepository(JdbcConnectionManager jdbcConnectionManager) {
        this.jdbcConnectionManager = jdbcConnectionManager;
    }

    @Override
    public List<GameInfo> findAllGameInfos() {
        String sql = "SELECT * FROM `game_info`";
        try (
                Connection connection = jdbcConnectionManager.getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(sql)
        ) {
            List<GameInfo> gameInfos = new ArrayList<>();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String turn = resultSet.getString("turn");
                double cho_score = resultSet.getDouble("cho_score");
                double han_score = resultSet.getDouble("han_score");
                GameInfo gameInfo = new GameInfo(id, turn, cho_score, han_score);
                gameInfos.add(gameInfo);
            }
            return gameInfos;
        } catch (SQLException e) {
            throw new IllegalStateException("[ERROR] 모든 보드를 불러오는 데 실패했습니다.");
        }
    }

    @Override
    public GameInfo findGameInfoById(int id) {
        String sql = "SELECT * FROM `game_info` WHERE `id` = ?";
        try (
                Connection connection = jdbcConnectionManager.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (!resultSet.next()) {
                throw new IllegalArgumentException("[ERROR] 해당 번호의 board가 없습니다.");
            }

            String turn = resultSet.getString("turn");
            double cho_score = resultSet.getDouble("cho_score");
            double han_score = resultSet.getDouble("han_score");

            return new GameInfo(id, turn, cho_score, han_score);
        } catch (SQLException e) {
            throw new IllegalStateException("[ERROR] 보드를 불러오는 데 실패했습니다.", e);
        }
    }

    @Override
    public int saveGameInfo() {
        String sql = "INSERT INTO `game_info`(`turn`, `cho_score`, `han_score`) VALUES (?, ?, ?)";
        try (
                Connection connection = jdbcConnectionManager.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            preparedStatement.setString(1, CountryType.CHO.toString());
            preparedStatement.setDouble(2, CountryType.CHO.getInitScore());
            preparedStatement.setDouble(3, CountryType.HAN.getInitScore());
            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (!resultSet.next()) {
                throw new IllegalStateException("[ERROR] 생성된 보드 ID를 가져오는 데 실패했습니다.");
            }
            return resultSet.getInt(1);
        } catch (SQLException e) {
            throw new IllegalStateException("[ERROR] 보드 생성에 실패했습니다.", e);
        }
    }

    @Override
    public void updateGameInfo(CountryType countryType, Map<CountryType, Double> scores, int id) {
        String sql = "UPDATE `game_info` SET `turn` = ?, `cho_score` = ?, `han_score` = ? WHERE `id` = ?";
        try (
                Connection connection = jdbcConnectionManager.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {
            preparedStatement.setString(1, countryType.toString());
            preparedStatement.setDouble(2, scores.get(CountryType.CHO));
            preparedStatement.setDouble(3, scores.get(CountryType.HAN));
            preparedStatement.setInt(4, id);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("[ERROR] 보드 업데이트에 실패했습니다.", e);
        }
    }

    @Override
    public void deleteGameInfo(int id) {
        String sql = "DELETE FROM `game_info` WHERE `id` = ?";
        try (
                Connection connection = jdbcConnectionManager.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("[ERROR] 보드 삭제에 실패했습니다.", e);
        }
    }

    @Override
    public List<PositionState> findAllPositionStatesByGameInfoId(int gameInfoId) {
        String sql = "SELECT * FROM `position_state` WHERE `game_info_id` = ?";
        try (
                Connection connection = jdbcConnectionManager.getConnection();
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
        } catch (SQLException e) {
            throw new IllegalStateException("[ERROR] 보드 상태를 불러오는 데 실패했습니다.");
        }
    }

    @Override
    public PositionState findPositionStateByPosition(Position position, int gameInfoId) {
        String sql = "SELECT * FROM `position_state` WHERE `position_x` = ? AND `position_y` = ? AND `game_info_id` = ?";
        try (
                Connection connection = jdbcConnectionManager.getConnection();
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
        } catch (SQLException e) {
            throw new IllegalStateException("[ERROR] 포지션을 확인하는 데 실패했습니다.", e);
        }
    }

    @Override
    public void savePositionState(Position position, PieceInfo pieceInfo, int gameInfoId) {
        String sql = "INSERT INTO `position_state` (`position_x`, `position_y`, `piece_type`, `piece_country`, `game_info_id`) VALUES (?, ?, ?, ?, ?)";
        try (
                Connection connection = jdbcConnectionManager.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {
            preparedStatement.setInt(1, position.x());
            preparedStatement.setInt(2, position.y());
            preparedStatement.setString(3, pieceInfo.pieceType().toString());
            preparedStatement.setString(4, pieceInfo.countryType().toString());
            preparedStatement.setInt(5, gameInfoId);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("[ERROR] 보드 상태 생성에 실패했습니다.", e);
        }
    }

    @Override
    public void updatePositionState(Position position, PieceInfo pieceInfo, int gameInfoId) {
        String sql = "UPDATE `position_state` SET `piece_type` = ?, `piece_country` = ? WHERE `position_x` = ? AND `position_y` = ? AND `game_info_id` = ?";
        try (
                Connection connection = jdbcConnectionManager.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {
            preparedStatement.setString(1, pieceInfo.pieceType().toString());
            preparedStatement.setString(2, pieceInfo.countryType().toString());
            preparedStatement.setInt(3, position.x());
            preparedStatement.setInt(4, position.y());
            preparedStatement.setInt(5, gameInfoId);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("[ERROR] 보드 상태 업데이트에 실패했습니다.", e);
        }
    }

    @Override
    public void deletePositionStateByPosition(Position position, int gameInfoId) {
        String sql = "DELETE FROM `position_state` WHERE `position_x` = ? AND `position_y` = ? AND `game_info_id` = ?";
        try (
                Connection connection = jdbcConnectionManager.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {
            preparedStatement.setInt(1, position.x());
            preparedStatement.setInt(2, position.y());
            preparedStatement.setInt(3, gameInfoId);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("[ERROR] 보드 상태 삭제에 실패했습니다.", e);
        }
    }

    @Override
    public void deleteAllPositionStatesByGameInfoId(int gameInfoId) {
        String sql = "DELETE FROM `position_state` WHERE `game_info_id` = ?";
        try (
                Connection connection = jdbcConnectionManager.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {
            preparedStatement.setInt(1, gameInfoId);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("[ERROR] 보드 상태 삭제에 실패했습니다.", e);
        }
    }

    @Override
    public List<PositionHistory> findPositionHistoriesByGameInfoId(int gameInfoId) {
        List<PositionHistory> positionHistories = new ArrayList<>();
        String sql = "SELECT * FROM `position_history` WHERE `id` = ? AND `game_info_id` = ?";
        try (
                Connection connection = jdbcConnectionManager.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {
            for (int id : getBoardSnapshotIds()) {
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

    private List<Integer> getBoardSnapshotIds() {
        String sql = "SELECT DISTINCT `id` FROM `position_history`";
        try (
                Connection connection = jdbcConnectionManager.getConnection();
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
    public void savePositionHistory(PieceInfos pieceInfos, int gameInfoId, CountryType turn) {
        String sql = "INSERT INTO `position_history` VALUES (?, ?, ?, ?, ?, ?, ?)";
        int snapshotId = getNextSnapshotId();
        try (
                Connection connection = jdbcConnectionManager.getConnection();
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

    private int getNextSnapshotId() {
        String sql = "SELECT MAX(id) FROM `position_history`";
        try (
                Connection connection = jdbcConnectionManager.getConnection();
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
    public void deletePositionHistoriesByGameInfoId(int gameInfoId) {
        String sql = "DELETE FROM `position_history` WHERE `game_info_id` = ?";
        try (
                Connection connection = jdbcConnectionManager.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {
            preparedStatement.setInt(1, gameInfoId);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("[ERROR] 특정 보드의 모든 보드 스냅샷 삭제에 실패했습니다.", e);
        }
    }
}
