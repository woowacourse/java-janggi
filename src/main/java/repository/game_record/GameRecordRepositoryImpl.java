package repository.game_record;

import domain.board.Formation;
import domain.game.GameStatus;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import repository.connector.Connector;
import repository.game_record.dto.GameRecord;

public class GameRecordRepositoryImpl implements GameRecordRepository {

    private static final String GAME_NOT_FOUND_MESSAGE = "저장된 게임이 없습니다.";
    private static final String SAVE_FAILED_MESSAGE = "데이터 저장에 실패했습니다.";
    private static final String UPDATE_FAILED_MESSAGE = "데이터 수정에 실패했습니다.";
    private static final String LIST_FAILED_MESSAGE = "데이터 목록 조회에 실패했습니다.";
    private static final String LOAD_FAILED_MESSAGE = "데이터 조회에 실패했습니다.";

    private final Connector connector;

    public GameRecordRepositoryImpl(Connector connector) {
        this.connector = connector;
    }

    @Override
    public List<GameRecord> findAllGameRecordsByGameStatus(GameStatus status) {
        String sql = "SELECT id, cho_formation, han_formation, game_status FROM game_record "
            + "WHERE game_status = ? ORDER BY id ASC";
        try (Connection conn = connector.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, status.name());

            try (ResultSet rs = stmt.executeQuery()) {
                List<GameRecord> gameRecords = new ArrayList<>();
                while (rs.next()) {
                    gameRecords.add(mapGameRecord(rs));
                }
                return gameRecords;
            }
        } catch (SQLException e) {
            throw new RuntimeException(LIST_FAILED_MESSAGE, e);
        }
    }

    @Override
    public GameRecord findGameRecordByGameStatus(GameStatus status) {
        String sql = "SELECT id, cho_formation, han_formation, game_status FROM game_record "
            + "WHERE game_status = ?";
        try (Connection conn = connector.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, status.name());

            try (ResultSet rs = stmt.executeQuery()) {
                if (!rs.next()) {
                    throw new IllegalStateException(GAME_NOT_FOUND_MESSAGE);
                }

                return mapGameRecord(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException(LOAD_FAILED_MESSAGE, e);
        }
    }

    @Override
    public void save(GameRecord gameRecord) {
        String sql = "INSERT INTO game_record(cho_formation, han_formation, game_status) VALUES (?, ?, ?)";
        try (Connection conn = connector.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, gameRecord.choFormation().getOption());
            stmt.setString(2, gameRecord.hanFormation().getOption());
            stmt.setString(3, gameRecord.gameStatus().name());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(SAVE_FAILED_MESSAGE, e);
        }
    }

    @Override
    public void updateGameStatus(Long gameRecordId, GameStatus gameStatus) {
        String sql = "UPDATE game_record SET game_status = ? WHERE id = ?";

        try (Connection conn = connector.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, gameStatus.name());
            stmt.setLong(2, gameRecordId);

            int result = stmt.executeUpdate();

            if (result == 0) {
                throw new IllegalStateException(GAME_NOT_FOUND_MESSAGE);
            }

        } catch (SQLException e) {
            throw new RuntimeException(UPDATE_FAILED_MESSAGE, e);
        }
    }

    @Override
    public void updateGameStatuses(GameStatus sourceStatus, GameStatus targetStatus) {
        String sql = "UPDATE game_record SET game_status = ? WHERE game_status = ?";

        try (Connection conn = connector.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, targetStatus.name());
            stmt.setString(2, sourceStatus.name());
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(UPDATE_FAILED_MESSAGE, e);
        }
    }

    private Formation parseFormation(String value) {
        try {
            return Formation.from(value);
        } catch (IllegalArgumentException ignored) {
            return Formation.valueOf(value);
        }
    }

    private GameRecord mapGameRecord(ResultSet rs) throws SQLException {
        Formation choFormation = parseFormation(rs.getString("cho_formation"));
        Formation hanFormation = parseFormation(rs.getString("han_formation"));
        GameStatus gameStatus = GameStatus.valueOf(rs.getString("game_status"));
        return new GameRecord(rs.getLong("id"), choFormation, hanFormation, gameStatus);
    }
}
