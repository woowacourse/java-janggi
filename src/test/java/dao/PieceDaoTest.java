package dao;

import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class PieceDaoTest {

    private final PieceDao pieceDao = new PieceDao();

    @Test
    public void connection() {
        try (final var connection = pieceDao.getConnection()) {
            assertThat(connection).isNotNull();
        } catch (SQLException e) {
            throw new IllegalArgumentException("연결 오류 발생");
        }
    }
}
