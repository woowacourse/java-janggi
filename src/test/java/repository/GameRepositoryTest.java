package repository;

import domain.piece.Team;
import domain.settingType.SettingType;
import domain.state.GameInitializer;
import domain.state.JanggiGame;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
        long savedId = gameRepository.save(team, title);

        List<GameRoomInfo> gameInfo = gameRepository.getAll();

        Assertions.assertThat(gameInfo).hasSize(1);
        Assertions.assertThat(gameInfo.getFirst().id()).isEqualTo(savedId);
        Assertions.assertThat(gameInfo.getFirst().title()).isEqualTo(title);
    }

    @Test
    void 턴이_변경되면_반영되어야_한다() {
        String title = "테스트용 장기방";
        Team team = Team.CHO;
        long savedId = gameRepository.save(team, title);

        // when
        JanggiGame game = GameInitializer.init(SettingType.LEFT, SettingType.LEFT);
        game = game.pass();

        gameRepository.updateGame(savedId, game);
        Team updatedTurn = gameRepository.getCurrentTeam(savedId);

        // then
        Assertions.assertThat(updatedTurn).isEqualTo(Team.HAN);
    }

    @Test
    void 턴이_변경되어야_한다() {
        String title = "테스트용 장기방";
        Team team = Team.HAN;
        long savedId = gameRepository.save(team, title);

        // when
        JanggiGame game = GameInitializer.init(SettingType.LEFT, SettingType.LEFT);

        gameRepository.updateGame(savedId, game);

        String sql = "SELECT IS_FINISHED FROM GAME_ROOM WHERE ID = ?";

        boolean isFinished = true;
        try (
                Connection connection = ConnectionManager.getConnection();
                PreparedStatement psmt = connection.prepareStatement(sql)
        ) {
            psmt.setLong(1, savedId);
            ResultSet resultSet = psmt.executeQuery();
            while (resultSet.next()) {
                isFinished = resultSet.getBoolean(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // then
        Assertions.assertThat(isFinished).isFalse();
    }
}