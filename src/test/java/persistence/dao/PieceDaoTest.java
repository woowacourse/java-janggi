package persistence.dao;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

import domain.board.BoardLocation;
import domain.piece.PieceType;
import domain.piece.Score;
import domain.piece.Team;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.entity.PieceEntity;

class PieceDaoTest {

    private Connection connection;
    private PieceDao pieceDao = new PieceDao();

    @BeforeEach
    void setUp() throws SQLException {
        connection = DriverManager.getConnection("jdbc:h2:mem:testdb", "sa", "");
        createTable();
    }

    @AfterEach
    void tearDown() throws SQLException {
        connection.close();
    }

    @Test
    @DisplayName("여러 개의 Piece를 생성할 수 있다")
    void testCreateAll() throws SQLException {
        // given
        Long janggiGameId = 1L;
        List<PieceEntity> pieces = List.of(
                new PieceEntity(null, 1, 1, PieceType.SCHOLAR, Team.HAN, new Score(1), janggiGameId),
                new PieceEntity(null, 1, 2, PieceType.CANNON, Team.HAN, new Score(10), janggiGameId)
        );

        // when
        pieceDao.createAll(connection, pieces);

        // then
        List<PieceEntity> storedPieces = pieceDao.findAllByJanggiGameId(connection, janggiGameId);
        assertSoftly(softly -> {
            softly.assertThat(storedPieces).hasSize(2);
            softly.assertThat(storedPieces)
                    .extracting(PieceEntity::type)
                    .containsExactlyInAnyOrder(PieceType.SCHOLAR, PieceType.CANNON);
        });
    }

    @Test
    @DisplayName("특정 Janggi 게임의 모든 Piece를 조회할 수 있다")
    void testFindAllByJanggiGameId() throws SQLException {
        // given
        Long janggiGameId = 1L;
        List<PieceEntity> pieces = List.of(
                new PieceEntity(null, 1, 1, PieceType.SCHOLAR, Team.HAN, new Score(1), janggiGameId),
                new PieceEntity(null, 1, 2, PieceType.CANNON, Team.HAN, new Score(10), janggiGameId)
        );

        pieceDao.createAll(connection, pieces);

        // when
        List<PieceEntity> foundPieces = pieceDao.findAllByJanggiGameId(connection, janggiGameId);

        // then
        assertSoftly(softly -> {
            softly.assertThat(foundPieces).hasSize(2);
            softly.assertThat(foundPieces)
                    .extracting(PieceEntity::type)
                    .containsExactlyInAnyOrder(PieceType.SCHOLAR, PieceType.CANNON);
        });
    }

    @Test
    @DisplayName("Piece의 위치를 업데이트할 수 있다")
    void testUpdate() throws SQLException {
        // given
        Long janggiGameId = 1L;
        List<PieceEntity> pieces = List.of(
                new PieceEntity(null, 1, 1, PieceType.SCHOLAR, Team.HAN, new Score(1), janggiGameId),
                new PieceEntity(null, 4, 4, PieceType.CANNON, Team.HAN, new Score(10), janggiGameId)
        );

        pieceDao.createAll(connection, pieces);

        BoardLocation origin = new BoardLocation(4, 4);
        BoardLocation newLocation = new BoardLocation(5, 5);

        // when
        pieceDao.update(connection, origin, newLocation);

        // then
        List<PieceEntity> updatedPieces = pieceDao.findAllByJanggiGameId(connection, janggiGameId);
        assertSoftly(softly -> {
            softly.assertThat(updatedPieces).hasSize(2);
            softly.assertThat(updatedPieces.get(1).column()).isEqualTo(5);
            softly.assertThat(updatedPieces.get(1).row()).isEqualTo(5);
        });
    }

    private void createTable() throws SQLException {
        try (var statement = connection.createStatement()) {
            statement.execute("""
                DROP TABLE IF EXISTS piece;
                CREATE TABLE piece (
                    id BIGINT AUTO_INCREMENT PRIMARY KEY,
                    column_index INT NOT NULL,
                    row_index INT NOT NULL,
                    type VARCHAR(255) NOT NULL,
                    team VARCHAR(255) NOT NULL,
                    score DOUBLE NOT NULL,
                    janggi_game_id BIGINT NOT NULL
                );
            """);
        }
    }
}
