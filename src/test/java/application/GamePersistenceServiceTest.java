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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import repository.GameRepository;

class GamePersistenceServiceTest {
    @Test
    void 진행중인_게임이_있으면_복원한_세션을_반환한다() {
        // given
        InMemoryGameRepository repository = new InMemoryGameRepository();
        GamePersistenceService service = new GamePersistenceService(repository);
        Map<Position, Piece> pieces = new HashMap<>();
        pieces.put(new Position(0, 0), new Cha(Side.CHO));
        pieces.put(new Position(8, 4), new Gung(Side.HAN));
        repository.latestRunningGame = JanggiGame.restore(1L, new Board(pieces), Side.HAN, GameResult.running());

        // when
        Optional<JanggiGame> result = service.loadLatestRunningGame();

        // then
        assertThat(result).isPresent();
        JanggiGame janggiGame = result.orElseThrow();
        assertThat(janggiGame.gameId()).isEqualTo(1L);
        assertThat(janggiGame.currentTurn()).isEqualTo(Side.HAN);
        assertThat(janggiGame.board().pieces().get(new Position(0, 0)).getType()).isEqualTo(PieceType.CHA);
        assertThat(janggiGame.board().pieces().get(new Position(8, 4)).getType()).isEqualTo(PieceType.GUNG);
    }

    @Test
    void 진행중인_게임이_없으면_새_게임을_생성하고_저장한다() {
        // given
        InMemoryGameRepository repository = new InMemoryGameRepository();
        GamePersistenceService service = new GamePersistenceService(repository);
        SangSetup choSangSetup = new LeftSangSetup();
        SangSetup hanSangSetup = new RightSangSetup();

        // when
        JanggiGame janggiGame = service.createNewGame(choSangSetup, hanSangSetup);

        // then
        assertThat(janggiGame.gameId()).isEqualTo(1L);
        assertThat(repository.savedGames).hasSize(1);
        assertThat(repository.savedGames.getFirst().gameId()).isEqualTo(1L);
        assertThat(repository.savedGames.getFirst().gameResult()).isEqualTo(GameResult.running());
        assertThat(repository.savedGames.getFirst().board().pieces()).isNotEmpty();
    }

    @Test
    void 진행_상태를_저장하면_기존_게임을_업데이트한다() {
        // given
        InMemoryGameRepository repository = new InMemoryGameRepository();
        GamePersistenceService service = new GamePersistenceService(repository);
        JanggiGame game = JanggiGame.of(new LeftSangSetup(), new RightSangSetup());
        game.assignGameId(10L);

        // when
        service.saveProgress(game);

        // then
        assertThat(repository.updatedGame).isNotNull();
        assertThat(repository.updatedGame.gameId()).isEqualTo(10L);
    }

    @Test
    void 시작시_진행중인_게임이_있으면_이어하기_결과를_반환한다() {
        // given
        InMemoryGameRepository repository = new InMemoryGameRepository();
        Map<Position, Piece> pieces = new HashMap<>();
        pieces.put(new Position(0, 0), new Cha(Side.CHO));
        repository.latestRunningGame = JanggiGame.restore(3L, new Board(pieces), Side.CHO, GameResult.running());
        GameService service = new GameService(new GamePersistenceService(repository));

        // when
        GameStartResult result = service.startOrResume(() -> {
            JanggiGame game = JanggiGame.of(new LeftSangSetup(), new RightSangSetup());
            game.assignGameId(99L);
            return game;
        });

        // then
        assertThat(result.resumed()).isTrue();
        assertThat(result.game().gameId()).isEqualTo(3L);
    }

    @Test
    void 점수로_종료하면_게임을_종료상태로_저장한다() {
        // given
        InMemoryGameRepository repository = new InMemoryGameRepository();
        GameService service = new GameService(new GamePersistenceService(repository));
        JanggiGame game = JanggiGame.of(new LeftSangSetup(), new RightSangSetup());
        game.assignGameId(10L);

        // when
        GameScore gameScore = service.finishByScore(game);

        // then
        assertThat(gameScore.winner()).isEqualTo(game.gameResult().winner());
        assertThat(repository.updatedGame).isNotNull();
        assertThat(repository.updatedGame.gameResult().status()).isEqualTo(GameStatus.ENDED);
        assertThat(repository.updatedGame.gameResult().winner()).isEqualTo(game.gameResult().winner());
    }

    private static class InMemoryGameRepository implements GameRepository {
        private long sequence = 1L;
        private final java.util.List<JanggiGame> savedGames = new ArrayList<>();
        private JanggiGame latestRunningGame;
        private JanggiGame updatedGame;

        @Override
        public JanggiGame save(JanggiGame janggiGame) {
            janggiGame.assignGameId(sequence++);
            savedGames.add(janggiGame);
            return janggiGame;
        }

        @Override
        public Optional<JanggiGame> findLatestRunningGame() {
            return Optional.ofNullable(latestRunningGame);
        }

        @Override
        public void update(JanggiGame janggiGame) {
            updatedGame = janggiGame;
        }
    }
}
