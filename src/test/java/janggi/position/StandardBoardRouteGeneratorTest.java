package janggi.position;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class StandardBoardRouteGeneratorTest {

    @Test
    @DisplayName("장기판 각 위치의 이동 가능 경로를 생성하여 반환한다")
    void should_return_standard_board_route() {
        // given & when
        Map<Position, Directions> boardEdge = new StandardBoardRouteGenerator().generate();

        // then
        assertAll(
                // NOTE: 각 꼭지점은 2방향만 갈 수 있다.
                () -> assertThat(boardEdge.get(new Position(1,1)).getDirections()).hasSize(2),
                () -> assertThat(boardEdge.get(new Position(1,10)).getDirections()).hasSize(2),
                () -> assertThat(boardEdge.get(new Position(9,1)).getDirections()).hasSize(2),
                () -> assertThat(boardEdge.get(new Position(9,10)).getDirections()).hasSize(2),

                // NOTE: 각 변은 3방향만 갈 수 있다.
                () -> assertThat(boardEdge.get(new Position(1,5)).getDirections()).hasSize(3),
                () -> assertThat(boardEdge.get(new Position(9,5)).getDirections()).hasSize(3),
                () -> assertThat(boardEdge.get(new Position(5,1)).getDirections()).hasSize(3),
                () -> assertThat(boardEdge.get(new Position(5,10)).getDirections()).hasSize(3),

                // NOTE: 그 외 모든 위치는 4방향으로 갈 수 있다.
                () -> assertThat(boardEdge.get(new Position(5,5)).getDirections()).hasSize(4)
        );
    }

    @Test
    @DisplayName("장기판 왕궁 내 대각선 이동 가능 경로가 잘 생성됐는지 확인한다")
    void check_palace_directions_of_created_board_route() {
        // given & when
        Map<Position, Directions> boardEdge = new StandardBoardRouteGenerator().generate();

        // then
        assertAll(
                // NOTE: RED의 왕궁 내 이동 경로가 잘 생성되었는지 확인한다.
                () -> assertThat(boardEdge.get(new Position(5,2)).getDirections()).hasSize(8),
                () -> assertThat(boardEdge.get(new Position(4,1)).getDirections()).hasSize(4),
                () -> assertThat(boardEdge.get(new Position(6,1)).getDirections()).hasSize(4),
                () -> assertThat(boardEdge.get(new Position(4,3)).getDirections()).hasSize(5),
                () -> assertThat(boardEdge.get(new Position(6,3)).getDirections()).hasSize(5),

                // NOTE: BLUE의 왕궁 내 이동 경로가 잘 생성되었는지 확인한다.
                () -> assertThat(boardEdge.get(new Position(5,9)).getDirections()).hasSize(8),
                () -> assertThat(boardEdge.get(new Position(4,8)).getDirections()).hasSize(5),
                () -> assertThat(boardEdge.get(new Position(6,8)).getDirections()).hasSize(5),
                () -> assertThat(boardEdge.get(new Position(4,10)).getDirections()).hasSize(4),
                () -> assertThat(boardEdge.get(new Position(6,10)).getDirections()).hasSize(4)
        );
    }
}
