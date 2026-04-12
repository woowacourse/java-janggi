package janggi.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import janggi.domain.JanggiGame;
import janggi.domain.Position;
import janggi.domain.team.TeamType;
import janggi.persistence.GameStatus;
import janggi.persistence.dto.MoveHistory;
import janggi.persistence.model.JanggiGameHistory;
import janggi.persistence.repository.JanggiGameRepository;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class JanggiServiceTest {

    @Test
    @DisplayName("진행 중인 게임이 없으면 새 게임을 생성하고 초기 장기 게임을 반환한다.")
    void loadInitialGameWhenNoRecentGameExists() {
        // given
        FakeJanggiGameRepository repository = new FakeJanggiGameRepository(JanggiGameHistory.createEmpty());
        JanggiService janggiService = new JanggiService(repository);

        // when
        JanggiGame janggiGame = janggiService.loadGame();

        // then
        assertAll(
            () -> assertThat(repository.createNewGameCalled).isTrue(),
            () -> assertThat(janggiGame.getTurnCount()).isZero(),
            () -> assertThat(janggiGame.getCurrentTurnTeam()).isEqualTo(TeamType.CHU)
        );
    }

    @Test
    @DisplayName("진행 중인 게임이 있으면 최근 기록으로 장기 게임을 복원한다.")
    void rebuildGameFromRecentGameHistory() {
        // given
        JanggiGameHistory gameHistory = new JanggiGameHistory(
            7L,
            List.of(new MoveHistory(1, 1, 4, 2, 4)),
            GameStatus.IN_PROGRESS
        );
        FakeJanggiGameRepository repository = new FakeJanggiGameRepository(gameHistory);
        JanggiService janggiService = new JanggiService(repository);

        // when
        JanggiGame janggiGame = janggiService.loadGame();

        // then
        assertAll(
            () -> assertThat(repository.createNewGameCalled).isFalse(),
            () -> assertThat(janggiGame.getTurnCount()).isEqualTo(1),
            () -> assertThat(janggiGame.getCurrentTurnTeam()).isEqualTo(TeamType.HAN)
        );
    }

    @Test
    @DisplayName("게임을 진행하면 이동 결과를 저장소에 저장한다.")
    void playAndSaveMove() {
        // given
        FakeJanggiGameRepository repository = new FakeJanggiGameRepository(JanggiGameHistory.createEmpty());
        JanggiService janggiService = new JanggiService(repository);
        JanggiGame janggiGame = janggiService.loadGame();
        Position startPosition = new Position(1, 4);
        Position endPosition = new Position(2, 4);

        // when
        janggiService.play(janggiGame, startPosition, endPosition);

        // then
        assertAll(
            () -> assertThat(janggiGame.getTurnCount()).isEqualTo(1),
            () -> assertThat(janggiGame.getCurrentTurnTeam()).isEqualTo(TeamType.HAN),
            () -> assertThat(repository.savedGameId).isEqualTo(repository.createdGameId),
            () -> assertThat(repository.savedTurnNumber).isEqualTo(1),
            () -> assertThat(repository.savedStartPosition).isEqualTo(startPosition),
            () -> assertThat(repository.savedEndPosition).isEqualTo(endPosition)
        );
    }

    @Test
    @DisplayName("게임 종료 시 저장소에 종료 상태를 반영한다.")
    void finishGame() {
        // given
        FakeJanggiGameRepository repository = new FakeJanggiGameRepository(JanggiGameHistory.createEmpty());
        JanggiService janggiService = new JanggiService(repository);
        janggiService.loadGame();

        // when
        janggiService.finishGame();

        // then
        assertAll(
            () -> assertThat(repository.updatedGameStatus).isEqualTo(GameStatus.FINISHED),
            () -> assertThat(repository.updatedGameId).isEqualTo(repository.createdGameId)
        );
    }

    private static class FakeJanggiGameRepository implements JanggiGameRepository {

        private final JanggiGameHistory recentGame;
        private final long createdGameId = 1L;
        private boolean createNewGameCalled;
        private long savedGameId;
        private int savedTurnNumber;
        private Position savedStartPosition;
        private Position savedEndPosition;
        private GameStatus updatedGameStatus;
        private long updatedGameId;

        private FakeJanggiGameRepository(JanggiGameHistory recentGame) {
            this.recentGame = recentGame;
        }

        @Override
        public long createNewGame() {
            createNewGameCalled = true;
            return createdGameId;
        }

        @Override
        public void saveMove(long gameId, int turnNumber, Position startPosition, Position endPosition) {
            savedGameId = gameId;
            savedTurnNumber = turnNumber;
            savedStartPosition = startPosition;
            savedEndPosition = endPosition;
        }

        @Override
        public void update(GameStatus gameStatus, long gameId) {
            updatedGameStatus = gameStatus;
            updatedGameId = gameId;
        }

        @Override
        public JanggiGameHistory findRecentGame() {
            return recentGame;
        }
    }
}
