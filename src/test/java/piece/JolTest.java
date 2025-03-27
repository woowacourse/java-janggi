package piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static pieceProperty.PieceType.JOL;

import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import pieceProperty.Position;
import pieceProperty.Positions;

class JolTest {

    @DisplayName("졸병은 이름과 위치 정보를 가진다,")
    @Test
    void jolByeongBoardPosition() {
        //given
        Position position = new Position(4, 5);

        //when
        Jol jol = new Jol(position);

        //then
        assertThat(jol.currentPosition()).isEqualTo(new Position(4, 5));
    }

    @DisplayName("자신의 위치를 기준으로 이동할 수 없다면 false를 반환한다.")
    @ParameterizedTest
    @MethodSource("jolNonCanMoveToPositionProvider")
    void nonCanMoveTo(Position position) {
        //given
        Jol jol = new Jol(new Position(5, 5));

        //when //then
        assertThatThrownBy(() -> jol.canMoveTo(position))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageStartingWith("[ERROR]");
    }

    @DisplayName("졸은 자신의 위치에서 목적지까지의 경로를 계산하여 반환한다.")
    @Test
    void makeRoute() {
        //given
        Jol jol = new Jol(new Position(5, 5));
        Position futurePosition = new Position(4, 5);

        //when
        Positions actual = jol.makeRoute(futurePosition);

        //then
        assertThat(actual.getPositions().contains(futurePosition)).isFalse();
    }

    private static Stream<Arguments> jolNonCanMoveToPositionProvider() {
        return Stream.of(
                Arguments.of(new Position(6, 5)),
                Arguments.of(new Position(6, 3)),
                Arguments.of(new Position(6, 6)));
    }

    @Test
    @DisplayName("자신의 타입 리턴 테스트")
    void pieceTypeTest() {
        Jol jol = new Jol(new Position(5, 5));

        assertThat(jol.getPieceType().equals(JOL)).isTrue();
    }

    @Test
    @DisplayName("왕인지 물어보는 테스트")
    void isJanggunTest() {
        Jol jol = new Jol(new Position(5, 5));

        assertThat(jol.getPieceType().isJanggun()).isFalse();
    }

    @Test
    @DisplayName("포 판별 테스트")
    void isPoTest() {
        Jol jol = new Jol(new Position(5, 5));

        assertThat(jol.getPieceType().isPo()).isFalse();
    }
}
