package janggi.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.setting.CampType;
import janggi.value.Position;
import java.util.List;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class JolTest {

    static final Position STANDARD = new Position(4, 4);

    @DisplayName("초의 장기말을 이동시킬 수 있다.")
    @ParameterizedTest
    @MethodSource()
    void test1(Position destination) {
        //given
        Jol jol = Jol.from(STANDARD, CampType.CHO);

        //when
        Jol movedJol = jol.move(destination, List.of(), List.of());

        //then
        assertThat(movedJol.getPosition()).isEqualTo(destination);
    }

    static Stream<Arguments> test1() {
        return Stream.of(
                Arguments.of(new Position(STANDARD.x() + 1, STANDARD.y())),
                Arguments.of(new Position(STANDARD.x() - 1, STANDARD.y())),
                Arguments.of(new Position(STANDARD.x(), STANDARD.y() - 1))
        );
    }

    @DisplayName("한의 장기말을 이동시킬 수 있다.")
    @ParameterizedTest
    @MethodSource()
    void test2(Position destination) {
        //given
        Jol jol = Jol.from(STANDARD, CampType.HAN);

        //when
        Jol movedJol = jol.move(destination, List.of(), List.of());

        //then
        assertThat(movedJol.getPosition()).isEqualTo(destination);
    }

    static Stream<Arguments> test2() {
        return Stream.of(
                Arguments.of(new Position(STANDARD.x() + 1, STANDARD.y())),
                Arguments.of(new Position(STANDARD.x() - 1, STANDARD.y())),
                Arguments.of(new Position(STANDARD.x(), STANDARD.y() + 1))
        );
    }


    @DisplayName("장기말의 이동 규칙에 어긋난 경우 이동이 불가능합니다.")
    @ParameterizedTest
    @MethodSource()
    void test3(Position destination) {
        //given
        Jol jol = Jol.from(STANDARD, CampType.CHO);

        //when & then
        assertThatThrownBy(() -> jol.move(destination, List.of(), List.of()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 이동이 불가능합니다.");
    }

    static Stream<Arguments> test3() {
        return Stream.of(
                Arguments.of(new Position(STANDARD.x() + 2, STANDARD.y())),
                Arguments.of(new Position(STANDARD.x() - 2, STANDARD.y())),
                Arguments.of(new Position(STANDARD.x(), STANDARD.y() - 2))
        );
    }

    @DisplayName("아군 장기말이 장애물일 경우 해당 위치로 이동이 불가능하다.")
    @Test
    void test4() {
        //given
        Jol jol = Jol.from(STANDARD, CampType.CHO);
        Position destination = new Position(4, 3);
        Jol otherPiece = Jol.from(destination, CampType.CHO);

        //when & then
        assertThatThrownBy(() -> jol.move(destination, List.of(), List.of(otherPiece)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 이동이 불가능합니다.");
    }

    @DisplayName("상대 장기말이 장애물일 경우 해당 위치로 이동이 가능하다.")
    @Test
    void test5() {
        //given
        Jol jol = Jol.from(STANDARD, CampType.CHO);
        Position destination = new Position(4, 3);
        Jol otherPiece = Jol.from(destination, CampType.CHO);

        //when
        Jol movedJol = jol.move(destination, List.of(otherPiece), List.of());

        //then
        Assertions.assertThat(movedJol.getPosition()).isEqualTo(destination);
    }
}