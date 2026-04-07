package janggi.repository;

import janggi.db.DBInitializer;
import janggi.domain.board.Board;
import janggi.domain.board.BoardFactory;
import janggi.domain.team.Team;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Optional;

import static janggi.domain.board.PieceSetup.OUTER_ELEPHANT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class GameRepositoryTest {

    private Connection conn;
    private GameRepository repository;

    @BeforeEach
    void beforeEach() throws SQLException {
        conn = DriverManager.getConnection("jdbc:h2:mem:test");
        DBInitializer.initialize(conn);
        repository = new GameRepository(conn);
    }

    @AfterEach
    void afterEach() throws SQLException {
        conn.createStatement().execute("DROP ALL OBJECTS");
        conn.close();
    }

    @Test
    void 게임_생성_테스트() {
        // given
        Board board = BoardFactory.create(OUTER_ELEPHANT, OUTER_ELEPHANT);

        // when
        int gameId = repository.createGame(board.showBoard());

        // then
        GameInfo gameInfo = repository.findGame().orElseThrow();
        Team team = Team.valueOf(gameInfo.getCurrentTeam());
        assertAll(
                () -> assertThat(gameId).isEqualTo(gameInfo.getGameId()),
                () -> assertThat(gameInfo.getWinner()).isNull(),
                () -> assertThat(team).isEqualTo(Team.CHO));
    }

    @Test
    void 게임이_없으면_빈_Optional을_반환한다() {
        Optional<GameInfo> gameInfo = repository.findGame();

        assertThat(gameInfo).isEmpty();
    }

    @Test
    void 턴을_업데이트하면_현재_팀이_변경된다() {
        Board board = BoardFactory.create(OUTER_ELEPHANT, OUTER_ELEPHANT);
        int gameId = repository.createGame(board.showBoard());

        repository.updateTurn(gameId, Team.HAN);

        GameInfo gameInfo = repository.findGame().orElseThrow();
        assertThat(Team.valueOf(gameInfo.getCurrentTeam())).isEqualTo(Team.HAN);
    }

    @Test
    void 승자_저장_테스트() {
        Board board = BoardFactory.create(OUTER_ELEPHANT, OUTER_ELEPHANT);
        int gameId = repository.createGame(board.showBoard());

        repository.updateWinner(gameId, Team.CHO);

        GameInfo gameInfo = repository.findGame().orElseThrow();
        assertThat(gameInfo.getWinner()).isEqualTo("CHO");
    }

    @Test
    void 게임을_삭제하면_기물도_함께_삭제된다() {
        Board board = BoardFactory.create(OUTER_ELEPHANT, OUTER_ELEPHANT);
        int gameId = repository.createGame(board.showBoard());

        repository.deleteGame(gameId);

        assertAll(
                () -> assertThat(repository.findGame()).isEmpty(),
                () -> assertThat(repository.findPieces(gameId)).isEmpty());
    }
}
