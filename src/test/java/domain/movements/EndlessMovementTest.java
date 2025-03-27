package domain.movements;

import domain.board.BoardPoint;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class EndlessMovementTest {
    @Test
    @DisplayName("도착할 수 있는 지점들을 모두 반환한다")
    void test_calculateTotalArrivalPoints() {
        // given
        EndlessMovement endlessMovement = new EndlessMovement();

        // when
        List<BoardPoint> boardPoints = endlessMovement.calculateTotalArrivalPoints(new BoardPoint(3, 3));

        // then
        assertThat(boardPoints).containsExactlyInAnyOrder(
                new BoardPoint(5, 3),
                new BoardPoint(6, 3),
                new BoardPoint(7, 3),
                new BoardPoint(8, 3),
                new BoardPoint(9, 3),
                new BoardPoint(3, 4),
                new BoardPoint(3, 5),
                new BoardPoint(3, 6),
                new BoardPoint(3, 7),
                new BoardPoint(3, 8),
                new BoardPoint(0, 3),
                new BoardPoint(3, 2),
                new BoardPoint(3, 1),
                new BoardPoint(3, 0),
                new BoardPoint(4, 3),
                new BoardPoint(2, 3),
                new BoardPoint(1, 3)
        );
    }

    @Test
    @DisplayName("경로 상의 모든 지점들을 반환한다")
    void test_calculateRoutePoints() {
        // given
        EndlessMovement endlessMovement = new EndlessMovement();

        // when
        List<BoardPoint> boardPoints = endlessMovement.calculateRoutePoints(new BoardPoint(3, 3), new BoardPoint(3, 6));

        // then
        assertThat(boardPoints).containsExactlyInAnyOrder(
                new BoardPoint(3, 4),
                new BoardPoint(3, 5),
                new BoardPoint(3, 6)
        );
    }

    @Test
    @DisplayName("이동할 수 없는 위치로 경로를 반환할 것을 요청하는 경우 예외를 던진다")
    void test_calculateRoutePointsThrowsException() {
        // given
        EndlessMovement endlessMovement = new EndlessMovement();

        BoardPoint startBoardPoint = new BoardPoint(0, 0);
        BoardPoint invalidArrivalBoardPoint = new BoardPoint(1, 1);

        // when & then
        assertThatThrownBy(() -> endlessMovement.calculateRoutePoints(startBoardPoint, invalidArrivalBoardPoint))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("해당 위치로 이동할 수 없습니다.");
    }
}
