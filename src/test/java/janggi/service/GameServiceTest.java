package janggi.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import janggi.dao.GameDao;
import janggi.dao.MoveDao;
import janggi.dao.entity.GameEntity;
import janggi.domain.game.Game;
import janggi.domain.game.Status;
import janggi.view.BoardSetUpFormat;
import java.util.ArrayList;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GameServiceTest {

    private GameDao gameDaoMock;
    private MoveDao moveDaoMock;
    private GameService gameService;

    @BeforeEach
    void setUp() {
        // Mock 객체 생성
        gameDaoMock = mock(GameDao.class);
        moveDaoMock = mock(MoveDao.class);
        gameService = new GameService(gameDaoMock, moveDaoMock);
    }

    @Test
    void 게임_생성_시_ID가_할당되어야_함() {
        // Arrange
        when(gameDaoMock.save(any())).thenReturn(1);

        // Act
        Game game = gameService.createGame("테스트 게임",
                BoardSetUpFormat.IN_ELEPHANT.getBoardSetUp(),
                BoardSetUpFormat.IN_ELEPHANT.getBoardSetUp());

        // Assert
        assertEquals(1, game.getId());
        verify(gameDaoMock, times(1)).save(any());
    }

    @Test
    void 게임_이름으로_조회_시_해당_게임을_반환() {
        // Arrange
        GameEntity mockGameEntity = new GameEntity(
                1, "테스트 게임",
                BoardSetUpFormat.IN_ELEPHANT,
                BoardSetUpFormat.IN_ELEPHANT,
                Status.IN_PROGRESS,
                null
        );

        when(gameDaoMock.findByName("테스트 게임"))
                .thenReturn(Optional.of(mockGameEntity));
        when(moveDaoMock.findByGameIdOrderByMoveNumber(1))
                .thenReturn(new ArrayList<>());

        // Act
        Game game = gameService.findByName("테스트 게임");

        // Assert
        assertEquals(1, game.getId());
    }

    @Test
    void 게임이_없으면_예외_발생() {
        // Arrange
        when(gameDaoMock.findByName("없는 게임"))
                .thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(IllegalArgumentException.class,
                () -> gameService.findByName("없는 게임"));
    }
}
