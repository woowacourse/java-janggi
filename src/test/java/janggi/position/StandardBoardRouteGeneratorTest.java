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
}
