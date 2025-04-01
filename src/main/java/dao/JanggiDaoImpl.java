package dao;

import config.Connector;
import domain.type.ChessTeam;
import game.Janggi;
import game.Turn;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class JanggiDaoImpl implements JanggiDao {

    private final Connector connector;

    public JanggiDaoImpl(final Connector connector) {
        this.connector = connector;
    }
    @Override
    public void save(final Janggi janggi) {
        if (janggi == null) {
            return;
        }

        String sql = "INSERT INTO janggi (turn) VALUES (?)";

        try (PreparedStatement pstmt = connector.getConnection().prepareStatement(sql)) {
            pstmt.setString(1, janggi.getCurrentTeam().name());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("삽입 중 오류 발생", e);
        }
    }
    @Override
    public Optional<Turn> findTurn() {
        String sql = "SELECT * FROM janggi LIMIT 1";
        try (Connection connection = connector.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                final ChessTeam chessTeam = ChessTeam.valueOf(rs.getString("turn"));
                final Turn turn = new Turn(chessTeam);
                return Optional.of(turn);
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException("장기 정보 조회 중 오류 발생", e);
        }
    }
    @Override
    public void clear() {
        final String sql = "DELETE FROM janggi";
        try (PreparedStatement pstmt = connector.getConnection().prepareStatement(sql)) {
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("테이블 클리어 오류",e);
        }
    }
}
