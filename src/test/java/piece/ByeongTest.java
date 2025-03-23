package piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static pieceProperty.PieceType.BYEONG;

import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import pieceProperty.Position;
import pieceProperty.Positions;

class ByeongTest {
    @DisplayName("볃은 이름과 위치를 가진다.")
    @Test
    void byenogBoardPosition() {
        //given
        Position position = new Position(0, 0);

        //when
        Byeong byeong = new Byeong(position);

        //then
        assertThat(byeong.getBoardPosition().getCol()).isEqualTo(0);
        assertThat(byeong.getBoardPosition().getRow()).isEqualTo(0);
    }

    @DisplayName("자신의 위치를 기준으로 이동할 수 없다면 예외를 던진다.")
    @ParameterizedTest
    @MethodSource("byeongNonCanMoveToPositionProvider")
    void nonCanMoveTo(Position position) {
        //given
        Byeong byeong = new Byeong(new Position(5, 5));

        //when
        assertThatThrownBy(() -> byeong.canMoveTo(position))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageStartingWith("[ERROR]");
    }

    @DisplayName("자신의 위치를 기준으로 뒤를 제외한 가로,세로 한칸 이동을 할 수 있다면 true를 반환한다.")
    @ParameterizedTest
    @MethodSource("byeongCanMoveToPositionProvider")
    void canMoveTo(Position position) {
        //given
        Byeong byeong = new Byeong(new Position(5, 5));

        //when
        boolean actual = byeong.canMoveTo(position);

        //then
        assertThat(actual).isTrue();
    }

    @DisplayName("병은 자신의 위치에서 목적지까지의 경로를 계산하여 반환한다.")
    @Test
    void makeRoute() {
        //given
        Byeong byeong = new Byeong(new Position(5, 5));
        Position futurePosition = new Position(4, 5);

        //when
        Positions actual = byeong.makeRoute(futurePosition);

        //then
        assertThat(actual.getPositions().contains(futurePosition)).isTrue();
    }

    private static Stream<Arguments> byeongNonCanMoveToPositionProvider() {
        return Stream.of(
                Arguments.of(new Position(4, 5)),
                Arguments.of(new Position(6, 3)),
                Arguments.of(new Position(6, 6))
        );
    }

    private static Stream<Arguments> byeongCanMoveToPositionProvider() {
        return Stream.of(
                Arguments.of(new Position(6, 5)),
                Arguments.of(new Position(5, 6)),
                Arguments.of(new Position(5, 4))
        );
    }

    @Test
    @DisplayName("자신의 타입 리턴 테스트")
    void pieceTypeTest() {
        Byeong byeong = new Byeong(new Position(5, 5));

        assertThat(byeong.getPieceType().equals(BYEONG)).isTrue();
    }

    @Test
    @DisplayName("왕인지 물어보는 테스트")
    void isKingTest() {
        Byeong byeong = new Byeong(new Position(5, 5));

        assertThat(byeong.isKing()).isFalse();
    }
}
