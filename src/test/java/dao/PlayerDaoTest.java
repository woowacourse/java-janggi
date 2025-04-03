package dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.jupiter.api.Assertions.assertAll;

import dao.fixture.JanggiGameTestFixture;
import domain.TeamType;
import domain.player.Player;
import domain.player.Username;
import domain.turn.GameState;
import domain.turn.TurnState;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import util.H2ConnectionFactory;

class PlayerDaoTest {

    private PlayerDao playerDao;
    private Connection connection;

    @BeforeEach
    void setup() throws SQLException {
        H2ConnectionFactory h2Connection = new H2ConnectionFactory();
        h2Connection.initializeTable();
        connection = h2Connection.getConnection();
        connection.setAutoCommit(false);
        playerDao = new PlayerDao();
    }

    @AfterEach
    void rollback() throws SQLException {
        connection.rollback();
        connection.close();
    }

    @Test
    @DisplayName("플레이어 데이터를 저장한다")
    void savePlayerTest() {
        // given
        long gameId = JanggiGameTestFixture.saveNewJanggiGame(connection);
        Username name = new Username("루키");
        TeamType team = TeamType.HAN;
        Player player = new Player(name, team);

        // when & then
        assertThatCode(() -> playerDao.savePlayer(player, gameId, connection))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("게임에 해당하는 플레이어들의 데이터를 조회한다")
    void findPlayersByGameIdTest() {
        // given
        JanggiGameDao janggiGameDao = new JanggiGameDao();
        long gameId = janggiGameDao.saveJanggiGame(new TurnState(false, TeamType.CHO), GameState.IN_PROGRESS,
                connection);

        Username choPlayerName = new Username("루키");
        TeamType choPlayerTeam = TeamType.CHO;
        Player choPlayer = new Player(choPlayerName, choPlayerTeam);

        Username hanPlayerName = new Username("코기");
        TeamType hanPlayerTeam = TeamType.HAN;
        Player hanPlayer = new Player(hanPlayerName, hanPlayerTeam);

        playerDao.savePlayer(choPlayer, gameId, connection);
        playerDao.savePlayer(hanPlayer, gameId, connection);

        // when
        List<Player> players = playerDao.findPlayersByGameId(gameId, connection);

        //then
        Player findChoPlayer = players.getFirst();
        Player findHanPlayer = players.getLast();
        assertAll(
                () -> assertThat(findChoPlayer.getName()).isEqualTo("루키"),
                () -> assertThat(findHanPlayer.getName()).isEqualTo("코기"),
                () -> assertThat(findChoPlayer.getTeamType()).isEqualTo(TeamType.CHO),
                () -> assertThat(findHanPlayer.getTeamType()).isEqualTo(TeamType.HAN)
        );
    }
}
