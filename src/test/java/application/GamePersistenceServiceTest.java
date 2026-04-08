package application;

import static org.assertj.core.api.Assertions.assertThat;

import domain.board.LeftSangSetup;
import domain.board.RightSangSetup;
import domain.board.SangSetup;
import domain.game.GameScore;
import domain.game.GameStatus;
import domain.game.JanggiGame;
import domain.pieces.PieceType;
import domain.pieces.Side;
import domain.position.Position;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import repository.GameRepository;
import repository.SavedGameDto;
import repository.SavedGameReadMapper;
import repository.SavedGameWriteMapper;
import repository.SavedPieceDto;

class GamePersistenceServiceTest {
    private final Clock fixedClock = Clock.fixed(Instant.parse("2026-04-08T12:00:00Z"), ZoneId.of("Asia/Seoul"));
    private final SavedGameWriteMapper writeMapper = new SavedGameWriteMapper(fixedClock);
    private final SavedGameReadMapper readMapper = new SavedGameReadMapper();

    @Test
    void 진행중인_게임이_있으면_복원한_세션을_반환한다() {
        // given
        InMemoryGameRepository repository = new InMemoryGameRepository();
        GamePersistenceService service = new GamePersistenceService(repository, writeMapper, readMapper);
        SavedGameDto savedGameDto = new SavedGameDto(
                1L,
                Side.HAN,
                GameStatus.RUNNING,
                null,
                LocalDateTime.of(2026, 4, 7, 10, 0),
                LocalDateTime.of(2026, 4, 7, 10, 5),
                List.of(
                        new SavedPieceDto(0, 0, Side.CHO, PieceType.CHA),
                        new SavedPieceDto(8, 4, Side.HAN, PieceType.GUNG)
                )
        );
        repository.latestRunningGame = savedGameDto;

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
        GamePersistenceService service = new GamePersistenceService(repository, writeMapper, readMapper);
        SangSetup choSangSetup = new LeftSangSetup();
        SangSetup hanSangSetup = new RightSangSetup();

        // when
        GameSession session = service.createNewGame(choSangSetup, hanSangSetup);

        // then
        assertThat(session.gameId()).isEqualTo(1L);
        assertThat(session.createdAt()).isEqualTo(LocalDateTime.of(2026, 4, 8, 21, 0));
        assertThat(repository.savedGames).hasSize(1);
        assertThat(repository.savedGames.getFirst().gameId()).isNull();
        assertThat(repository.savedGames.getFirst().status()).isEqualTo(GameStatus.RUNNING);
        assertThat(repository.savedGames.getFirst().pieces()).isNotEmpty();
    }

    @Test
    void 진행_상태를_저장하면_기존_게임을_업데이트한다() {
        // given
        InMemoryGameRepository repository = new InMemoryGameRepository();
        GamePersistenceService service = new GamePersistenceService(repository, writeMapper, readMapper);
        JanggiGame game = JanggiGame.of(new LeftSangSetup(), new RightSangSetup());
        GameSession session = new GameSession(
                10L,
                LocalDateTime.of(2026, 4, 7, 9, 0),
                game
        );

        // when
        service.saveProgress(session);

        // then
        assertThat(repository.updatedGame).isNotNull();
        assertThat(repository.updatedGame.gameId()).isEqualTo(10L);
        assertThat(repository.updatedGame.createdAt()).isEqualTo(LocalDateTime.of(2026, 4, 7, 9, 0));
        assertThat(repository.updatedGame.updatedAt()).isEqualTo(LocalDateTime.of(2026, 4, 8, 21, 0));
    }

    @Test
    void 시작시_진행중인_게임이_있으면_이어하기_결과를_반환한다() {
        // given
        InMemoryGameRepository repository = new InMemoryGameRepository();
        repository.latestRunningGame = new SavedGameDto(
                3L,
                Side.CHO,
                GameStatus.RUNNING,
                null,
                LocalDateTime.of(2026, 4, 7, 10, 0),
                LocalDateTime.of(2026, 4, 7, 10, 5),
                List.of(new SavedPieceDto(0, 0, Side.CHO, PieceType.CHA))
        );
        GameService service = new GameService(new GamePersistenceService(repository, writeMapper, readMapper));

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
        GameService service = new GameService(new GamePersistenceService(repository, writeMapper, readMapper));
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
        assertThat(repository.updatedGame).isNotNull();
        assertThat(repository.updatedGame.status()).isEqualTo(GameStatus.ENDED);
        assertThat(repository.updatedGame.winner()).isEqualTo(session.game().gameResult().winner());
    }

    private static class InMemoryGameRepository implements GameRepository {
        private long sequence = 1L;
        private final java.util.List<SavedGameDto> savedGames = new java.util.ArrayList<>();
        private SavedGameDto latestRunningGame;
        private SavedGameDto updatedGame;

        @Override
        public long save(SavedGameDto savedGameDto) {
            savedGames.add(savedGameDto);
            return sequence++;
        }

        @Override
        public Optional<SavedGameDto> findLatestRunningGame() {
            return Optional.ofNullable(latestRunningGame);
        }

        @Override
        public void update(SavedGameDto savedGameDto) {
            updatedGame = savedGameDto;
        }
    }
}
