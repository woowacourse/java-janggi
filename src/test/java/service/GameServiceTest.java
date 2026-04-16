package service;

import static org.assertj.core.api.Assertions.assertThat;

import domain.board.BoardFactory;
import domain.board.Formation;
import domain.game.Game;
import domain.game.GameStatus;
import domain.piece.Empty;
import domain.piece.Side;
import domain.piece.Soldier;
import domain.vo.Position;
import fixture.BoardFixture;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import repository.game_record.GameRecordRepository;
import repository.game_record.dto.GameRecord;
import repository.move_record.MoveRecordRepository;
import repository.move_record.dto.MoveRecord;

class GameServiceTest {

    @Nested
    @DisplayName("저장된 게임 존재 여부 조회")
    class ExistsGame {

        @DisplayName("진행 중인 게임이 하나면 true를 반환한다.")
        @Test
        void 진행_중인_게임이_하나면_true를_반환한다() {
            FakeMoveRecordRepository moveRecordRepository = new FakeMoveRecordRepository();
            FakeGameRecordRepository gameRecordRepository = new FakeGameRecordRepository();
            gameRecordRepository.storedGameRecords.add(
                new GameRecord(1L, Formation.from("1"), Formation.from("1"), GameStatus.IN_PROGRESS)
            );
            GameService gameService = new GameService(moveRecordRepository, gameRecordRepository);

            boolean actual = gameService.existsGame();

            assertThat(actual).isTrue();
        }

        @DisplayName("진행 중인 게임이 없으면 false를 반환한다.")
        @Test
        void 진행_중인_게임이_없으면_false를_반환한다() {
            FakeMoveRecordRepository moveRecordRepository = new FakeMoveRecordRepository();
            FakeGameRecordRepository gameRecordRepository = new FakeGameRecordRepository();
            gameRecordRepository.storedGameRecords.add(
                new GameRecord(1L, Formation.from("1"), Formation.from("1"), GameStatus.FINISHED)
            );
            GameService gameService = new GameService(moveRecordRepository, gameRecordRepository);

            boolean actual = gameService.existsGame();

            assertThat(actual).isFalse();
        }

        @DisplayName("진행 중인 게임이 두 개 이상이면 false를 반환한다.")
        @Test
        void 진행_중인_게임이_두_개_이상이면_false를_반환한다() {
            FakeMoveRecordRepository moveRecordRepository = new FakeMoveRecordRepository();
            FakeGameRecordRepository gameRecordRepository = new FakeGameRecordRepository();
            gameRecordRepository.storedGameRecords.add(
                new GameRecord(1L, Formation.from("1"), Formation.from("1"), GameStatus.IN_PROGRESS)
            );
            gameRecordRepository.storedGameRecords.add(
                new GameRecord(2L, Formation.from("2"), Formation.from("2"), GameStatus.IN_PROGRESS)
            );
            GameService gameService = new GameService(moveRecordRepository, gameRecordRepository);

            boolean actual = gameService.existsGame();

            assertThat(actual).isFalse();
            assertThat(gameRecordRepository.updateGameStatusesCallCount).isZero();
            assertThat(gameRecordRepository.findAllGameRecordsByGameStatus(GameStatus.IN_PROGRESS)).hasSize(2);
        }
    }

    @Nested
    @DisplayName("게임 생성")
    class CreateGame {

        @DisplayName("기존 진행 중인 게임은 종료 처리하고 새 게임 정보를 저장한다.")
        @Test
        void 기존_진행_중인_게임은_종료_처리하고_새_게임_정보를_저장한다() {
            FakeMoveRecordRepository moveRecordRepository = new FakeMoveRecordRepository();
            FakeGameRecordRepository gameRecordRepository = new FakeGameRecordRepository();
            gameRecordRepository.storedGameRecords.add(
                new GameRecord(1L, Formation.from("1"), Formation.from("1"), GameStatus.IN_PROGRESS)
            );
            moveRecordRepository.save(1L, new MoveRecord(1, 7, 1, 6, Side.CHO));
            GameService gameService = new GameService(moveRecordRepository, gameRecordRepository);

            Game game = gameService.finishGamesAndCreateGame(Formation.from("1"), Formation.from("2"));

            assertThat(gameRecordRepository.updateGameStatusesCallCount).isEqualTo(1);
            assertThat(gameRecordRepository.saveCallCount).isEqualTo(1);
            assertThat(gameRecordRepository.storedGameRecords).hasSize(2);
            assertThat(gameRecordRepository.storedGameRecords)
                .anySatisfy(gameRecord -> assertThat(gameRecord).isEqualTo(
                    new GameRecord(1L, Formation.from("1"), Formation.from("1"), GameStatus.FINISHED)
                ))
                .anySatisfy(gameRecord -> {
                    assertThat(gameRecord.choFormation()).isEqualTo(Formation.from("1"));
                    assertThat(gameRecord.hanFormation()).isEqualTo(Formation.from("2"));
                    assertThat(gameRecord.gameStatus()).isEqualTo(GameStatus.IN_PROGRESS);
                });
            assertThat(moveRecordRepository.findAllByGameRecordId(1L)).containsExactly(
                new MoveRecord(1, 7, 1, 6, Side.CHO)
            );
            assertThat(game.getCurrentTurn()).isEqualTo(Side.CHO);
            assertThat(game.isGameEnd()).isFalse();
        }
    }

    @Nested
    @DisplayName("게임 불러오기")
    class LoadGame {

        @DisplayName("진행 중인 게임의 포진과 해당 게임의 기보만 반영해 게임을 복원한다.")
        @Test
        void 진행_중인_게임의_포진과_해당_게임의_기보만_반영해_게임을_복원한다() {
            FakeMoveRecordRepository moveRecordRepository = new FakeMoveRecordRepository();
            FakeGameRecordRepository gameRecordRepository = new FakeGameRecordRepository();
            gameRecordRepository.storedGameRecords.add(
                new GameRecord(1L, Formation.from("1"), Formation.from("1"), GameStatus.FINISHED)
            );
            gameRecordRepository.storedGameRecords.add(
                new GameRecord(2L, Formation.from("1"), Formation.from("1"), GameStatus.IN_PROGRESS)
            );
            moveRecordRepository.save(1L, new MoveRecord(2, 7, 2, 6, Side.CHO));
            moveRecordRepository.save(2L, new MoveRecord(1, 7, 1, 6, Side.CHO));
            GameService gameService = new GameService(moveRecordRepository, gameRecordRepository);

            Game game = gameService.loadGame();

            assertThat(game.getBoard().get(Position.of(1, 6))).isInstanceOf(Soldier.class);
            assertThat(game.getBoard().get(Position.of(1, 6)).isSameSide(Side.CHO)).isTrue();
            assertThat(game.getBoard().get(Position.of(1, 7))).isInstanceOf(Empty.class);
            assertThat(game.getCurrentTurn()).isEqualTo(Side.HAN);
            assertThat(gameRecordRepository.requestedGameStatus).isEqualTo(GameStatus.IN_PROGRESS);
            assertThat(moveRecordRepository.requestedGameRecordId).isEqualTo(2L);
        }
    }

    @Nested
    @DisplayName("기물 이동 및 저장")
    class MoveAndSave {

        @DisplayName("게임이 종료되지 않으면 진행 중인 게임 ID로 이동 기록을 저장한다.")
        @Test
        void 게임이_종료되지_않으면_진행_중인_게임_ID로_이동_기록을_저장한다() {
            FakeMoveRecordRepository moveRecordRepository = new FakeMoveRecordRepository();
            FakeGameRecordRepository gameRecordRepository = new FakeGameRecordRepository();
            gameRecordRepository.storedGameRecords.add(
                new GameRecord(1L, Formation.from("1"), Formation.from("1"), GameStatus.IN_PROGRESS)
            );
            GameService gameService = new GameService(moveRecordRepository, gameRecordRepository);
            Game game = new Game(BoardFactory.createBoard(Formation.from("1"), Formation.from("1")));
            Position source = Position.of(1, 7);
            Position target = Position.of(1, 6);

            gameService.moveAndSave(game, source, target);

            assertThat(moveRecordRepository.findAllByGameRecordId(1L)).containsExactly(
                new MoveRecord(1, 7, 1, 6, Side.CHO)
            );
            assertThat(gameRecordRepository.updateGameStatusCallCount).isZero();
        }

        @DisplayName("게임이 종료되면 마지막 이동을 저장하고 진행 중인 게임을 모두 FINISHED로 변경한다.")
        @Test
        void 게임이_종료되면_마지막_이동을_저장하고_진행_중인_게임을_모두_FINISHED로_변경한다() {
            FakeMoveRecordRepository moveRecordRepository = new FakeMoveRecordRepository();
            FakeGameRecordRepository gameRecordRepository = new FakeGameRecordRepository();
            gameRecordRepository.storedGameRecords.add(
                new GameRecord(1L, Formation.from("1"), Formation.from("1"), GameStatus.IN_PROGRESS)
            );
            gameRecordRepository.storedGameRecords.add(
                new GameRecord(2L, Formation.from("2"), Formation.from("2"), GameStatus.IN_PROGRESS)
            );
            gameRecordRepository.storedGameRecords.add(
                new GameRecord(3L, Formation.from("3"), Formation.from("3"), GameStatus.FINISHED)
            );
            moveRecordRepository.save(2L, new MoveRecord(3, 7, 3, 6, Side.CHO));
            moveRecordRepository.save(3L, new MoveRecord(4, 7, 4, 6, Side.CHO));
            GameService gameService = new GameService(moveRecordRepository, gameRecordRepository);
            Game game = new Game(BoardFixture.createGameEndBoard());
            Position source = Position.of(5, 7);
            Position target = Position.of(5, 6);

            gameService.moveAndSave(game, source, target);

            assertThat(moveRecordRepository.findAllByGameRecordId(1L)).containsExactly(
                new MoveRecord(5, 7, 5, 6, Side.CHO)
            );
            assertThat(moveRecordRepository.findAllByGameRecordId(2L)).containsExactly(
                new MoveRecord(3, 7, 3, 6, Side.CHO)
            );
            assertThat(moveRecordRepository.findAllByGameRecordId(3L)).containsExactly(
                new MoveRecord(4, 7, 4, 6, Side.CHO)
            );
            assertThat(gameRecordRepository.updateGameStatusCallCount).isZero();
            assertThat(gameRecordRepository.updateGameStatusesCallCount).isEqualTo(1);
            assertThat(gameRecordRepository.findAllGameRecordsByGameStatus(GameStatus.IN_PROGRESS)).isEmpty();
            assertThat(gameRecordRepository.findAllGameRecordsByGameStatus(GameStatus.FINISHED))
                .anySatisfy(gameRecord -> assertThat(gameRecord).isEqualTo(
                    new GameRecord(1L, Formation.from("1"), Formation.from("1"), GameStatus.FINISHED)
                ))
                .anySatisfy(gameRecord -> assertThat(gameRecord).isEqualTo(
                    new GameRecord(2L, Formation.from("2"), Formation.from("2"), GameStatus.FINISHED)
                ))
                .anySatisfy(gameRecord -> assertThat(gameRecord).isEqualTo(
                    new GameRecord(3L, Formation.from("3"), Formation.from("3"), GameStatus.FINISHED)
                ));
        }
    }

    private static class FakeMoveRecordRepository implements MoveRecordRepository {

        private final Map<Long, List<MoveRecord>> storedMoveRecordsByGameId = new HashMap<>();
        private Long requestedGameRecordId;

        @Override
        public void save(Long gameRecordId, MoveRecord moveRecord) {
            storedMoveRecordsByGameId.computeIfAbsent(gameRecordId, ignored -> new ArrayList<>()).add(moveRecord);
        }

        @Override
        public List<MoveRecord> findAllByGameRecordId(Long gameRecordId) {
            requestedGameRecordId = gameRecordId;
            return new ArrayList<>(storedMoveRecordsByGameId.getOrDefault(gameRecordId, List.of()));
        }
    }

    private static class FakeGameRecordRepository implements GameRecordRepository {

        private final List<GameRecord> storedGameRecords = new ArrayList<>();
        private GameStatus requestedGameStatus;
        private Long updatedGameRecordId;
        private long nextGameRecordId = 1L;
        private int saveCallCount;
        private int updateGameStatusCallCount;
        private int updateGameStatusesCallCount;

        @Override
        public List<GameRecord> findAllGameRecordsByGameStatus(GameStatus status) {
            requestedGameStatus = status;
            return storedGameRecords.stream()
                .filter(gameRecord -> gameRecord.gameStatus() == status)
                .toList();
        }

        @Override
        public GameRecord findGameRecordByGameStatus(GameStatus status) {
            requestedGameStatus = status;
            return storedGameRecords.stream()
                .filter(gameRecord -> gameRecord.gameStatus() == status)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("게임 기록이 존재하지 않습니다."));
        }

        @Override
        public void save(GameRecord gameRecord) {
            saveCallCount++;
            nextGameRecordId = Math.max(nextGameRecordId, maxGameRecordId() + 1);
            storedGameRecords.add(new GameRecord(
                nextGameRecordId++,
                gameRecord.choFormation(),
                gameRecord.hanFormation(),
                gameRecord.gameStatus()
            ));
        }

        @Override
        public void updateGameStatus(Long gameRecordId, GameStatus gameStatus) {
            updateGameStatusCallCount++;
            updatedGameRecordId = gameRecordId;
            replaceGameRecord(gameRecordId, gameStatus);
        }

        @Override
        public void updateGameStatuses(GameStatus sourceStatus, GameStatus targetStatus) {
            updateGameStatusesCallCount++;
            List<GameRecord> updatedGameRecords = new ArrayList<>();
            for (GameRecord gameRecord : storedGameRecords) {
                if (gameRecord.gameStatus() == sourceStatus) {
                    updatedGameRecords.add(new GameRecord(
                        gameRecord.id(),
                        gameRecord.choFormation(),
                        gameRecord.hanFormation(),
                        targetStatus
                    ));
                    continue;
                }
                updatedGameRecords.add(gameRecord);
            }
            storedGameRecords.clear();
            storedGameRecords.addAll(updatedGameRecords);
        }

        private void replaceGameRecord(Long gameRecordId, GameStatus gameStatus) {
            for (int index = 0; index < storedGameRecords.size(); index++) {
                GameRecord gameRecord = storedGameRecords.get(index);
                if (gameRecord.id().equals(gameRecordId)) {
                    storedGameRecords.set(index, new GameRecord(
                        gameRecord.id(),
                        gameRecord.choFormation(),
                        gameRecord.hanFormation(),
                        gameStatus
                    ));
                    return;
                }
            }
            throw new IllegalStateException("게임 기록이 존재하지 않습니다.");
        }

        private long maxGameRecordId() {
            return storedGameRecords.stream()
                .mapToLong(GameRecord::id)
                .max()
                .orElse(0L);
        }
    }
}
