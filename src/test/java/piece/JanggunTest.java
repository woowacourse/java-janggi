package piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static pieceProperty.PieceType.JANGGUN;

import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import pieceProperty.Position;
import pieceProperty.Positions;

class JanggunTest {

    @DisplayName("왕은 위치 정보를 가진다,")
    @Test
    void janggunBoardPosition() {
        //given
        Position position = new Position(4, 5);

        //when
        Janggun janggun = new Janggun(position);

        //then
        assertThat(janggun.currentPosition()).isEqualTo(new Position(4, 5));
    }

    @DisplayName("자신의 위치를 기준으로 이동할 수 없다면 false를 반환한다.")
    @ParameterizedTest
    @MethodSource("JanggunNonCanMoveToPositionProvider")
    void canMoveToValidate(Position position) {
        //given
        Janggun janggun = new Janggun(new Position(5, 5));

        //when //then
        assertThatThrownBy(() -> janggun.canMoveTo(position))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageStartingWith("[ERROR]");
    }

    @DisplayName("왕은 자신의 위치에서 목적지까지의 경로를 계산하여 반환한다.")
    @Test
    void makeRoute() {
        //given
        Janggun janggun = new Janggun(new Position(5, 5));
        Position futurePosition = new Position(4, 5);

        //when
        Positions actual = janggun.makeRoute(futurePosition);

        //then
        assertThat(actual.getPositions().contains(futurePosition)).isFalse();
    }

    private static Stream<Arguments> JanggunNonCanMoveToPositionProvider() {
        return Stream.of(
                Arguments.of(new Position(7, 5)),
                Arguments.of(new Position(3, 5)),
                Arguments.of(new Position(5, 7)),
                Arguments.of(new Position(5, 3)),
                Arguments.of(new Position(6, 6)),
                Arguments.of(new Position(4, 6)),
                Arguments.of(new Position(6, 4)),
                Arguments.of(new Position(4, 4))
        );
    }

    @Test
    @DisplayName("자신의 타입 리턴 테스트")
    void pieceTypeTest() {
        Janggun janggun = new Janggun(new Position(5, 5));

        assertThat(janggun.getPieceType().equals(JANGGUN)).isTrue();
    }

    @Test
    @DisplayName("왕인지 물어보는 테스트")
    void isJanggunTest() {
        Janggun janggun = new Janggun(new Position(5, 5));

        assertThat(janggun.getPieceType().isJanggun()).isTrue();
    }

    @Test
    @DisplayName("포 판별 테스트")
    void isPoTest() {
        Janggun janggun = new Janggun(new Position(5, 5));

        assertThat(janggun.getPieceType().isPo()).isFalse();
    }
}
