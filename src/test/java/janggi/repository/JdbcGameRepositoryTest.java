package janggi.repository;

import janggi.db.DBInitializer;
import janggi.domain.JanggiGame;
import janggi.domain.Team;
import janggi.domain.board.BoardFactory;
import janggi.dto.GameInfo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static janggi.domain.board.PieceSetup.OUTER_ELEPHANT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class JdbcGameRepositoryTest {

    private Connection conn;
    private JdbcGameRepository repository;

    @BeforeEach
    void setUp() throws SQLException {
        conn = DriverManager.getConnection("jdbc:h2:mem:test");
        DBInitializer.initialize(conn);
        repository = new JdbcGameRepository(conn);
    }

    @AfterEach
    void tearDown() throws SQLException {
        conn.createStatement().execute("DROP ALL OBJECTS");
        conn.close();
    }

    @DisplayName("게임을 생성하면 ID를 반환한다")
    @Test
    void 게임을_생성하면_ID를_반환한다() {
        // given
        JanggiGame game = new JanggiGame(BoardFactory.create(OUTER_ELEPHANT, OUTER_ELEPHANT), Team.FIRST_TURN);

        // when
        long gameId = repository.createGame(game);

        // then
        assertThat(gameId).isPositive();
    }

    @DisplayName("게임 ID로 게임을 조회할 수 있다.")
    @Test
    void 생성한_게임을_조회할_수_있다() {
        // given
        JanggiGame game = new JanggiGame(BoardFactory.create(OUTER_ELEPHANT, OUTER_ELEPHANT), Team.FIRST_TURN);
        long gameId = repository.createGame(game);

        // when
        JanggiGame loaded = repository.getById(gameId).get();

        // then
        assertAll(
                () -> assertThat(loaded.getCurrentTeam()).isEqualTo(Team.FIRST_TURN),
                () -> assertThat(loaded.getBoard()).hasSize(90)
        );
    }

    @DisplayName("게임을 업데이트하면 변경사항이 반영된다.")
    @Test
    void 게임_업데이트_후_변경사항이_반영된다() {
        // given
        JanggiGame game = new JanggiGame(BoardFactory.create(OUTER_ELEPHANT, OUTER_ELEPHANT), Team.FIRST_TURN);
        long gameId = repository.createGame(game);

        JanggiGame loaded = repository.getById(gameId).get();
        loaded.play(new janggi.domain.position.Movement(
                janggi.domain.position.Position.from("71"),
                janggi.domain.position.Position.from("61")));

        // when
        repository.saveGameState(gameId, loaded);

        // then
        JanggiGame updated = repository.getById(gameId).get();
        assertThat(updated.getCurrentTeam()).isEqualTo(Team.HAN);
    }

    @DisplayName("게임 목록을 조회할 수 있다.")
    @Test
    void 게임_목록을_조회할_수_있다() {
        // given
        JanggiGame game1 = new JanggiGame(BoardFactory.create(OUTER_ELEPHANT, OUTER_ELEPHANT), Team.FIRST_TURN);
        JanggiGame game2 = new JanggiGame(BoardFactory.create(OUTER_ELEPHANT, OUTER_ELEPHANT), Team.FIRST_TURN);
        repository.createGame(game1);
        repository.createGame(game2);

        // when
        List<GameInfo> games = repository.findAllGames();

        // then
        assertThat(games).hasSize(2);
    }

    @DisplayName("게임 목록은 최신순으로 정렬된다.")
    @Test
    void 게임_목록은_최신순으로_정렬된다() {
        // given
        JanggiGame game1 = new JanggiGame(BoardFactory.create(OUTER_ELEPHANT, OUTER_ELEPHANT), Team.FIRST_TURN);
        JanggiGame game2 = new JanggiGame(BoardFactory.create(OUTER_ELEPHANT, OUTER_ELEPHANT), Team.FIRST_TURN);
        long id1 = repository.createGame(game1);
        long id2 = repository.createGame(game2);

        // when
        repository.saveGameState(id1, game1);
        List<GameInfo> games = repository.findAllGames();

        // then
        assertThat(games.getFirst().getId()).isEqualTo(id1);
    }

    @DisplayName("게임 목록에 점수가 포함된다.")
    @Test
    void 게임_목록에_점수가_포함된다() {
        // given
        JanggiGame game = new JanggiGame(BoardFactory.create(OUTER_ELEPHANT, OUTER_ELEPHANT), Team.FIRST_TURN);
        repository.createGame(game);

        // when
        GameInfo info = repository.findAllGames().getFirst();

        // then
        assertAll(
                () -> assertThat(info.getHanScore()).isEqualTo(73.5),
                () -> assertThat(info.getChoScore()).isEqualTo(72.0)
        );
    }

    @DisplayName("게임을 삭제하면 조회되지 않는다.")
    @Test
    void 게임을_삭제하면_조회되지_않는다() {
        // given
        JanggiGame game = new JanggiGame(BoardFactory.create(OUTER_ELEPHANT, OUTER_ELEPHANT), Team.FIRST_TURN);
        long gameId = repository.createGame(game);

        // when
        repository.deleteGame(gameId);

        // then
        assertThat(repository.findAllGames()).isEmpty();
    }

    @DisplayName("존재하지 않는 게임을 삭제하면 거짓이 반환된다.")
    @Test
    void 존재하지_않는_게임을_삭제하면_거짓이_반환된다() {
        // when
        boolean result = repository.deleteGame(999L);

        // then
        assertThat(result).isFalse();
    }

    @DisplayName("존재하지 않는 게임을 조회하면 Empty가 반환된다.")
    @Test
    void 존재하지_않는_게임을_조회하면_Empty가_반환된다() {
        // when & then
        assertThat(repository.getById(999L)).isEmpty();
    }

    @DisplayName("게임이 없으면 빈 목록을 반환한다.")
    @Test
    void 게임이_없으면_빈_목록을_반환한다() {
        // when & then
        assertThat(repository.findAllGames()).isEmpty();
    }
}
