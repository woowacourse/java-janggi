package domain.player;


import domain.Team;
import java.sql.SQLException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PlayerDaoTest {
    private PlayerDao playerDao;

    @BeforeEach
    public void setUp() {
        playerDao = new PlayerDao();
    }

    @DisplayName("플레이어 DB 저장 테스트")
    @Test
    public void test1() throws SQLException {

        //given
        String playerName = "레몬";
        Team team = Team.BLUE;  // Team.RED가 Player에 맞는 Team입니다.
        int gameId = 100;

        //when
        Player savedPlayer = playerDao.insertPlayer(playerName, gameId, team);

        //then
        Assertions.assertThat(savedPlayer.getId()).isEqualTo(1);
        Assertions.assertThat(savedPlayer.getName()).isEqualTo("레몬");
        Assertions.assertThat(savedPlayer.getTeam()).isEqualTo(Team.BLUE);
    }


}
