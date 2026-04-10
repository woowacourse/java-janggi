package janggi.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.config.TestConfig;
import janggi.domain.board.HorseElephantPosition;
import janggi.domain.dynasty.Dynasty;
import janggi.domain.game.Game;
import janggi.domain.game.GameState;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import janggi.domain.position.Position;
import janggi.entity.GameEntity;
import janggi.entity.MovementEntity;
import janggi.entity.PieceEntity;
import janggi.repository.game.FakeGameRepository;
import janggi.repository.movement.FakeMovementRepository;
import janggi.repository.piece.FakePieceRepository;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class JanggiServiceTest {

    private FakeGameRepository gameRepository;
    private FakePieceRepository pieceRepository;
    private FakeMovementRepository movementRepository;
    private JanggiService janggiService;

    @BeforeAll
    static void setUpTestDatabaseProperty() {
        TestConfig.setUp();
    }

    @BeforeEach
    void setUp() {
        gameRepository = new FakeGameRepository();
        pieceRepository = new FakePieceRepository();
        movementRepository = new FakeMovementRepository();
        janggiService = new JanggiService(gameRepository, pieceRepository, movementRepository);
    }

    @Test
    void 게임을_생성할_수_있다() {
        // given
        Map<Dynasty, HorseElephantPosition> positions = defaultHorseElephantPositions();

        // when
        Long gameId = janggiService.makeGame(positions);

        // then
        Game game = janggiService.findGame(gameId);
        assertThat(gameId).isNotNull();
        assertThat(game.currentDynasty()).isEqualTo(Dynasty.CHO);
        assertThat(game.pieces()).isNotEmpty();
    }

    @Test
    void 존재하지_않는_게임을_조회하면_예외가_발생한다() {
        // when & then
        assertThatThrownBy(() -> janggiService.findGame(999L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("gameId가 999인 게임이 존재하지 않습니다.");
    }

    @Test
    void 기물을_이동할_수_있다() {
        // given
        Long gameId = janggiService.makeGame(defaultHorseElephantPositions());
        Position from = Position.from(1, 1);
        Position to = Position.from(2, 1);

        // when
        janggiService.movePiece(gameId, from, to);

        // then
        Game game = janggiService.findGame(gameId);
        assertThat(game.currentDynasty()).isEqualTo(Dynasty.HAN);
        assertThat(game.pieces()).doesNotContainKey(from);
        assertThat(game.pieces()).containsKey(to);
    }

    @Test
    void 기물을_이동하면_이동_기록이_저장된다_도착지에_기물이_없는_경우() {
        // given
        Long gameId = janggiService.makeGame(defaultHorseElephantPositions());
        Position from = Position.from(1, 1);
        Position to = Position.from(2, 1);

        // when
        janggiService.movePiece(gameId, from, to);

        // then
        assertThat(movementRepository.findAllByGameId(gameId)).hasSize(1);
        MovementEntity movement = movementRepository.findAllByGameId(gameId).getFirst();

        assertThat(movement.from()).isEqualTo(from);
        assertThat(movement.to()).isEqualTo(to);
        assertThat(movement.destTeam()).isNull();
        assertThat(movement.destType()).isNull();
    }

    @Test
    void 기물을_잡으면_이동_기록에_잡힌_기물_정보가_저장된다() {
        // given
        Long gameId = gameRepository.save(null, GameEntity.toEntity(Dynasty.CHO, GameState.PLAYING));
        pieceRepository.saveAll(null, gameId, List.of(
                PieceEntity.toEntity(1, 1, "CHO", "CHARIOT"),
                PieceEntity.toEntity(1, 2, "HAN", "SOLDIER")
        ));

        Position from = Position.from(1, 1);
        Position to = Position.from(1, 2);

        // when
        janggiService.movePiece(gameId, from, to);

        // then
        assertThat(movementRepository.findAllByGameId(gameId)).hasSize(1);

        MovementEntity movement = movementRepository.findAllByGameId(gameId).getFirst();

        assertThat(movement.from()).isEqualTo(from);
        assertThat(movement.to()).isEqualTo(to);
        assertThat(movement.destTeam()).isEqualTo("HAN");
        assertThat(movement.destType()).isEqualTo("SOLDIER");
    }


    @Test
    void 진행중인_게임_id들을_조회할_수_있다() {
        // given
        Long firstGameId = janggiService.makeGame(defaultHorseElephantPositions());
        Long secondGameId = janggiService.makeGame(defaultHorseElephantPositions());

        // when
        List<Long> gameIds = janggiService.findPlayableGameIds();

        // then
        assertThat(gameIds).contains(firstGameId, secondGameId);
    }

    @Test
    void 진행중인_게임_id를_검증할_수_있다() {
        // given
        Long gameId = janggiService.makeGame(defaultHorseElephantPositions());

        // when
        Long validatedGameId = janggiService.validatePlayableGameId(gameId);

        // then
        assertThat(validatedGameId).isEqualTo(gameId);
    }

    @Test
    void 진행중이지_않은_게임_id를_검증하면_예외가_발생한다() {
        // when & then
        assertThatThrownBy(() -> janggiService.validatePlayableGameId(123L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("gameId가 123인 진행 중 게임이 존재하지 않습니다.");
    }

    @Test
    void 새로_생성한_게임은_종료되지_않았다() {
        // given
        Long gameId = janggiService.makeGame(defaultHorseElephantPositions());

        // when
        boolean finished = janggiService.isFinishedGame(gameId);

        // then
        assertThat(finished).isFalse();
    }

    @Test
    void 무르기를_하면_직전_이동이_되돌아간다() {
        // given
        Long gameId = janggiService.makeGame(defaultHorseElephantPositions());
        Position from = Position.from(1, 1);
        Position to = Position.from(2, 1);

        janggiService.movePiece(gameId, from, to);

        // when
        janggiService.undoMovement(gameId);

        // then
        Game game = janggiService.findGame(gameId);
        assertThat(game.currentDynasty()).isEqualTo(Dynasty.CHO);
        assertThat(game.pieces()).containsKey(from);
        assertThat(game.pieces()).doesNotContainKey(to);
        assertThat(movementRepository.findAllByGameId(gameId)).isEmpty();
    }

    @Test
    void 무르기를_하면_잡힌_기물도_복구된다() {
        // given
        Long gameId = gameRepository.save(null, GameEntity.toEntity(Dynasty.CHO, GameState.PLAYING));
        pieceRepository.saveAll(null, gameId, List.of(
                PieceEntity.toEntity(1, 1, "CHO", "CHARIOT"),
                PieceEntity.toEntity(1, 2, "HAN", "SOLDIER")
        ));

        janggiService.movePiece(gameId, Position.from(1, 1), Position.from(1, 2));

        // when
        janggiService.undoMovement(gameId);

        // then
        Game game = janggiService.findGame(gameId);
        assertThat(game.pieces())
                .containsEntry(Position.from(1, 1), new Piece(Dynasty.CHO, PieceType.CHARIOT))
                .containsEntry(Position.from(1, 2), new Piece(Dynasty.HAN, PieceType.SOLDIER));
    }

    private Map<Dynasty, HorseElephantPosition> defaultHorseElephantPositions() {
        Map<Dynasty, HorseElephantPosition> positions = new EnumMap<>(Dynasty.class);
        positions.put(Dynasty.CHO, HorseElephantPosition.HEEH);
        positions.put(Dynasty.HAN, HorseElephantPosition.HEEH);
        return positions;
    }

}
