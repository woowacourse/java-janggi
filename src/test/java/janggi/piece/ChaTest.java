package janggi.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import janggi.value.Position;
import java.util.List;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class ChaTest {

    static final Position STANDARD = new Position(4, 4);

    @DisplayName("장기말을 이동시킬 수 있다.")
    @ParameterizedTest
    @MethodSource()
    void test1(Position destination) {
        //given
        Cha cha = Cha.from(STANDARD);

        //when
        Cha movedCha = cha.move(destination, List.of(), List.of());

        //then
        assertThat(movedCha.getPosition()).isEqualTo(destination);
    }

    static Stream<Arguments> test1() {
        return Stream.of(
                Arguments.of(new Position(STANDARD.getX() + 1, STANDARD.getY())),
                Arguments.of(new Position(STANDARD.getX() - 1, STANDARD.getY())),
                Arguments.of(new Position(STANDARD.getX(), STANDARD.getY() + 1)),
                Arguments.of(new Position(STANDARD.getX(), STANDARD.getY() - 1)),
                Arguments.of(new Position(8, STANDARD.getY())),
                Arguments.of(new Position(0, STANDARD.getY())),
                Arguments.of(new Position(STANDARD.getX(), 0)),
                Arguments.of(new Position(STANDARD.getX(), 9))
        );
    }


    @DisplayName("장기말의 이동 규칙에 어긋난 경우 이동이 불가능합니다.")
    @ParameterizedTest
    @MethodSource()
    void test2(Position destination) {
        //given
        Cha cha = Cha.from(STANDARD);

        //when & then
        assertThatThrownBy(() -> cha.move(destination, List.of(), List.of()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 이동이 불가능합니다.");
    }

    static Stream<Arguments> test2() {
        return Stream.of(
                Arguments.of(new Position(STANDARD.getX() + 1, STANDARD.getY() + 1)),
                Arguments.of(new Position(STANDARD.getX() + 1, STANDARD.getY() - 1)),
                Arguments.of(new Position(STANDARD.getX() - 1, STANDARD.getY() + 1)),
                Arguments.of(new Position(STANDARD.getX() - 1, STANDARD.getY() - 1))
        );
    }

    @DisplayName("아군 장기말이 장애물일 경우 장애물 이전 위치까지 이동이 가능하다.")
    @Test
    void test4() {
        //given
        Cha cha = Cha.from(STANDARD);
        Position positionBeforeHurdle = new Position(5, 4);
        Position destination = new Position(6, 4);
        Cha otherPiece = Cha.from(destination);

        //when & then
        Cha movedCha = cha.move(positionBeforeHurdle, List.of(), List.of(otherPiece));
        assertThat(movedCha.getPosition()).isEqualTo(positionBeforeHurdle);
    }

    @DisplayName("아군 장기말이 장애물일 경우 장애물 위치를 포함해 너머로 이동이 불가능하다.")
    @Test
    void test5() {
        //given
        Cha cha = Cha.from(STANDARD);
        Position destination = new Position(6, 4);
        Cha otherPiece = Cha.from(destination);

        //when & then
        assertAll(
                () -> assertThatThrownBy(() -> cha.move(new Position(6, 4), List.of(), List.of(otherPiece)))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessage("[ERROR] 이동이 불가능합니다."),
                () -> assertThatThrownBy(() -> cha.move(new Position(7, 4), List.of(), List.of(otherPiece)))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessage("[ERROR] 이동이 불가능합니다.")
        );
    }

    @DisplayName("상대 장기말이 장애물일 경우 장애물 위치를 포함해 너머로 이동이 불가능하다.")
    @Test
    void test6() {
        //given
        Cha cha = Cha.from(STANDARD);
        Position destination = new Position(6, 4);
        Position hurdlePosition = new Position(5, 4);
        Cha otherPiece = Cha.from(hurdlePosition);

        //when & then
        assertThatThrownBy(() -> cha.move(destination, List.of(otherPiece), List.of()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 이동이 불가능합니다.");
    }

    @DisplayName("상대 장기말이 장애물일 경우 장애물 위치까지 이동이 가능하다.")
    @Test
    void test7() {
        //given
        Cha cha = Cha.from(STANDARD);
        Position positionBeforeHurdle = new Position(5, 4);
        Position destination = new Position(6, 4);
        Cha otherPiece = Cha.from(destination);

        //when & then
        Cha movedCha = cha.move(destination, List.of(otherPiece), List.of());
        Assertions.assertThat(movedCha.getPosition()).isEqualTo(destination);
    }

    @DisplayName("아군 장기말이 장애물일 경우 장애물 위치를 포함해 너머로 이동이 불가능하다.")
    @Test
    void test8() {
        //given
        Cha cha = Cha.from(STANDARD);
        Position destination = new Position(6, 4);
        Cha otherPiece = Cha.from(destination);

        //when & then
        assertAll(
                () -> assertThatThrownBy(() -> cha.move(new Position(7, 4), List.of(), List.of(otherPiece)))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessage("[ERROR] 이동이 불가능합니다."),
                () -> assertThatThrownBy(() -> cha.move(new Position(8, 4), List.of(), List.of(otherPiece)))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessage("[ERROR] 이동이 불가능합니다.")
        );
    }
}