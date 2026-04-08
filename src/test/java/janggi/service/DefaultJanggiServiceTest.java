package janggi.service;

import janggi.domain.board.Location;
import janggi.domain.Side;
import janggi.domain.piece.Ma;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import janggi.domain.state.GameContext;
import janggi.domain.strategy.arrangement.ArrangementStrategy;
import janggi.domain.strategy.intersection.IntersectionInitializer;
import janggi.domain.strategy.arrangement.MaSangMaSang;
import janggi.domain.strategy.intersection.PalaceIntersectionInitializer;
import janggi.repository.dao.GameDao;
import janggi.repository.dao.PieceDao;
import janggi.repository.entity.GameEntity;
import janggi.repository.entity.PieceEntity;
import janggi.repository.util.TransactionManager;
import janggi.service.dto.GameInformation;
import janggi.support.TestArrangementStrategy;
import janggi.support.TestDBConnectionProvider;
import java.util.List;
import java.util.Map;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DefaultJanggiServiceTest {

    TransactionManager transactionManager;
    JanggiService janggiService;
    GameDao gameDao;
    PieceDao pieceDao;

    @BeforeEach
    void setup() {
        transactionManager = new TransactionManager(new TestDBConnectionProvider());

        gameDao = new GameDao(transactionManager);
        pieceDao = new PieceDao(transactionManager);
        janggiService = new DefaultJanggiService(transactionManager);

        transactionManager.begin();
    }

    @AfterEach
    void tearDown() {
        transactionManager.rollback();
        transactionManager.close();
    }

    @Test
    @DisplayName("진행중인 게임이 존재한다면 식별자를 반환한다.")
    void returnActiveGameIds() {
        // given
        Long active = gameDao.insert(new GameEntity("CHO", true));
        gameDao.insert(new GameEntity("HAN", false));

        // when
        List<Long> activeGameIds = janggiService.findActiveGameIds();

        // then
        Assertions.assertThat(activeGameIds).hasSize(1)
                .containsExactly(active);
    }

    @Test
    @DisplayName("식별자를 전달하면 게임 정보를 로딩한다.")
    void returnGameInformationByGameId() {
        // given
        Long gameId = gameDao.insert(new GameEntity("CHO", true));
        pieceDao.insert(new PieceEntity(gameId, "CHA", "CHO", 1, 1));
        pieceDao.insert(new PieceEntity(gameId, "MA", "CHO", 1, 2));
        pieceDao.insert(new PieceEntity(gameId, "GUNG", "CHO", 1, 3));
        IntersectionInitializer intersectionInitializer = new PalaceIntersectionInitializer();

        // when
        GameInformation gameInformation = janggiService.loadGameInformation(gameId, intersectionInitializer);

        // then
        List<List<Piece>> grid = gameInformation.board().to2DArray();

        Assertions.assertThat(grid.get(1).get(1).getType()).isEqualTo(PieceType.CHA);
        Assertions.assertThat(grid.get(1).get(2).getType()).isEqualTo(PieceType.MA);
        Assertions.assertThat(grid.get(1).get(3).getType()).isEqualTo(PieceType.GUNG);
    }

    @Test
    @DisplayName("새로운 게임을 생성한다.")
    void returnGameInformationByArrangementStrategy() {
        // given
        List<ArrangementStrategy> arrangementStrategies = List.of(
                new TestArrangementStrategy(Map.of(Location.of(1, 1), new Ma(Side.CHO)))
        );
        IntersectionInitializer intersectionInitializer = new PalaceIntersectionInitializer();

        // when
        GameInformation game = janggiService.createGame(arrangementStrategies, intersectionInitializer);

        // then
        Assertions.assertThat(game.gameId()).isNotNull();
        Assertions.assertThat(game.currentSide()).isEqualTo(Side.CHO);
        Assertions.assertThat(game.board().getAlivePieces()).hasSize(1);
    }

    @Test
    @DisplayName("기물을 이동하는 경우 데이터베이스 내 위치를 업데이트 한다.")
    void updatePieceLocation_WhenMove() {
        // given
        List<ArrangementStrategy> arrangementStrategies = List.of(
                new MaSangMaSang(Side.CHO),
                new MaSangMaSang(Side.HAN)
        );
        IntersectionInitializer intersectionInitializer = new PalaceIntersectionInitializer();
        GameInformation game = janggiService.createGame(arrangementStrategies, intersectionInitializer);
        GameContext context = GameContext.createInProgress(game.board().getAlivePieces(), game.currentSide());

        // when
        janggiService.movePiece(game, Location.of(9, 0), Location.of(8, 0), context);
        List<List<Piece>> updatedBoardGrid = janggiService.loadGameInformation(game.gameId(),
                new PalaceIntersectionInitializer()).board().to2DArray();

        // then
        Assertions.assertThat(updatedBoardGrid.get(8).get(0).getType()).isEqualTo(PieceType.CHA);
        Assertions.assertThat(updatedBoardGrid.get(9).get(0).isEmpty()).isTrue();
    }

    @Test
    @DisplayName("게임이 종료된 경우 게임 상태를 종료 상태로 변경한다.")
    void updateGameState_WhenGameEnd() {
        // given
        List<ArrangementStrategy> arrangementStrategies = List.of(
                new MaSangMaSang(Side.CHO),
                new MaSangMaSang(Side.HAN)
        );
        IntersectionInitializer intersectionInitializer = new PalaceIntersectionInitializer();
        GameInformation game = janggiService.createGame(arrangementStrategies, intersectionInitializer);

        // when
        janggiService.endGame(game.gameId());
        List<Long> activeGameIds = janggiService.findActiveGameIds();

        // then
        Assertions.assertThat(activeGameIds).isEmpty();
    }
}
