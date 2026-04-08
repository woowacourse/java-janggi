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
    private static final String DELETE_ALL_FAILED_MESSAGE = "데이터 삭제에 실패했습니다.";

    private final Connector connector;

    public MoveRecordRepositoryImpl(Connector connector) {
        this.connector = connector;
    }

    @Override
    public void save(MoveRecord moveRecord) {
        String sql = "INSERT INTO move_record(source_x, source_y, target_x, target_y, turn_side) "
            + "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = connector.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, moveRecord.sourceX());
            stmt.setInt(2, moveRecord.sourceY());
            stmt.setInt(3, moveRecord.targetX());
            stmt.setInt(4, moveRecord.targetY());
            stmt.setString(5, moveRecord.turn().name());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(SAVE_FAILED_MESSAGE);
        }
    }

    @Override
    public List<MoveRecord> findAll() {
        String sql = "SELECT source_x, source_y, target_x, target_y, turn_side FROM move_record";

        try (Connection conn = connector.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()) {
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
        } catch (SQLException e) {
            throw new RuntimeException(LOAD_FAILED_MESSAGE, e);
        }
    }

    @Override
    public void deleteAll() {
        String sql = "DELETE FROM move_record";
        try (Connection conn = connector.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(DELETE_ALL_FAILED_MESSAGE, e);
        }
    }
}
