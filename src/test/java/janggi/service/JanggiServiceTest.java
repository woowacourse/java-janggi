package janggi.service;

import fixture.TestContainer;
import fixture.TestContainerSupport;
import janggi.GameContext;
import janggi.GameId;
import janggi.GameStatus;
import janggi.board.Board;
import janggi.coordinate.Position;
import janggi.coordinate.Vector;
import janggi.player.Team;
import janggi.repository.ConnectionProvider;
import janggi.repository.dto.GameDto;
import janggi.repository.mysql.GameMysqlRepository;
import janggi.repository.mysql.PieceMysqlRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class JanggiServiceTest extends TestContainerSupport {

    private final JanggiService service = new JanggiService(
            getConnectionProvider(),
            new Transaction(),
            new GameMysqlRepository(),
            new PieceMysqlRepository()
    );

    private static ConnectionProvider getConnectionProvider() {
        return () -> {
            try {
                return TestContainer.getConnection();
            } catch (final Exception e) {
                throw new RuntimeException(e);
            }
        };
    }

    @Test
    @DisplayName("새로운 게임 컨텍스트를 생성할 수 있다")
    void createNewContext() {
        // given
        // when
        final GameContext context = service.createNewContext();

        // then
        assertThat(context.getGameId().isSet()).isFalse();
        assertThat(context.getCurrentPlayer().getTeam()).isEqualTo(Team.HAN);
        assertThat(context.getAlivePieces().getPieces().size()).isEqualTo(32);
    }

    @Test
    @DisplayName("게임을 저장하고 다시 불러올 수 있다")
    void saveAndLoadGameContext() {
        // given
        final GameContext context = service.createNewContext();
        final Board board = context.getBoard();
        final Position from = Position.of(4, 1); // 한나라 병
        final Position to = from.add(Vector.create().down());
        service.movePiece(board, context.getCurrentPlayer(), from, to);

        // when
        service.saveGameWithPieces(context);
        final GameId savedId = GameId.from(1);
        final GameContext loaded = service.loadSavedContext(savedId);

        // then
        assertThat(loaded.getGameId()).isEqualTo(savedId);
        assertThat(loaded.getAlivePieces().getByTeam(Team.HAN).getPieces())
                .anyMatch(p -> p.getPosition().equals(to));
    }

    @Test
    @DisplayName("불러온 게임을 다시 저장할 수 있다")
    void LoadAndSaveGameContext() {
        // given
        final GameContext firstContext = service.createNewContext();
        service.saveGameWithPieces(firstContext);

        final GameContext secondContext = service.loadSavedContext(GameId.from(1));
        final Board board = secondContext.getBoard();
        final Position from = Position.of(4, 1); // 한나라 병
        final Position to = from.add(Vector.create().down());
        service.movePiece(board, secondContext.getCurrentPlayer(), from, to);

        // when
        service.saveGameWithPieces(secondContext);

        // then
        final GameContext thirdContext = service.loadSavedContext(secondContext.getGameId());

        assertThat(secondContext.getGameId()).isEqualTo(thirdContext.getGameId());
        assertThat(thirdContext.getAlivePieces().getByTeam(Team.HAN).getPieces())
                .anyMatch(p -> p.getPosition().equals(to));
    }

    @Test
    @DisplayName("저장된 진행 중 게임 목록을 조회할 수 있다")
    void getRunningGames() {
        // given
        final GameContext context = service.createNewContext();
        service.saveGameWithPieces(context);

        // when
        final List<GameDto> games = service.getRunningGames();

        // then
        assertThat(games).isNotEmpty();
    }

    @Test
    @DisplayName("저장된 게임이 없으면 예외가 발생한다")
    void noRunningGameThrows() {
        // given
        // when
        // then
        assertThatThrownBy(service::getRunningGames)
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("저장된 게임이 없습니다");
    }

    @Test
    @DisplayName("게임 종료 상태로 변경할 수 있다")
    void finishGame() throws SQLException {
        // given
        final GameContext context = service.createNewContext();
        service.saveGameWithPieces(context);

        // when
        final GameId savedId = GameId.from(1);
        service.finishGame(savedId);

        // then
        final Optional<GameDto> found = new GameMysqlRepository().findById(TestContainer.getConnection(), savedId);

        assertThat(found).isPresent();
        assertThat(found.get().status()).isEqualTo(GameStatus.FINISHED.name());
    }
}
