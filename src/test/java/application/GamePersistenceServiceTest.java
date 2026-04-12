package application;

import static org.assertj.core.api.Assertions.assertThat;

import domain.board.Board;
import domain.board.LeftSangSetup;
import domain.board.RightSangSetup;
import domain.board.SangSetup;
import domain.game.GameResult;
import domain.game.GameScore;
import domain.game.GameStatus;
import domain.game.JanggiGame;
import domain.pieces.Cha;
import domain.pieces.Gung;
import domain.pieces.Piece;
import domain.pieces.PieceType;
import domain.pieces.Side;
import domain.position.Position;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import repository.GameRepository;

class GamePersistenceServiceTest {
    private static final LocalDateTime SAVED_AT = LocalDateTime.of(2026, 4, 8, 21, 0);

    @Test
    void 진행중인_게임이_있으면_복원한_세션을_반환한다() {
        // given
        InMemoryGameRepository repository = new InMemoryGameRepository();
        GamePersistenceService service = new GamePersistenceService(repository);
        Map<Position, Piece> pieces = new HashMap<>();
        pieces.put(new Position(0, 0), new Cha(Side.CHO));
        pieces.put(new Position(8, 4), new Gung(Side.HAN));
        repository.latestRunningGame = new GameSession(
                1L,
                LocalDateTime.of(2026, 4, 7, 10, 0),
                JanggiGame.restore(new Board(pieces), Side.HAN, GameResult.running())
        );

        // when
        Optional<GameSession> result = service.loadLatestRunningGame();

        // then
        assertThat(result).isPresent();
        GameSession session = result.orElseThrow();
        assertThat(session.gameId()).isEqualTo(1L);
        assertThat(session.createdAt()).isEqualTo(LocalDateTime.of(2026, 4, 7, 10, 0));
        assertThat(session.game().currentTurn()).isEqualTo(Side.HAN);
        assertThat(session.game().board().pieces().get(new Position(0, 0)).getType()).isEqualTo(PieceType.CHA);
        assertThat(session.game().board().pieces().get(new Position(8, 4)).getType()).isEqualTo(PieceType.GUNG);
    }

    @Test
    void 진행중인_게임이_없으면_새_게임을_생성하고_저장한다() {
        // given
        InMemoryGameRepository repository = new InMemoryGameRepository();
        GamePersistenceService service = new GamePersistenceService(repository);
        SangSetup choSangSetup = new LeftSangSetup();
        SangSetup hanSangSetup = new RightSangSetup();

        // when
        GameSession session = service.createNewGame(choSangSetup, hanSangSetup);

        // then
        assertThat(session.gameId()).isEqualTo(1L);
        assertThat(session.createdAt()).isEqualTo(SAVED_AT);
        assertThat(repository.savedGames).hasSize(1);
        assertThat(repository.savedGames.getFirst().gameId()).isEqualTo(1L);
        assertThat(repository.savedGames.getFirst().game().gameResult()).isEqualTo(GameResult.running());
        assertThat(repository.savedGames.getFirst().game().board().pieces()).isNotEmpty();
    }

    @Test
    void 진행_상태를_저장하면_기존_게임을_업데이트한다() {
        // given
        InMemoryGameRepository repository = new InMemoryGameRepository();
        GamePersistenceService service = new GamePersistenceService(repository);
        JanggiGame game = JanggiGame.of(new LeftSangSetup(), new RightSangSetup());
        GameSession session = new GameSession(
                10L,
                LocalDateTime.of(2026, 4, 7, 9, 0),
                game
        );

        // when
        service.saveProgress(session);

        // then
        assertThat(repository.updatedSession).isNotNull();
        assertThat(repository.updatedSession.gameId()).isEqualTo(10L);
        assertThat(repository.updatedSession.createdAt()).isEqualTo(LocalDateTime.of(2026, 4, 7, 9, 0));
    }

    @Test
    void 시작시_진행중인_게임이_있으면_이어하기_결과를_반환한다() {
        // given
        InMemoryGameRepository repository = new InMemoryGameRepository();
        Map<Position, Piece> pieces = new HashMap<>();
        pieces.put(new Position(0, 0), new Cha(Side.CHO));
        repository.latestRunningGame = new GameSession(
                3L,
                LocalDateTime.of(2026, 4, 7, 10, 0),
                JanggiGame.restore(new Board(pieces), Side.CHO, GameResult.running())
        );
        GameService service = new GameService(new GamePersistenceService(repository));

        // when
        GameStartResult result = service.startOrResume(() -> new GameSession(99L, LocalDateTime.now(),
                JanggiGame.of(new LeftSangSetup(), new RightSangSetup())));

        // then
        assertThat(result.resumed()).isTrue();
        assertThat(result.session().gameId()).isEqualTo(3L);
    }

    @Test
    void 점수로_종료하면_게임을_종료상태로_저장한다() {
        // given
        InMemoryGameRepository repository = new InMemoryGameRepository();
        GameService service = new GameService(new GamePersistenceService(repository));
        JanggiGame game = JanggiGame.of(new LeftSangSetup(), new RightSangSetup());
        GameSession session = new GameSession(
                10L,
                LocalDateTime.of(2026, 4, 7, 9, 0),
                game
        );

        // when
        GameScore gameScore = service.finishByScore(session);

        // then
        assertThat(gameScore.winner()).isEqualTo(session.game().gameResult().winner());
        assertThat(repository.updatedSession).isNotNull();
        assertThat(repository.updatedSession.game().gameResult().status()).isEqualTo(GameStatus.ENDED);
        assertThat(repository.updatedSession.game().gameResult().winner()).isEqualTo(session.game().gameResult().winner());
    }

    private static class InMemoryGameRepository implements GameRepository {
        private long sequence = 1L;
        private final java.util.List<GameSession> savedGames = new ArrayList<>();
        private GameSession latestRunningGame;
        private GameSession updatedSession;

        @Override
        public GameSession save(JanggiGame janggiGame) {
            GameSession session = new GameSession(sequence++, SAVED_AT, janggiGame);
            savedGames.add(session);
            return session;
        }

        @Override
        public Optional<GameSession> findLatestRunningGame() {
            return Optional.ofNullable(latestRunningGame);
        }

        @Override
        public void update(GameSession session) {
            updatedSession = session;
        }
    }
}
