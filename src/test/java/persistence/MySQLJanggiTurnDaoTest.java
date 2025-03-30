package persistence;

import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import piece.player.Team;

class MySQLJanggiTurnDaoTest {

    private final DatabaseConnection connection = new TestMySQLConnection();
    private final MySQLJanggiTurnDao janggiTurnDao = new MySQLJanggiTurnDao(connection);

    @AfterEach
    void clearDatabases() {
        janggiTurnDao.deleteAll();
    }

    @Test
    void 턴_추가_테스트() {
        Team team = Team.BLUE;
        int turn = 1;
        int score = 100;

        janggiTurnDao.addTurnScore(team, turn, score);

        Optional<Integer> latestTurnId = janggiTurnDao.findLatestTurnId();
        Assertions.assertTrue(latestTurnId.isPresent());
    }

    @Test
    void 가장_최근의_턴을_가져올_수_있다() {
        Team team = Team.BLUE;
        int turn = 1;
        int score = 100;

        janggiTurnDao.addTurnScore(team, turn, score);

        Optional<Integer> latestTurnId = janggiTurnDao.findLatestTurnId();
        Assertions.assertTrue(latestTurnId.isPresent());
    }

    @Test
    void 전체_삭제_테스트() {
        Team team = Team.BLUE;
        int turn = 1;
        int score = 100;

        janggiTurnDao.addTurnScore(team, turn, score);
        janggiTurnDao.deleteAll();

        Optional<Integer> latestTurnId = janggiTurnDao.findLatestTurnId();
        Assertions.assertTrue(latestTurnId.isEmpty());
    }

    @Test
    void 최근_턴이_존재하지않으면_빈값을_반환함() {
        Optional<Integer> latestTurn = janggiTurnDao.findLatestTurn();
        Assertions.assertTrue(latestTurn.isEmpty());
    }
}
