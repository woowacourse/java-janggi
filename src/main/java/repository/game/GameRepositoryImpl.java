package repository.game;

import domain.Formation;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import repository.connector.Connector;

public class GameRepositoryImpl implements GameRepository {

    private static final String SAVE_FAILED_MESSAGE = "데이터 저장에 실패했습니다.";
    private static final String EXISTS_FAILED_MESSAGE = "데이터 존재 여부 조회에 실패했습니다.";
    private static final String LOAD_FAILED_MESSAGE = "데이터 조회에 실패했습니다.";
    private static final String DELETE_ALL_FAILED_MESSAGE = "데이터 삭제에 실패했습니다.";

    private final Connector connector;

    public GameRepositoryImpl(Connector connector) {
        this.connector = connector;
    }

    @Override
    public boolean existsGameRecord() {
        String sql = "SELECT EXISTS(SELECT 1 FROM game_record)";
        try (Connection conn = connector.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1) == 1;
            }
            return false;
        } catch (SQLException e) {
            throw new RuntimeException(EXISTS_FAILED_MESSAGE, e);
        }
    }

    @Override
    public GameRecord findGameRecord() {
        String sql = "SELECT cho_formation, han_formation FROM game_record LIMIT 1";
        try (Connection conn = connector.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()) {
            if (!rs.next()) {
                throw new IllegalStateException("저장된 게임이 없습니다.");
            }

            Formation choFormation = parseFormation(rs.getString("cho_formation"));
            Formation hanFormation = parseFormation(rs.getString("han_formation"));
            return new GameRecord(choFormation, hanFormation);
        } catch (SQLException e) {
            throw new RuntimeException(LOAD_FAILED_MESSAGE, e);
        }
    }

    @Override
    public void save(GameRecord gameRecord) {
        String sql = "INSERT INTO game_record(cho_formation, han_formation) VALUES (?, ?)";
        try (Connection conn = connector.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, gameRecord.choFormation().getOption());
            stmt.setString(2, gameRecord.hanFormation().getOption());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(SAVE_FAILED_MESSAGE, e);
        }
    }

    @Override
    public void deleteAll() {
        String sql = "DELETE FROM game_record";
        try (Connection conn = connector.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(DELETE_ALL_FAILED_MESSAGE, e);
        }
    }

    private Formation parseFormation(String value) {
        try {
            return Formation.from(value);
        } catch (IllegalArgumentException ignored) {
            return Formation.valueOf(value);
        }
    }
}
