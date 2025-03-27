package piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static pieceProperty.PieceType.SA;

import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import pieceProperty.Position;
import pieceProperty.Positions;

class SaTest {

    @DisplayName("사는 위치 정보를 가진다,")
    @Test
    void saBoardPosition() {
        //given
        Position position = new Position(4, 5);

        //when
        Sa sa = new Sa(position);

        //then
        assertThat(sa.currentPosition()).isEqualTo(new Position(4, 5));
    }

    @DisplayName("자신의 위치를 기준으로 이동할 수 없다면 false를 반환한다.")
    @ParameterizedTest
    @MethodSource("saNonCanMoveToPositionProvider")
    void canMoveToValidate(Position position) {
        //given
        Sa sa = new Sa(new Position(5, 5));

        //when //then
        assertThatThrownBy(() -> sa.canMoveTo(position))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageStartingWith("[ERROR]");
    }

    @DisplayName("사는 자신의 위치에서 목적지까지의 경로를 계산하여 반환한다.")
    @Test
    void makeRoute() {
        //given
        Sa sa = new Sa(new Position(5, 5));
        Position futurePosition = new Position(4, 5);

        //when
        Positions actual = sa.makeRoute(futurePosition);

        //then
        assertThat(actual.getPositions().contains(futurePosition)).isFalse();
    }

    private static Stream<Arguments> saNonCanMoveToPositionProvider() {
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
        Sa sa = new Sa(new Position(5, 5));

        assertThat(sa.getPieceType().equals(SA)).isTrue();
    }

    @Test
    @DisplayName("왕인지 물어보는 테스트")
    void isJanggunTest() {
        Sa sa  = new Sa(new Position(5, 5));

        assertThat(sa.getPieceType().isJanggun()).isFalse();
    }

    @Test
    @DisplayName("포 판별 테스트")
    void isPoTest() {
        Sa sa = new Sa(new Position(5, 5));

        assertThat(sa.getPieceType().isPo()).isFalse();
    }
}
