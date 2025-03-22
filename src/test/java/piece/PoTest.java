package piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class PoTest {

    @DisplayName("포는 위치 정보를 가진다,")
    @Test
    void poBoardPosition() {
        //given
        Position position = new Position(4, 5);

        //when
        Po po = new Po(new PieceProfile("포", Nation.HAN), position);

        //then
        assertThat(po.getBoardPosition()).isEqualTo(new Position(4, 5));
    }

    @DisplayName("자신의 위치를 기준으로 이동할 수 없다면 false를 반환한다.")
    @Test
    void nonIsMove() {
        //given
        Po po = new Po(new PieceProfile("포", Nation.HAN), new Position(0, 0));

        //when //then
        assertThatThrownBy(() -> po.isMove(new Position(1, 1)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageStartingWith("[ERROR]");
    }

    @DisplayName("포는 움직임을 자신의 위치를 기준으로 가로, 세로 방향으로 무제한 이동할 수 있다면 true를 반환한다.")
    @ParameterizedTest
    @MethodSource("poIsMovePositionProvider")
    void isMove(Position position) {
        //given
        Po po = new Po(new PieceProfile("포", Nation.HAN), new Position(0, 0));

        //when
        boolean actual = po.isMove(position);

        //then
        assertThat(actual).isTrue();
    }

    @DisplayName("포는 자신의 위치에서 목적지까지의 경로를 계산하여 반환한다.")
    @Test
    void makeRoute() {
        //given
        Po po = new Po(new PieceProfile("포", Nation.HAN), new Position(0, 0));
        Position futurePosition = new Position(5, 0);

        //when
        List<Position> actual = po.makeRoute(futurePosition);

        //then
        assertThat(actual).containsExactly(
                new Position(1, 0),
                new Position(2, 0),
                new Position(3, 0),
                new Position(4, 0),
                new Position(5, 0)
        );
    }

    private static Stream<Arguments> poIsMovePositionProvider() {
        return Stream.of(
                Arguments.of(new Position(0, 1)),
                Arguments.of(new Position(1, 0))
        );
    }

}
