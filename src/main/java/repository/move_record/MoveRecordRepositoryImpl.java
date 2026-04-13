package repository.move_record;

import domain.piece.Side;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import repository.connector.Connector;
import repository.move_record.dto.MoveRecord;

public class MoveRecordRepositoryImpl implements MoveRecordRepository {

    private static final String SAVE_FAILED_MESSAGE = "데이터 저장에 실패했습니다.";
    private static final String LOAD_FAILED_MESSAGE = "데이터 조회에 실패했습니다.";

    private final Connector connector;

    public MoveRecordRepositoryImpl(Connector connector) {
        this.connector = connector;
    }

    @Override
    public void save(Long gameRecordId, MoveRecord moveRecord) {
        String sql = "INSERT INTO move_record(game_record_id, source_x, source_y, target_x, target_y, turn_side) "
            + "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = connector.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, gameRecordId);
            stmt.setInt(2, moveRecord.sourceX());
            stmt.setInt(3, moveRecord.sourceY());
            stmt.setInt(4, moveRecord.targetX());
            stmt.setInt(5, moveRecord.targetY());
            stmt.setString(6, moveRecord.turn().name());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(SAVE_FAILED_MESSAGE, e);
        }
    }

    @Override
    public List<MoveRecord> findAllByGameRecordId(Long gameRecordId) {
        String sql = "SELECT source_x, source_y, target_x, target_y, turn_side FROM move_record "
            + "WHERE game_record_id = ? ORDER BY id ASC";

        try (Connection conn = connector.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, gameRecordId);

            try (ResultSet rs = stmt.executeQuery()) {
                List<MoveRecord> moveRecords = new ArrayList<>();
                while (rs.next()) {
                    moveRecords.add(new MoveRecord(
                        rs.getInt("source_x"),
                        rs.getInt("source_y"),
                        rs.getInt("target_x"),
                        rs.getInt("target_y"),
                        Side.valueOf(rs.getString("turn_side"))
                    ));
                }
                return moveRecords;
            }
        } catch (SQLException e) {
            throw new RuntimeException(LOAD_FAILED_MESSAGE, e);
        }
    }
}
