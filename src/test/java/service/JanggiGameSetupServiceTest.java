package service;

import static domain.player.Team.CHO;
import static domain.player.Team.HAN;
import static org.assertj.core.api.Assertions.assertThat;

import dao.BoardDao;
import dao.GameInfo;
import repository.JanggiGameRepository;
import dao.JanggiGameDao;
import db.TestDbBootstrap;
import domain.board.Board;
import domain.board.BoardFactory;
import domain.board.Formation;
import common.GameStatus;
import domain.player.Name;
import domain.player.Player;
import domain.player.Team;
import db.DbConnectionFactory;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class JanggiGameSetupServiceTest {
    private final JanggiGameDao janggiGameDao = new JanggiGameDao();
    private final BoardDao boardDao = new BoardDao();
    private final JanggiGameRepository janggiGameRepository = new JanggiGameRepository(janggiGameDao, boardDao);
    private final JanggiGameSetupService janggiGameSetupService = new JanggiGameSetupService(janggiGameRepository);

    @BeforeEach
    void setUp() {
        TestDbBootstrap.initializeTestDb();
    }

    @Test
    void 새_게임을_생성하면_세션을_반환하고_진행_상태로_저장한다() {
        JanggiGameSession session = janggiGameSetupService.createNewGame(
            createPlayer("CHO Player", CHO),
            createPlayer("HAN Player", HAN),
            Formation.from(1),
            Formation.from(1)
        );

        assertThat(session.gameId()).isPositive();
        assertThat(session.janggiGameManager().getCurrentPlayer().getProfile().team()).isEqualTo(CHO);
        assertThat(janggiGameSetupService.findProgressGames())
            .anyMatch(gameInfo -> gameInfo.gameId() == session.gameId());
    }

    @Test
    void 저장된_게임을_세션으로_불러온다() throws SQLException {
        try (Connection connection = DbConnectionFactory.createConnection()) {
            long gameId = janggiGameDao.createGame(connection, "CHO Player", "HAN Player");
            Board board = BoardFactory.createWithFormation(Formation.from(1), Formation.from(1));
            boardDao.saveFullBoard(gameId, board);

            Optional<JanggiGameSession> loaded = janggiGameSetupService.loadSessionById(gameId);

            assertThat(loaded).isPresent();
            assertThat(loaded.get().gameId()).isEqualTo(gameId);
            assertThat(loaded.get().janggiGameManager().getCurrentPlayer().getProfile().team()).isEqualTo(CHO);
        }
    }

    @Test
    void 저장되지_않은_게임은_로드할_수_없다() {
        Optional<JanggiGameSession> result = janggiGameSetupService.loadSessionById(999999L);

        assertThat(result).isEmpty();
    }

    @Test
    void 모든_진행중인_게임을_반환한다() throws SQLException {
        try (Connection connection = DbConnectionFactory.createConnection()) {
            List<GameInfo> existingGames = janggiGameSetupService.findProgressGames();
            for (GameInfo game : existingGames) {
                janggiGameDao.updateGameState(connection, game.gameId(), Team.CHO, GameStatus.CHO_WIN);
            }

            long gameId1 = janggiGameDao.createGame(connection, "CHO Player1", "HAN Player1");
            long gameId2 = janggiGameDao.createGame(connection, "CHO Player2", "HAN Player2");

            List<GameInfo> games = janggiGameSetupService.findProgressGames();

            assertThat(games).hasSize(2);
            assertThat(games).anyMatch(game -> game.gameId() == gameId1);
            assertThat(games).anyMatch(game -> game.gameId() == gameId2);
        }
    }

    private Player createPlayer(String name, Team team) {
        return new Player(new Name(name), team);
    }
}
