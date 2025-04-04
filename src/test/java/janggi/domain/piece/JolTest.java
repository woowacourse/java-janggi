package janggi.domain.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.domain.piece.Jol;
import janggi.domain.piece.Pieces;
import janggi.domain.value.JanggiPosition;
import java.util.List;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class JolTest {

    static final JanggiPosition STANDARD = new JanggiPosition(4, 4);

    @DisplayName("초의 장기말을 이동시킬 수 있다.")
    @ParameterizedTest
    @MethodSource()
    void test1(JanggiPosition destination) {
        //given
        Jol jol = Jol.from(STANDARD);

        //when
        Jol movedJol = jol.move(destination, new Pieces(List.of()), new Pieces(List.of()));

        //then
        assertThat(movedJol.getPosition()).isEqualTo(destination);
    }

    static Stream<Arguments> test1() {
        return Stream.of(
                Arguments.of(new JanggiPosition(STANDARD.x() + 1, STANDARD.y())),
                Arguments.of(new JanggiPosition(STANDARD.x() - 1, STANDARD.y())),
                Arguments.of(new JanggiPosition(STANDARD.x(), STANDARD.y() - 1))
        );
    }

    @DisplayName("장기말의 이동 규칙에 어긋난 경우 이동이 불가능합니다.")
    @ParameterizedTest
    @MethodSource()
    void test3(JanggiPosition destination) {
        //given
        Jol jol = Jol.from(STANDARD);

        //when & then
        assertThatThrownBy(() -> jol.move(destination, new Pieces(List.of()), new Pieces(List.of())))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 이동이 불가능합니다.");
    }

    static Stream<Arguments> test3() {
        return Stream.of(
                Arguments.of(new JanggiPosition(STANDARD.x() + 2, STANDARD.y())),
                Arguments.of(new JanggiPosition(STANDARD.x() - 2, STANDARD.y())),
                Arguments.of(new JanggiPosition(STANDARD.x(), STANDARD.y() - 2))
        );
    }

    @DisplayName("아군 장기말이 장애물일 경우 해당 위치로 이동이 불가능하다.")
    @Test
    void test4() {
        //given
        Jol jol = Jol.from(STANDARD);
        JanggiPosition destination = new JanggiPosition(4, 3);
        Jol otherPiece = Jol.from(destination);

        //when & then
        assertThatThrownBy(() -> jol.move(destination, new Pieces(List.of()), new Pieces(List.of(otherPiece))))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 이동이 불가능합니다.");
    }

    @DisplayName("상대 장기말이 장애물일 경우 해당 위치로 이동이 가능하다.")
    @Test
    void test5() {
        //given
        Jol jol = Jol.from(STANDARD);
        JanggiPosition destination = new JanggiPosition(4, 3);
        Jol otherPiece = Jol.from(destination);

        //when
        Jol movedJol = jol.move(destination, new Pieces(List.of(otherPiece)), new Pieces(List.of()));

        //then
        Assertions.assertThat(movedJol.getPosition()).isEqualTo(destination);
    }

    @DisplayName("적 장기말이 목적지에 있을 때, 잡아서 없애버린다.")
    @Test
    void test6() {
        //given
        Jol jol = Jol.from(STANDARD);
        JanggiPosition destination = new JanggiPosition(4,3);
        Jol enemyJol = Jol.from(destination);
        Pieces enemyPieces = new Pieces(List.of(enemyJol));

        //when
        Jol movedJol = jol.move(destination, enemyPieces, new Pieces(List.of()));

        //then
        Assertions.assertThat(enemyPieces.isNotBlockedBy(destination)).isTrue();
    }

    @DisplayName("졸이 궁성내에 있을 때 이동")
    @ParameterizedTest
    @MethodSource()
    void test7(JanggiPosition currentPosition, JanggiPosition destination) {
        //given
        Jol jol = Jol.from(currentPosition);

        //when
        boolean isMove = jol.ableToMove(destination, new Pieces(List.of()), new Pieces(List.of()));

        //then
        Assertions.assertThat(isMove).isTrue();
    }

    static Stream<Arguments> test7() {
        return Stream.of(
                Arguments.of(new JanggiPosition(3,2), new JanggiPosition(4,2)),
                Arguments.of(new JanggiPosition(3,2), new JanggiPosition(3,1)),
                Arguments.of(new JanggiPosition(3,2), new JanggiPosition(4,1)),
                Arguments.of(new JanggiPosition(5,2), new JanggiPosition(4,2)),
                Arguments.of(new JanggiPosition(5,2), new JanggiPosition(5,1)),
                Arguments.of(new JanggiPosition(5,2), new JanggiPosition(4,1)),
                Arguments.of(new JanggiPosition(4,1), new JanggiPosition(3,1)),
                Arguments.of(new JanggiPosition(4,1), new JanggiPosition(5,1)),
                Arguments.of(new JanggiPosition(4,1), new JanggiPosition(4,0)),
                Arguments.of(new JanggiPosition(4,1), new JanggiPosition(5,0)),
                Arguments.of(new JanggiPosition(4,1), new JanggiPosition(3,0)),
                Arguments.of(new JanggiPosition(3,0), new JanggiPosition(4,0)),
                Arguments.of(new JanggiPosition(5,0), new JanggiPosition(4,0))
        );
    }

    @DisplayName("졸이 궁성내에 있을 때 이동불가능한 경우")
    @ParameterizedTest
    @MethodSource()
    void test8(JanggiPosition currentPosition, JanggiPosition destination) {
        //given
        Jol jol = Jol.from(currentPosition);

        //when & then
        assertThatThrownBy(
                () -> jol.ableToMove(destination, new Pieces(List.of()), new Pieces(List.of()))
        ).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 이동 가능한 방향이 없습니다.");
    }

    static Stream<Arguments> test8() {
        return Stream.of(
                Arguments.of(new JanggiPosition(4,2), new JanggiPosition(3,1)),
                Arguments.of(new JanggiPosition(4,2), new JanggiPosition(5,1)),
                Arguments.of(new JanggiPosition(3,1), new JanggiPosition(4,0)),
                Arguments.of(new JanggiPosition(3,1), new JanggiPosition(4,2)),
                Arguments.of(new JanggiPosition(5,1), new JanggiPosition(4,0)),
                Arguments.of(new JanggiPosition(5,1), new JanggiPosition(4,2)),
                Arguments.of(new JanggiPosition(4,0), new JanggiPosition(3,1)),
                Arguments.of(new JanggiPosition(4,0), new JanggiPosition(5,1))
        );
    }
}