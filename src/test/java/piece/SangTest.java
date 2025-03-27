package piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static pieceProperty.PieceType.SANG;

import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import pieceProperty.Position;
import pieceProperty.Positions;

class SangTest {

    @DisplayName("상은 위치 정보를 가진다,")
    @Test
    void sangBoardPosition() {
        //given
        Position position = new Position(4, 5);

        //when
        Sang sang = new Sang(position);

        //then
        assertThat(sang.currentPosition()).isEqualTo(new Position(4, 5));
    }

    @DisplayName("자신의 위치를 기준으로 이동할 수 없다면 예외를 던진다.")
    @ParameterizedTest
    @MethodSource("sangNonCanMoveToPositionProvider")
    void nonCanMoveTo(Position position) {
        //given
        Sang sang = new Sang(new Position(5, 5));

        //when //then
        assertThatThrownBy(() -> sang.canMoveTo(position))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageStartingWith("[ERROR]");
    }

    @DisplayName("상은 자신의 위치에서 목적지까지의 경로를 계산하여 반환한다.")
    @Test
    void makeRoute() {
        //given
        Sang sang = new Sang(new Position(5, 5));
        Position futurePosition = new Position(3, 2);

        //when
        Positions actual = sang.makeRoute(futurePosition);

        //then
        assertThat(actual.getPositions()).containsExactly(
                new Position(5, 4),
                new Position(4, 3)
        );
    }

    private static Stream<Arguments> sangNonCanMoveToPositionProvider() {
        return Stream.of(
                Arguments.of(new Position(2, 5)),
                Arguments.of(new Position(2, 4)),
                Arguments.of(new Position(2, 6)),
                Arguments.of(new Position(4, 8)),
                Arguments.of(new Position(5, 8)),
                Arguments.of(new Position(6, 8)),
                Arguments.of(new Position(8, 4)),
                Arguments.of(new Position(8, 5)),
                Arguments.of(new Position(8, 6)),
                Arguments.of(new Position(4, 2)),
                Arguments.of(new Position(5, 2)),
                Arguments.of(new Position(6, 2))
        );
    }

    @Test
    @DisplayName("자신의 타입 리턴 테스트")
    void pieceTypeTest() {
        Sang sang = new Sang(new Position(5, 5));

        assertThat(sang.getPieceType().equals(SANG)).isTrue();
    }

    @Test
    @DisplayName("왕인지 물어보는 테스트")
    void isJanggunTest() {
        Sang sang = new Sang(new Position(5, 5));

        assertThat(sang.getPieceType().isJanggun()).isFalse();
    }

    @Test
    @DisplayName("포 판별 테스트")
    void isPoTest() {
        Sang sang = new Sang(new Position(5, 5));

        assertThat(sang.getPieceType().isPo()).isFalse();
    }

}

