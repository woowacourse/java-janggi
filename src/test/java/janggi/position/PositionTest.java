package janggi.position;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class PositionTest {
    @Test
    @DisplayName("row,column에 따른 position 생성 확인")
    void positionCreateTest() {
        //given
        Position position = new Position(2, 3);
        //when
        int expectedX = position.row();
        int expectedY = position.column();
        //then
        assertAll(
                () -> assertThat(expectedX).isEqualTo(2),
                () -> assertThat(expectedY).isEqualTo(3)
        );
    }

    @ParameterizedTest
    @MethodSource("makeOutOfBoardTrueTestData")
    @DisplayName("Position이 board 이용 가능한 위치가 아니면 True")
    void outOfBoardTrueTest(Position position) {
        Assertions.assertThat(position.isOutOfBoards()).isTrue();
    }

    static Stream<Arguments> makeOutOfBoardTrueTestData() {
        return Stream.of(
        Arguments.arguments(
        new Position(11, 10)
                ),
        Arguments.arguments(
        new Position(0, 0)
                )
        );
    }

    @ParameterizedTest
    @MethodSource("makeOutOfBoardFalseTestData")
    @DisplayName("Position이 board 이용 가능한 위치에 있으면 False")
    void outOfBoardFalseTest(Position position) {
        Assertions.assertThat(position.isOutOfBoards()).isFalse();
    }

    static Stream<Arguments> makeOutOfBoardFalseTestData() {
        return Stream.of(
                Arguments.arguments(
                        new Position(10, 9)
                ),
                Arguments.arguments(
                        new Position(1, 1)
                )
        );
    }

    @ParameterizedTest
    @MethodSource("makeOutOfBoundsTestData")
    @DisplayName("영역 밖의 값을 가지면 True")
    void outOfBoundsTrueTest(Position position) {
        Assertions.assertThat(position.isOutOfBoards()).isTrue();
    }

    static Stream<Arguments> makeOutOfBoundsTestData() {
        return Stream.of(
                Arguments.arguments(
                        new Position(0,1)
                ),
                Arguments.arguments(
                        new Position(1,0)
                ),
                Arguments.arguments(
                        new Position(10,10)
                ),
                Arguments.arguments(
                        new Position(11,9)
                )
        );
    }
}
