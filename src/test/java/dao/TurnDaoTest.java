package dao;

import db.H2DatabaseConnector;
import domain.piece.Team;
import fake.FakeTurnDao;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.*;

@DisplayName("Turn Dao 테스트")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class TurnDaoTest {

    @Nested
    @DisplayName("Fake Turn Dao 테스트")
    class FakeTurnDaoTest {

        private TurnDao turnDao;

        @BeforeEach
        void setUp() {
            turnDao = new FakeTurnDao();
        }

        @Test
        void 턴을_조회할_수_있다() {
            assertThat(turnDao.load()).isEqualTo(Team.CHO);
        }

        @Test
        void 턴을_저장할_수_있다() {
            turnDao.save(Team.HAN);

            assertThat(turnDao.load()).isEqualTo(Team.HAN);
        }

        @Test
        void 턴_전체를_삭제할_수_있다() {
            turnDao.remove();

            assertThatThrownBy(turnDao::load)
                    .isInstanceOf(NoSuchElementException.class);
        }
    }

    @Nested
    @DisplayName("H2 Turn Dao 테스트")
    class H2TurnDaoTest {

        private TurnDao turnDao;

        @BeforeEach
        void setUp() {
            turnDao = new TurnDaoImpl(new H2DatabaseConnector());
            createTurnTable();
            insertTurn();
        }

        @AfterEach
        void tearDown() {
            dropTable();
        }

        private void createTurnTable() {
            String query = """
                    CREATE TABLE IF NOT EXISTS `turn` (
                        turn VARCHAR(3) PRIMARY KEY
                    );
                    """;
            try (final Connection connection = new H2DatabaseConnector().getConnection();
                 final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.execute();
            } catch (SQLException e) {
                throw new RuntimeException("[ERROR] H2 Turn 테이블 생성 오류: " + e.getMessage(), e);
            }
        }

        private void insertTurn() {
            String query = "INSERT INTO turn (turn) VALUES('CHO')";
            try (final Connection connection = new H2DatabaseConnector().getConnection();
                 final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException("[ERROR] H2 Turn 초기 데이터 삽입 오류: " + e.getMessage(), e);
            }
        }

        private void dropTable() {
            String query = "DROP table turn";
            try (final Connection connection = new H2DatabaseConnector().getConnection();
                 final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.execute();
            } catch (SQLException e) {
                throw new RuntimeException("[ERROR] H2 Turn 테이블 드랍 오류: " + e.getMessage(), e);
            }
        }

        @Test
        void 턴을_조회할_수_있다() {
            assertThat(turnDao.load()).isEqualTo(Team.CHO);
        }

        @Test
        void 턴을_저장할_수_있다() {
            turnDao.save(Team.HAN);

            assertThat(turnDao.load()).isEqualTo(Team.HAN);
        }

        @Test
        void 턴_전체를_삭제할_수_있다() {
            turnDao.remove();

            assertThatCode(turnDao::load)
                    .doesNotThrowAnyException();
        }
    }
}
