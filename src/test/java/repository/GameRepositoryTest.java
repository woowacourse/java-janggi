package repository;

import domain.piece.Team;
import domain.settingType.SettingType;
import domain.state.GameInitializer;
import domain.state.JanggiGame;
import domain.state.State;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GameRepositoryTest {
    private final GameRepository gameRepository = new GameRepository();

    @BeforeEach
    void init() {
        String sql = "DELETE FROM GAME_ROOM";

        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement psmt = conn.prepareStatement(sql)) {
            psmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void save() {
        String title = "테스트용 장기방";
        Team team = Team.CHO;
        long savedId = 0L;

        try (Connection conn = ConnectionManager.getConnection()) {
            GameRoomCreateInfo gameRoomInfo = new GameRoomCreateInfo(title, State.PLAYING, team);
            savedId = gameRepository.save(conn, gameRoomInfo);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        List<GameRoomInfo> gameInfo = gameRepository.getAll();

        Assertions.assertThat(gameInfo).hasSize(1);
        Assertions.assertThat(gameInfo.getFirst().id()).isEqualTo(savedId);
        Assertions.assertThat(gameInfo.getFirst().title()).isEqualTo(title);
    }

    @Test
    void 턴이_변경되면_반영되어야_한다() {
        String title = "테스트용 장기방";
        Team team = Team.CHO;
        long savedId = 0L;
        Team updatedTurn = null;
        try (Connection conn = ConnectionManager.getConnection()) {
            GameRoomCreateInfo gameRoomInfo = new GameRoomCreateInfo(title, State.PLAYING, team);
            savedId = gameRepository.save(conn, gameRoomInfo);
            // when
            JanggiGame game = GameInitializer.init(SettingType.LEFT, SettingType.LEFT);
            game = game.pass();

            gameRepository.updateGame(conn, savedId, game);
            updatedTurn = gameRepository.getCurrentTeam(savedId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // then
        Assertions.assertThat(updatedTurn).isEqualTo(Team.HAN);
    }
}