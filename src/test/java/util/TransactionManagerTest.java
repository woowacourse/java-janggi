package util;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import dao.JanggiGameDao;
import domain.TeamType;
import domain.turn.GameState;
import domain.turn.TurnState;
import java.sql.Connection;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TransactionManagerTest {

    private H2ConnectionFactory factory;

    @BeforeEach
    void setup() {
        factory = new H2ConnectionFactory();
        factory.initializeTable();
    }

    @Test
    @DisplayName("트랜잭션 도중 예외가 발생하지 않으면 커밋한다")
    void commitExecuteTest() {
        // given
        JanggiGameDao janggiGameDao = new JanggiGameDao();
        TransactionManager transactionManager = new TransactionManager(new H2ConnectionFactory());

        // when
        transactionManager.execute(
                connection -> janggiGameDao.saveJanggiGame(new TurnState(false, TeamType.HAN), GameState.IN_PROGRESS,
                        connection));

        // then
        Connection connection = factory.getConnection();
        List<Long> expected = janggiGameDao.findInProgressGameIds(connection);
        assertThat(expected).hasSize(1);
    }

    @Test
    @DisplayName("트랜잭션 도중 예외가 발생하면 롤백한다")
    void rollbackExecuteTest() {
        // given
        JanggiGameDao janggiGameDao = new JanggiGameDao();
        TransactionManager transactionManager = new TransactionManager(new H2ConnectionFactory());

        // when & then
        assertThatThrownBy(() -> {
            transactionManager.execute(connection -> {
                janggiGameDao.saveJanggiGame(new TurnState(false, TeamType.HAN), GameState.IN_PROGRESS,
                        connection);
                throw new IllegalStateException("예외 발생");
            });
        })
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("예외 발생");

        Connection connection = factory.getConnection();
        List<Long> expected = janggiGameDao.findInProgressGameIds(connection);
        assertThat(expected).hasSize(0);
    }
}
