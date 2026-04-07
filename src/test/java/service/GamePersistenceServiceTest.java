package service;

import static org.assertj.core.api.Assertions.assertThat;

import domain.board.formation.FormationType;
import domain.game.JanggiGame;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.Test;
import repository.GameRepository;

class GamePersistenceServiceTest {

    @Test
    void 진행중인_게임이_있으면_복원하고_새_게임은_만들지_않는다() {
        InMemoryGameRepository gameRepository = new InMemoryGameRepository();
        JanggiGame savedGame = JanggiGame.of(FormationType.DEFAULT, FormationType.DEFAULT);
        savedGame.playTurn(java.util.List.of(5, 7), java.util.List.of(5, 6));
        gameRepository.loadedGame = new LoadedGame(7L, savedGame);

        GamePersistenceService gamePersistenceService = new GamePersistenceService(gameRepository);
        AtomicInteger createdCount = new AtomicInteger();

        LoadedGame loadedGame = gamePersistenceService.loadOrCreate(() -> {
            createdCount.incrementAndGet();
            return JanggiGame.of(FormationType.INNER_HORSE, FormationType.INNER_HORSE);
        });

        assertThat(createdCount.get()).isZero();
        assertThat(loadedGame.gameId()).isEqualTo(7L);
        assertThat(loadedGame.game().turn()).isEqualTo(savedGame.turn());
        assertThat(loadedGame.game().createBoardDto()).isEqualTo(savedGame.createBoardDto());
    }

    @Test
    void 진행중인_게임이_없으면_새_게임을_생성하고_즉시_저장한다() {
        InMemoryGameRepository gameRepository = new InMemoryGameRepository();
        GamePersistenceService gamePersistenceService = new GamePersistenceService(gameRepository);

        LoadedGame loadedGame = gamePersistenceService.loadOrCreate(
                () -> JanggiGame.of(FormationType.DEFAULT, FormationType.DEFAULT)
        );

        assertThat(loadedGame.gameId()).isEqualTo(1L);
        assertThat(gameRepository.loadedGame).isNotNull();
        assertThat(gameRepository.loadedGame.gameId()).isEqualTo(1L);
        assertThat(gameRepository.loadedGame.game().turn()).isEqualTo(loadedGame.game().turn());
    }

    private static class InMemoryGameRepository implements GameRepository {

        private long nextId = 1L;
        private LoadedGame loadedGame;

        @Override
        public Optional<LoadedGame> findInProgressGame() {
            return Optional.ofNullable(loadedGame);
        }

        @Override
        public LoadedGame save(LoadedGame loadedGame) {
            if (loadedGame.gameId() == null) {
                long id = nextId++;
                this.loadedGame = new LoadedGame(id, loadedGame.game());
                return this.loadedGame;
            }
            this.loadedGame = loadedGame;
            return loadedGame;
        }
    }
}
