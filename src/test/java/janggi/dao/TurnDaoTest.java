package janggi.dao;

import static janggi.domain.Team.BLUE;
import static janggi.domain.Team.RED;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import janggi.domain.Turn;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TurnDaoTest {

    private final TurnDao turnDao = new TurnDao();

    @BeforeAll
    static void setUpClass() {
        DatabaseConnection.setTestMode(true);
    }

    @BeforeEach
    void setUp() {
        turnDao.deleteTurn();
    }

    @AfterAll
    static void tearDownClass() {
        DatabaseConnection.setTestMode(false);
    }

    @DisplayName("턴을 추가한다.")
    @Test
    void addTurnTest() {

        // given
        final Turn turn = new Turn(RED);

        // when & then
        assertThatCode(() -> {
            turnDao.addTurn(turn);
        }).doesNotThrowAnyException();
    }

    @DisplayName("턴을 찾는다.")
    @Test
    void findTurnTest() {

        // given
        final Turn turn = new Turn(RED);
        turnDao.addTurn(turn);

        // when
        final Turn findTurn = turnDao.findTurn();

        // then
        assertThat(turn).isEqualTo(findTurn);
    }

    @DisplayName("턴을 바꾼다.")
    @Test
    void updateTurnTest() {

        // given
        final Turn turn = new Turn(RED);
        turnDao.addTurn(turn);

        // when
        turnDao.updateTurn(new Turn(BLUE));

        // then
        assertThat(turnDao.findTurn()).isEqualTo(new Turn(BLUE));
    }

    @DisplayName("턴을 삭제한다.")
    @Test
    void deleteTurnTest() {

        // given
        final Turn turn = new Turn(RED);
        turnDao.addTurn(turn);

        // when
        turnDao.deleteTurn();

        // then
        assertThatCode(turnDao::findTurn)
                .isInstanceOf(RuntimeException.class)
                .hasMessage("턴 정보를 찾을 수 없습니다.");
    }
}
