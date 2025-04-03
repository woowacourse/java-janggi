package dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import domain.player.Team;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BoardDaoTest {

    private final TestDatabaseConnectionManager connectionManager = new TestDatabaseConnectionManager();
    private final BoardDao boardDao = new BoardDao(connectionManager);

    @BeforeEach
    void setUp() {
        try (PreparedStatement statement = connectionManager.getConnection().prepareStatement("DELETE FROM board")) {
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("[ERROR] 테스트 데이터 초기화를 실패했습니다.");
        }
    }

    @Test
    void 새로운_턴을_삽입한다() {
        // when & then
        assertThatCode(() -> boardDao.addBoard(Team.HAN))
                .doesNotThrowAnyException();
    }

    @Test
    void 현재_턴을_반환한다() {
        // given
        boardDao.addBoard(Team.CHO);

        // when
        Optional<Team> currentTurn = boardDao.findCurrentTurn();

        // then
        assertThat(currentTurn.get()).isEqualTo(Team.CHO);
    }

    @Test
    void 현재_턴을_업데이트한다() {
        // given
        boardDao.addBoard(Team.CHO);

        // when
        boardDao.updateCurrentTurn(Team.HAN);

        // then
        Optional<Team> currentTurn = boardDao.findCurrentTurn();
        assertThat(currentTurn.get()).isEqualTo(Team.HAN);
    }

    @Test
    void 턴_데이터를_초기화한다() {
        // when & then
        assertThatCode(boardDao::clear)
                .doesNotThrowAnyException();
    }

}
