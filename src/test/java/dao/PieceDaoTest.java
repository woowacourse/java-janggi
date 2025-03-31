package dao;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class PieceDaoTest {
    private final PieceDao pieceDao = new PieceDao();

    @Test
    public void connection() {
        final var connection = pieceDao.getConnection();
        assertThat(connection).isNotNull();
    }
}
