package service;

import static domain.player.Team.CHO;
import static domain.player.Team.HAN;
import static org.assertj.core.api.Assertions.assertThat;

import dao.BoardRepository;
import dao.GamePersistence;
import dao.GameRoom;
import domain.board.Formation;
import domain.manager.JanggiGameManager;
import domain.player.Name;
import domain.player.Player;
import domain.player.Team;
import domain.position.Position;
import db.DbBootstrap;
import db.DbConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class JanggiGamePlayServiceTest {
    private final GameRoom gameRoom = new GameRoom();
    private final BoardRepository boardRepository = new BoardRepository();
    private final GamePersistence gamePersistence = new GamePersistence(gameRoom, boardRepository);
    private final JanggiGameSetupService janggiGameSetupService = new JanggiGameSetupService(gamePersistence);
    private final JanggiGamePlayService janggiGamePlayService = new JanggiGamePlayService(gamePersistence);

    @BeforeEach
    void setUp() {
        DbBootstrap.initializeForTest();
    }

    @Test
    void playTurn_정상_이동이면_보드와_현재턴을_저장한다() {
        JanggiGameSession session = janggiGameSetupService.createNewGame(
            createPlayer("cho", CHO),
            createPlayer("han", HAN),
            Formation.from(1),
            Formation.from(1)
        );

        Position source = new Position(6, 0);
        Position destination = new Position(5, 0);

        janggiGamePlayService.playTurn(session.gameId(), session.janggiGameManager(), source, destination);

        assertThat(boardRepository.loadBoard(session.gameId())).containsKey(destination);
        assertThat(gameRoom.getCurrentTurn(session.gameId())).isEqualTo(HAN);
        assertThat(readGameStatus(session.gameId())).isEqualTo("PROGRESS");
    }

    @Test
    void playTurn_게임이_종료되면_진행상태를_업데이트하지_않는다() {
        Player choPlayer = createPlayer("cho", CHO);
        Player hanPlayer = createPlayer("han", HAN);
        EndedJanggiGameManager endedGameManager = new EndedJanggiGameManager(choPlayer, hanPlayer);
        long gameId = gameRoom.createGame("cho", "han");
        boardRepository.save(gameId, endedGameManager.getBoard());

        janggiGamePlayService.playTurn(gameId, endedGameManager, new Position(6, 0), new Position(5, 0));

        assertThat(gameRoom.getCurrentTurn(gameId)).isEqualTo(CHO);
        assertThat(readGameStatus(gameId)).isEqualTo("PROGRESS");
    }

    @Test
    void finishGame_승자가_CHO면_CHO_WIN으로_저장한다() {
        Player choPlayer = createPlayer("cho", CHO);
        Player hanPlayer = createPlayer("han", HAN);
        EndedJanggiGameManager endedGameManager = new EndedJanggiGameManager(choPlayer, hanPlayer);
        long gameId = gameRoom.createGame("cho", "han");

        janggiGamePlayService.finishGame(gameId, endedGameManager, CHO);

        assertThat(gameRoom.getCurrentTurn(gameId)).isEqualTo(CHO);
        assertThat(readGameStatus(gameId)).isEqualTo("CHO_WIN");
    }

    @Test
    void finishGame_승자가_HAN이면_HAN_WIN으로_저장한다() {
        Player choPlayer = createPlayer("cho", CHO);
        Player hanPlayer = createPlayer("han", HAN);
        EndedJanggiGameManager endedGameManager = new EndedJanggiGameManager(choPlayer, hanPlayer);
        long gameId = gameRoom.createGame("cho", "han");

        janggiGamePlayService.finishGame(gameId, endedGameManager, HAN);

        assertThat(gameRoom.getCurrentTurn(gameId)).isEqualTo(HAN);
        assertThat(readGameStatus(gameId)).isEqualTo("HAN_WIN");
    }

    private Player createPlayer(String name, Team team) {
        return new Player(new Name(name), team);
    }

    private String readGameStatus(long gameId) {
        String query = "SELECT status FROM game WHERE game_id = ?";
        try (Connection connection = DbConnectionFactory.createConnection();
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

    private static class EndedJanggiGameManager extends JanggiGameManager {
        private final Player hanPlayer;

        EndedJanggiGameManager(Player choPlayer, Player hanPlayer) {
            super(choPlayer, hanPlayer, Formation.from(1), Formation.from(1));
            this.hanPlayer = hanPlayer;
        }

        @Override
        public void validateSource(Position source) {
        }

        @Override
        public void move(Position source, Position destination) {
            endGame();
        }

        @Override
        public Player getCurrentPlayer() {
            return hanPlayer;
        }
    }
}
