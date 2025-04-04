package janggi.domain.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import janggi.domain.piece.Cha;
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

class ChaTest {

    static final JanggiPosition STANDARD = new JanggiPosition(4, 4);

    @DisplayName("장기말을 이동시킬 수 있다.")
    @ParameterizedTest
    @MethodSource()
    void test1(JanggiPosition destination) {
        //given
        Cha cha = Cha.from(STANDARD);

        //when
        Cha movedCha = cha.move(destination, new Pieces(List.of()), new Pieces(List.of()));

        //then
        assertThat(movedCha.getPosition()).isEqualTo(destination);
    }

    static Stream<Arguments> test1() {
        return Stream.of(
                Arguments.of(new JanggiPosition(STANDARD.x() + 1, STANDARD.y())),
                Arguments.of(new JanggiPosition(STANDARD.x() - 1, STANDARD.y())),
                Arguments.of(new JanggiPosition(STANDARD.x(), STANDARD.y() + 1)),
                Arguments.of(new JanggiPosition(STANDARD.x(), STANDARD.y() - 1)),
                Arguments.of(new JanggiPosition(8, STANDARD.y())),
                Arguments.of(new JanggiPosition(0, STANDARD.y())),
                Arguments.of(new JanggiPosition(STANDARD.x(), 0)),
                Arguments.of(new JanggiPosition(STANDARD.x(), 9))
        );
    }


    @DisplayName("장기말의 이동 규칙에 어긋난 경우 이동이 불가능합니다.")
    @ParameterizedTest
    @MethodSource()
    void test2(JanggiPosition destination) {
        //given
        Cha cha = Cha.from(STANDARD);

        //when & then
        assertThatThrownBy(() -> cha.move(destination, new Pieces(List.of()), new Pieces(List.of())))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 이동이 불가능합니다.");
    }

    static Stream<Arguments> test2() {
        return Stream.of(
                Arguments.of(new JanggiPosition(STANDARD.x() + 1, STANDARD.y() + 1)),
                Arguments.of(new JanggiPosition(STANDARD.x() + 1, STANDARD.y() - 1)),
                Arguments.of(new JanggiPosition(STANDARD.x() - 1, STANDARD.y() + 1)),
                Arguments.of(new JanggiPosition(STANDARD.x() - 1, STANDARD.y() - 1))
        );
    }

    @DisplayName("아군 장기말이 장애물일 경우 장애물 이전 위치까지 이동이 가능하다.")
    @Test
    void test4() {
        //given
        Cha cha = Cha.from(STANDARD);
        JanggiPosition janggiPositionBeforeHurdle = new JanggiPosition(5, 4);
        JanggiPosition destination = new JanggiPosition(6, 4);
        Cha otherPiece = Cha.from(destination);

        //when & then
        Cha movedCha = cha.move(janggiPositionBeforeHurdle, new Pieces(List.of()), new Pieces(List.of(otherPiece)));
        assertThat(movedCha.getPosition()).isEqualTo(janggiPositionBeforeHurdle);
    }

    @DisplayName("아군 장기말이 장애물일 경우 장애물 위치를 포함해 너머로 이동이 불가능하다.")
    @Test
    void test5() {
        //given
        Cha cha = Cha.from(STANDARD);
        JanggiPosition destination = new JanggiPosition(6, 4);
        Cha otherPiece = Cha.from(destination);

        //when & then
        assertAll(
                () -> assertThatThrownBy(() -> cha.move(new JanggiPosition(6, 4), new Pieces(List.of()), new Pieces(List.of(otherPiece))))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessage("[ERROR] 이동이 불가능합니다."),
                () -> assertThatThrownBy(() -> cha.move(new JanggiPosition(7, 4), new Pieces(List.of()), new Pieces(List.of(otherPiece))))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessage("[ERROR] 이동이 불가능합니다.")
        );
    }

    @DisplayName("상대 장기말이 장애물일 경우 장애물 위치를 포함해 너머로 이동이 불가능하다.")
    @Test
    void test6() {
        //given
        Cha cha = Cha.from(STANDARD);
        JanggiPosition destination = new JanggiPosition(6, 4);
        JanggiPosition hurdleJanggiPosition = new JanggiPosition(5, 4);
        Cha otherPiece = Cha.from(hurdleJanggiPosition);

        //when & then
        assertThatThrownBy(() -> cha.move(destination, new Pieces(List.of(otherPiece)), new Pieces(List.of())))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 이동이 불가능합니다.");
    }

    @DisplayName("상대 장기말이 장애물일 경우 장애물 위치까지 이동이 가능하다.")
    @Test
    void test7() {
        //given
        Cha cha = Cha.from(STANDARD);
        JanggiPosition janggiPositionBeforeHurdle = new JanggiPosition(5, 4);
        JanggiPosition destination = new JanggiPosition(6, 4);
        Cha otherPiece = Cha.from(destination);

        //when & then
        Cha movedCha = cha.move(destination, new Pieces(List.of(otherPiece)), new Pieces(List.of()));
        Assertions.assertThat(movedCha.getPosition()).isEqualTo(destination);
    }

    @DisplayName("아군 장기말이 장애물일 경우 장애물 위치를 포함해 너머로 이동이 불가능하다.")
    @Test
    void test8() {
        //given
        Cha cha = Cha.from(STANDARD);
        JanggiPosition destination = new JanggiPosition(6, 4);
        Cha otherPiece = Cha.from(destination);

        //when & then
        assertAll(
                () -> assertThatThrownBy(() -> cha.move(new JanggiPosition(7, 4), new Pieces(List.of()), new Pieces(List.of(otherPiece))))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessage("[ERROR] 이동이 불가능합니다."),
                () -> assertThatThrownBy(() -> cha.move(new JanggiPosition(8, 4), new Pieces(List.of()), new Pieces(List.of(otherPiece))))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessage("[ERROR] 이동이 불가능합니다.")
        );
    }

    @DisplayName("차가 궁성내에 있을 때 이동")
    @ParameterizedTest
    @MethodSource()
    void test9(JanggiPosition currentPosition, JanggiPosition  destination) {
        //given
        Cha cha = Cha.from(currentPosition);

        //when
        boolean isMove = cha.ableToMove(destination, new Pieces(List.of()), new Pieces(List.of()));

        //then
        Assertions.assertThat(isMove).isTrue();
    }

    static Stream<Arguments> test9() {
        return Stream.of(
                Arguments.of(new JanggiPosition(3,2), new JanggiPosition(3,1)),
                Arguments.of(new JanggiPosition(3,2), new JanggiPosition(3,0)),
                Arguments.of(new JanggiPosition(3,2), new JanggiPosition(4,1)),
                Arguments.of(new JanggiPosition(3,2), new JanggiPosition(5,0)),
                Arguments.of(new JanggiPosition(3,2), new JanggiPosition(4,2)),
                Arguments.of(new JanggiPosition(3,2), new JanggiPosition(5,2)),
                Arguments.of(new JanggiPosition(5,2), new JanggiPosition(3,2)),
                Arguments.of(new JanggiPosition(5,2), new JanggiPosition(4,2)),
                Arguments.of(new JanggiPosition(5,2), new JanggiPosition(5,1)),
                Arguments.of(new JanggiPosition(5,2), new JanggiPosition(5,0)),
                Arguments.of(new JanggiPosition(5,2), new JanggiPosition(4,1)),
                Arguments.of(new JanggiPosition(5,2), new JanggiPosition(3,0)),
                Arguments.of(new JanggiPosition(4,1), new JanggiPosition(3,0)),
                Arguments.of(new JanggiPosition(4,1), new JanggiPosition(5,0)),
                Arguments.of(new JanggiPosition(4,1), new JanggiPosition(5,2)),
                Arguments.of(new JanggiPosition(4,1), new JanggiPosition(3,2)),
                Arguments.of(new JanggiPosition(3,0), new JanggiPosition(4,0)),
                Arguments.of(new JanggiPosition(3,0), new JanggiPosition(5,0)),
                Arguments.of(new JanggiPosition(3,0), new JanggiPosition(4,1)),
                Arguments.of(new JanggiPosition(3,0), new JanggiPosition(5,2)),
                Arguments.of(new JanggiPosition(3,0), new JanggiPosition(3,1)),
                Arguments.of(new JanggiPosition(3,0), new JanggiPosition(3,2)),
                Arguments.of(new JanggiPosition(5,0), new JanggiPosition(4,0)),
                Arguments.of(new JanggiPosition(5,0), new JanggiPosition(3,0)),
                Arguments.of(new JanggiPosition(5,0), new JanggiPosition(4,1)),
                Arguments.of(new JanggiPosition(5,0), new JanggiPosition(3,2)),
                Arguments.of(new JanggiPosition(5,0), new JanggiPosition(5,1)),
                Arguments.of(new JanggiPosition(5,0), new JanggiPosition(5,2))
        );
    }

    @DisplayName("차가 궁성내에 있을 경우 못 움직이는 경우 확인")
    @ParameterizedTest
    @MethodSource()
    void test10(JanggiPosition currentPosition, JanggiPosition  destination) {
        //given
        Cha cha = Cha.from(currentPosition);

        //when
        boolean isMoved = cha.ableToMove(destination, new Pieces(List.of()), new Pieces(List.of()));

        //then
        Assertions.assertThat(isMoved).isFalse();
    }

    static Stream<Arguments> test10() {
        return Stream.of(
                Arguments.of(new JanggiPosition(4,0), new JanggiPosition(3,1)),
                Arguments.of(new JanggiPosition(4,0), new JanggiPosition(5,1)),
                Arguments.of(new JanggiPosition(3,1), new JanggiPosition(4,0)),
                Arguments.of(new JanggiPosition(3,1), new JanggiPosition(4,2)),
                Arguments.of(new JanggiPosition(5,1), new JanggiPosition(4,0)),
                Arguments.of(new JanggiPosition(5,1), new JanggiPosition(4,2)),
                Arguments.of(new JanggiPosition(4,2), new JanggiPosition(3,1)),
                Arguments.of(new JanggiPosition(4,2), new JanggiPosition(5,1))

        );
    }
}