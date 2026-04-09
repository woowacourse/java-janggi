package service;

import static domain.player.Team.CHO;
import static domain.player.Team.HAN;
import static org.assertj.core.api.Assertions.assertThat;

import dao.BoardDao;
import db.ConfigLoader;
import db.DbBootstrap;
import db.TransactionExecutor;
import repository.JanggiGameRepository;
import dao.JanggiGameDao;
import domain.board.Formation;
import domain.player.Name;
import domain.player.Player;
import domain.player.Team;
import domain.position.Position;
import db.DbConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class JanggiGamePlayServiceTest {
    private final ConfigLoader configLoader = new ConfigLoader("application-test.properties");
    private final DbConnectionFactory dbConnectionFactory = new DbConnectionFactory(configLoader);
    private final TransactionExecutor transactionExecutor = new TransactionExecutor(dbConnectionFactory);
    private final JanggiGameDao janggiGameDao = new JanggiGameDao(dbConnectionFactory);
    private final BoardDao boardDao = new BoardDao(dbConnectionFactory, transactionExecutor);
    private final JanggiGameRepository janggiGameRepository = new JanggiGameRepository(janggiGameDao, boardDao, transactionExecutor);
    private final JanggiGameSetupService janggiGameSetupService = new JanggiGameSetupService(janggiGameRepository);
    private final JanggiGamePlayService janggiGamePlayService = new JanggiGamePlayService(janggiGameRepository);

    @BeforeEach
    void setUp() {
        DbBootstrap dbBootstrap = new DbBootstrap(dbConnectionFactory);
        dbBootstrap.initialize();
    }


    @Nested
    class PlayTurnTest {
        @Test
        void 정상_이동이면_보드와_현재턴을_저장한다() {
            JanggiGameSession session = janggiGameSetupService.createNewGame(
                createPlayer("cho", CHO),
                createPlayer("han", HAN),
                Formation.from(1),
                Formation.from(1)
            );

            Position source = new Position(6, 0);
            Position destination = new Position(5, 0);

            janggiGamePlayService.playTurn(session.gameId(), session.janggiGameManager(), source, destination);

            assertThat(boardDao.loadBoard(session.gameId())).containsKey(destination);
            assertThat(janggiGameDao.getCurrentTurn(session.gameId())).isEqualTo(HAN);
            assertThat(readGameStatus(session.gameId())).isEqualTo("PROGRESS");
        }

        @Test
        void 게임이_종료되면_보드와_종료상태가_함께_저장된다() {
            Player choPlayer = createPlayer("cho", CHO);
            Player hanPlayer = createPlayer("han", HAN);

            JanggiGameSession session = janggiGameSetupService.createNewGame(
                choPlayer,
                hanPlayer,
                Formation.from(1),
                Formation.from(1)
            );

            EndedJanggiGameManager endedGameManager = new EndedJanggiGameManager(choPlayer, hanPlayer, choPlayer.getProfile());
            long gameId = session.gameId();

            janggiGamePlayService.playTurn(gameId, endedGameManager, new Position(6, 0), new Position(5, 0));

            assertThat(janggiGameDao.getCurrentTurn(gameId)).isEqualTo(CHO);
            assertThat(readGameStatus(gameId)).isEqualTo("CHO_WIN");
            assertThat(boardDao.loadBoard(gameId)).containsKey(new Position(5, 0));
        }
    }

    @Test
    void 게임이_종료되면_playTurn에서_승자를_반환한다() {
        Player choPlayer = createPlayer("cho", CHO);
        Player hanPlayer = createPlayer("han", HAN);

        JanggiGameSession session = janggiGameSetupService.createNewGame(
            choPlayer,
            hanPlayer,
            Formation.from(1),
            Formation.from(1)
        );

        EndedJanggiGameManager endedGameManager = new EndedJanggiGameManager(choPlayer, hanPlayer, choPlayer.getProfile());
        long gameId = session.gameId();

        janggiGamePlayService.playTurn(gameId, endedGameManager, new Position(6, 0), new Position(5, 0));

        assertThat(readGameStatus(gameId)).isEqualTo("CHO_WIN");
    }

    @Test
    void HAN_승리_테스트() {
        Player choPlayer = createPlayer("cho", CHO);
        Player hanPlayer = createPlayer("han", HAN);

        JanggiGameSession session = janggiGameSetupService.createNewGame(
            choPlayer,
            hanPlayer,
            Formation.from(1),
            Formation.from(1)
        );

        EndedJanggiGameManager endedGameManager = new EndedJanggiGameManager(choPlayer, hanPlayer, hanPlayer.getProfile());
        long gameId = session.gameId();

        janggiGamePlayService.playTurn(gameId, endedGameManager, new Position(6, 0), new Position(5, 0));

        assertThat(readGameStatus(gameId)).isEqualTo("HAN_WIN");
    }

    private Player createPlayer(String name, Team team) {
        return new Player(new Name(name), team);
    }

    private String readGameStatus(long gameId) {
        String query = "SELECT status FROM game WHERE game_id = ?";
        try (Connection connection = dbConnectionFactory.createConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, gameId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("status");
                }
            }
        } catch (SQLException e) {
            throw new IllegalStateException("게임 상태 조회에 실패했습니다.", e);
        }
        throw new IllegalStateException("해당 게임을 찾을 수 없습니다. gameId: " + gameId);
    }
}
