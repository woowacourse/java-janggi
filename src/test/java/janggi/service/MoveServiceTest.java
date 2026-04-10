package janggi.service;

import static org.mockito.Mockito.mock;

import janggi.dao.MoveDao;
import janggi.domain.game.Game;
import org.junit.jupiter.api.BeforeEach;

class MoveServiceTest {

    private MoveDao moveDaoMock;
    private MoveService moveService;
    private Game gameMock;

    @BeforeEach
    void setUp() {
        moveDaoMock = mock(MoveDao.class);
        moveService = new MoveService(moveDaoMock);
        gameMock = mock(Game.class);
    }

    //transaction 구현 안됐음
//    @Test
//    void 이동_저장_5회_실패_시_저장하지_않음() {
//        // Arrange
//        Point from = new Point(0, 0);
//        Point to = new Point(1, 1);
//
//        when(gameMock.getId()).thenReturn(1);
//        when(moveRepositoryMock.findNextMoveNumber(1))
//                .thenThrow(new IllegalArgumentException("중복"));
//
//        // Act
//        moveService.move(gameMock, from, to);
//
//        // Assert
//        verify(gameMock, never()).move(from, to);  // move 호출 안 됨
//    }
}
