package janggi.service;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import janggi.domain.game.Game;
import janggi.domain.point.Point;
import janggi.domain.side.Side;
import janggi.repository.MoveRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MoveServiceTest {

    private MoveRepository moveRepositoryMock;
    private MoveService moveService;
    private Game gameMock;

    @BeforeEach
    void setUp() {
        moveRepositoryMock = mock(MoveRepository.class);
        moveService = new MoveService(moveRepositoryMock);
        gameMock = mock(Game.class);
    }

    @Test
    void 이동_저장_실패_시_재시도() {
        // Arrange
        Point from = new Point(0, 0);
        Point to = new Point(1, 1);

        when(gameMock.getId()).thenReturn(1);
        when(gameMock.getTurn()).thenReturn(Side.CHO);

        // 첫 2번 실패, 3번째 성공
        when(moveRepositoryMock.findNextMoveNumber(1))
                .thenThrow(new IllegalArgumentException("중복"))
                .thenThrow(new IllegalArgumentException("중복"))
                .thenReturn(1);

        // Act
        moveService.move(gameMock, from, to);

        // Assert
        verify(moveRepositoryMock, times(3)).findNextMoveNumber(1);
        verify(gameMock, times(1)).move(from, to);
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
