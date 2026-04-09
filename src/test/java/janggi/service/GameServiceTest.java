package janggi.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import janggi.domain.game.Game;
import janggi.entity.GameEntity;
import janggi.entity.SetUpEntity;
import janggi.entity.Status;
import janggi.repository.GameRepository;
import janggi.repository.MoveRepository;
import janggi.view.BoardSetUpFormat;
import java.util.ArrayList;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GameServiceTest {

    private GameRepository gameRepositoryMock;
    private MoveRepository moveRepositoryMock;
    private GameService gameService;

    @BeforeEach
    void setUp() {
        // Mock 객체 생성
        gameRepositoryMock = mock(GameRepository.class);
        moveRepositoryMock = mock(MoveRepository.class);
        gameService = new GameService(gameRepositoryMock, moveRepositoryMock);
    }

    @Test
    void 게임_생성_시_ID가_할당되어야_함() {
        // Arrange
        when(gameRepositoryMock.save(any())).thenReturn(1);

        // Act
        Game game = gameService.createGame("테스트 게임",
                SetUpEntity.IN_ELEPHANT.getBoardSetUp(),
                SetUpEntity.IN_ELEPHANT.getBoardSetUp());

        // Assert
        assertEquals(1, game.getId());
        verify(gameRepositoryMock, times(1)).save(any());
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

        when(gameRepositoryMock.findByName("테스트 게임"))
                .thenReturn(Optional.of(mockGameEntity));
        when(moveRepositoryMock.findByGameIdOrderByMoveNumber(1))
                .thenReturn(new ArrayList<>());

        // Act
        Game game = gameService.findByName("테스트 게임");

        // Assert
        assertEquals(1, game.getId());
    }

    @Test
    void 게임이_없으면_예외_발생() {
        // Arrange
        when(gameRepositoryMock.findByName("없는 게임"))
                .thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(IllegalArgumentException.class,
                () -> gameService.findByName("없는 게임"));
    }
}
