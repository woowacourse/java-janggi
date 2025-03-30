package dao;

import static org.assertj.core.api.Assertions.assertThat;

import dao.fake.FakeConnector;
import dao.fake.InMemoryDatabase;
import domain.player.Player;
import domain.player.Score;
import domain.player.Team;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public final class PlayerDAOTest {
    private final InMemoryDatabase database = new InMemoryDatabase();
    private final Connector connector = new FakeConnector(database);
    private final PlayerDAO playerDAO = new PlayerDAO(connector);

    @Test
    @DisplayName("Player 추가를 요청한다.")
    void test_createWithGameId() {
        //given
        assertThat(database.getPlayers().isEmpty()).isTrue();
        final Team team = Team.HAN;
        final int gameId = 1;

        //when
        playerDAO.createWithGameId(team, gameId);

        //then
        final Player actual = database.getPlayers().get(1);
        assertThat(actual.getTeam()).isEqualTo(team);
    }

    @Test
    @DisplayName("Player 정보를 일괄 수정한다.")
    void test_updateBatch() {
        //given
        final int gameId = 1;
        playerDAO.createWithGameId(Team.CHO, gameId);
        playerDAO.createWithGameId(Team.HAN, gameId);
        final Player oldCho = database.getPlayers().get(1);
        final Player oldHan = database.getPlayers().get(2);

        assertThat(oldCho.isTurn()).isTrue();
        assertThat(oldHan.isTurn()).isFalse();

        final List<Player> players = new ArrayList<>();
        players.add(new Player(1, Team.CHO, new Score(0.0), false));
        players.add(new Player(2, Team.HAN, new Score(1.5), true));

        //when
        playerDAO.updateBatch(players);

        //then
        final Player newCho = database.getPlayers().get(1);
        assertThat(newCho.isTurn()).isFalse();
        assertThat(newCho.getId()).isEqualTo(oldCho.getId());

        final Player newHan = database.getPlayers().get(2);
        assertThat(newHan.isTurn()).isTrue();
        assertThat(newHan.getId()).isEqualTo(oldHan.getId());
    }

    @Test
    @DisplayName("Player 정보를 조회한다.")
    void test_findById() {
        //given
        final Team team = Team.HAN;
        final int gameId = 1;
        playerDAO.createWithGameId(team, gameId);

        //when
        Player player = playerDAO.findById(1);
        //then
        final Player expect = database.getPlayers().get(1);
        assertThat(player.getId()).isEqualTo(expect.getId());
    }

    @Test
    @DisplayName("같은 게임에 속한 플레이어들을 조회한다.")
    void test_findAllByGameId() {
        //given
        final int gameId = 1;
        playerDAO.createWithGameId(Team.CHO, gameId);
        playerDAO.createWithGameId(Team.HAN, gameId);
        playerDAO.createWithGameId(Team.HAN, gameId + 1);
        playerDAO.createWithGameId(Team.HAN, gameId + 2);
        //when
        List<Player> players = playerDAO.findAllByGameId(1);
        //then
        assertThat(players.size()).isEqualTo(2);
    }
}
