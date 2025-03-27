package save;

import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import piece.player.Team;

class JanggiTurnDaoTest {

    private JanggiTurnDao janggiTurnDao;

    @BeforeEach
    void setUp() {
        MySQLConnection connection = new TestJanggiConnection();
        janggiTurnDao = new JanggiTurnDao(connection);
        janggiTurnDao.deleteAll();
    }

    @Test
    void 턴_추가_테스트() {
        Team team = Team.BLUE;
        int turn = 1;
        int score = 100;

        janggiTurnDao.addTurnScore(team, turn, score);

        Optional<Integer> latestTurnId = janggiTurnDao.getLatestTurnId();
        Assertions.assertTrue(latestTurnId.isPresent());
    }

    @Test
    void 최근_턴이_존재하지않으면_빈값을_반환함() {
        Optional<Integer> latestTurn = janggiTurnDao.getLatestTurn();
        Assertions.assertTrue(latestTurn.isEmpty());
    }
}
