package dao;

import db.H2DatabaseConnector;
import domain.board.Point;
import domain.piece.Piece;
import domain.piece.Sa;
import domain.piece.Team;
import fake.FakeBoardDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Board Dao 테스트")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class BoardDaoTest {

    @Nested
    @DisplayName("Fake Board Dao 테스트")
    class FakeBoardDaoTest {

        private BoardDao boardDao;

        @BeforeEach
        void setUp() {
            boardDao = new FakeBoardDao();
        }

        @Test
        void 저장된_데이터가_있다면_true를_반환한다() {
            assertThat(boardDao.hasRecords()).isTrue();
        }

        @Test
        void 저장된_데이터가_없다면_false를_반환한다() {
            boardDao.removeAll();

            assertThat(boardDao.hasRecords()).isFalse();
        }

        @Test
        void 보드를_조회할_수_있다() {
            assertThat(boardDao.load()).hasSize(2);
        }

        @Test
        void 보드에_피스를_저장할_수_있다() {
            Point point = Point.of(1, 1);
            Piece sa = new Sa(Team.HAN);
            boardDao.save(point, sa);

            assertThat(boardDao.load().get(point)).isEqualTo(sa);
        }

        @Test
        void 보드_전체를_삭제할_수_있다() {
            boardDao.removeAll();

            assertThat(boardDao.load()).hasSize(0);
        }
    }

    @Nested
    @DisplayName("H2 Board Dao 테스트")
    class H2BoardDaoTest {

        private BoardDao boardDao;

        @BeforeEach
        void setUp() {
            boardDao = new BoardDaoImpl(new H2DatabaseConnector());
            createBoardTable();
        }

        private void createBoardTable() {
            String query = """
                    CREATE TABLE IF NOT EXISTS `board` (
                    	point_row       INT NOT NULL,
                    	point_column    INT NOT NULL,
                    	team            VARCHAR(3) NOT NULL,
                    	piece_type       VARCHAR(6) NOT NULL,
                    	PRIMARY KEY (point_row, point_column)
                    );
                    """;
            try (final Connection connection = new H2DatabaseConnector().getConnection();
                 final Statement statement = connection.createStatement()) {
                statement.execute(query);
            } catch (SQLException e) {
                throw new RuntimeException("[ERROR] H2 Board 테이블 생성 오류: " + e.getMessage(), e);
            }
        }

        @Test
        void 저장된_데이터가_있다면_true를_반환한다() {
            Point point = Point.of(1, 1);
            Piece sa = new Sa(Team.HAN);
            boardDao.save(point, sa);

            assertThat(boardDao.hasRecords()).isTrue();
        }

        @Test
        void 저장된_데이터가_없다면_false를_반환한다() {
            assertThat(boardDao.hasRecords()).isFalse();
        }

        @Test
        void 보드를_조회할_수_있다() {
            assertThat(boardDao.load()).hasSize(0);
        }

        @Test
        void 보드에_피스를_저장할_수_있다() {
            Point point = Point.of(1, 1);
            Piece sa = new Sa(Team.HAN);
            boardDao.save(point, sa);

            assertThat(boardDao.load()).hasSize(1);
        }

        @Test
        void 보드_전체를_삭제할_수_있다() {
            boardDao.removeAll();

            assertThat(boardDao.load()).hasSize(0);
        }
    }
}
