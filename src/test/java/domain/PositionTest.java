package domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.piece.Move;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class PositionTest {

    @DisplayName("장기판 범위를 벗어난 위치인 경우 예외를 발생시킨다")
    @ParameterizedTest
    @CsvSource({
            "0, 1",
            "1, 0",
            "0, 0",
            "1, 10",
            "11, 9",
            "11, 10"
    })
    void test1(int row, int column) {
        assertThatThrownBy(() -> new Position(row, column))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("장기판을 넘은 이동은 불가능 합니다.");
    }

    @DisplayName("위치를 이동할 수 있다")
    @Test
    void test2() {
        // given
        Position position = new Position(1, 1);

        // when
        Position movedPosition = position.movePosition(Move.RIGHT);

        // then
        Position expected = new Position(1, 2);
        Assertions.assertThat(movedPosition).isEqualTo(expected);
    }

    @DisplayName("해당 위치로 움직일 수 있는지 확인한다")
    @ParameterizedTest
    @CsvSource({
            "5,5,FRONT,true",
            "1,1,BACK,true",
            "1,1,FRONT,false",
            "10,1,BACK,false",
            "1,9,RIGHT,false",

    })
    void test3(int row, int column, Move move, boolean expected) {
        //given
        Position position = new Position(row, column);

        //when
        boolean canMove = position.canMovePosition(move);
        //then
        Assertions.assertThat(canMove).isEqualTo(expected);
    }

    @DisplayName("컬럼을 비교한다")
    @ParameterizedTest
    @CsvSource({
            "1,1,0",
            "5,1,0",
            "1,9,-8",
            "10,3,-2",
    })
    void test4(int row, int column, int expected) {
        // given
        Position position = new Position(row, column);
        Position comparedPosition = new Position(1, 1);

        // when
        int comparedValue = comparedPosition.compareColumn(position);

        // then
        assertThat(comparedValue).isEqualTo(expected);
    }

    @DisplayName("로우를 비교한다")
    @ParameterizedTest
    @CsvSource({
            "1,1,0",
            "5,1,-4",
            "2,9,-1",
            "10,3,-9",
    })
    void test5(int row, int column, int expected) {
        // given
        Position position = new Position(row, column);
        Position comparedPosition = new Position(1, 1);

        // when
        int comparedValue = comparedPosition.compareRow(position);

        // then
        assertThat(comparedValue).isEqualTo(expected);
    }
}