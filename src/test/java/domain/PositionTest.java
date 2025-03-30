package domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.piece.Position;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class PositionTest {

    @ParameterizedTest
    @CsvSource({
            "0, 1",
            "1, 0",
            "0, 0",
            "1, 10",
            "11, 9",
            "11, 10"
    })
    void 장기판_범위를_벗어난_위치인_경우_예외를_발생시킨다(int row, int column) {
        assertThatThrownBy(() -> new Position(row, column))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("장기판을 넘은 이동은 불가능 합니다.");
    }

    @Test
    void 위치를_오른쪽으로_이동할_수_있다() {
        // given
        Position position = new Position(4, 4);

        // when
        Position movedPosition = position.movePosition(Move.RIGHT);

        // then
        Position expected = new Position(4, 5);
        Assertions.assertThat(movedPosition).isEqualTo(expected);
    }

    @Test
    void 위치를_뒤로_이동할_수_있다() {
        // given
        Position position = new Position(4, 4);

        // when
        Position movedPosition = position.movePosition(Move.BACK);

        // then
        Position expected = new Position(5, 4);
        Assertions.assertThat(movedPosition).isEqualTo(expected);
    }

    @Test
    void 위치를_왼쪽으로_이동할_수_있다() {
        // given
        Position position = new Position(4, 4);

        // when
        Position movedPosition = position.movePosition(Move.LEFT);

        // then
        Position expected = new Position(4, 3);
        Assertions.assertThat(movedPosition).isEqualTo(expected);
    }

    @Test
    void 위치를_앞으로_이동할_수_있다() {
        // given
        Position position = new Position(4, 4);

        // when
        Position movedPosition = position.movePosition(Move.FRONT);

        // then
        Position expected = new Position(3, 4);
        Assertions.assertThat(movedPosition).isEqualTo(expected);
    }

    @Test
    void 위치를_왼쪽앞으로_이동할_수_있다() {
        // given
        Position position = new Position(4, 4);

        // when
        Position movedPosition = position.movePosition(Move.FRONT_LEFT);

        // then
        Position expected = new Position(3, 3);
        Assertions.assertThat(movedPosition).isEqualTo(expected);
    }

    @Test
    void 위치를_오른쪽앞으로_이동할_수_있다() {
        // given
        Position position = new Position(4, 4);

        // when
        Position movedPosition = position.movePosition(Move.FRONT_RIGHT);

        // then
        Position expected = new Position(3, 5);
        Assertions.assertThat(movedPosition).isEqualTo(expected);
    }

    @Test
    void 위치를_오른쪽뒤로_이동할_수_있다() {
        // given
        Position position = new Position(4, 4);

        // when
        Position movedPosition = position.movePosition(Move.BACK_RIGHT);

        // then
        Position expected = new Position(5, 5);
        Assertions.assertThat(movedPosition).isEqualTo(expected);
    }

    @Test
    void 위치를_왼쪽뒤로_이동할_수_있다() {
        // given
        Position position = new Position(4, 4);

        // when
        Position movedPosition = position.movePosition(Move.BACK_LEFT);

        // then
        Position expected = new Position(5, 3);
        Assertions.assertThat(movedPosition).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({
            "5,5,FRONT,true",
            "1,1,BACK,true",
            "1,1,FRONT,false",
            "10,1,BACK,false",
            "1,9,RIGHT,false",

    })
    void 해당_위치로_움직일_수_있는지_확인한다(int row, int column, Move move, boolean expected) {
        //given
        Position position = new Position(row, column);

        //when
        boolean canMove = position.canApplyMove(move);
        //then
        Assertions.assertThat(canMove).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({
            "1,1,0",
            "5,1,0",
            "1,9,-8",
            "10,3,-2",
    })
    void 컬럼값의_차이를_계산한다(int row, int column, int expected) {
        // given
        Position position = new Position(row, column);
        Position comparedPosition = new Position(1, 1);

        // when
        int comparedValue = comparedPosition.compareColumn(position);

        // then
        assertThat(comparedValue).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({
            "1,1,0",
            "5,1,-4",
            "2,9,-1",
            "10,3,-9",
    })
    void 행값을_차이를_비교한다(int row, int column, int expected) {
        // given
        Position position = new Position(row, column);
        Position comparedPosition = new Position(1, 1);

        // when
        int comparedValue = comparedPosition.compareRow(position);

        // then
        assertThat(comparedValue).isEqualTo(expected);
    }

    @Test
    void 왼쪽_끝에서는_왼쪽으로_갈_수_없다() {
        // given
        Position position = new Position(4, 1);
        // when
        boolean actual = position.canApplyMove(Move.LEFT);
        // then
        assertThat(actual).isFalse();
    }

    @Test
    void 오른쪽_끝에서는_오른쪽으로_갈_수_없다() {
        // given
        Position position = new Position(4, 9);
        // when
        boolean actual = position.canApplyMove(Move.RIGHT);
        // then
        assertThat(actual).isFalse();
    }

    @Test
    void 위쪽_끝에서는_위로_갈_수_없다() {
        // given
        Position position = new Position(1, 5);
        // when
        boolean actual = position.canApplyMove(Move.FRONT);
        // then
        assertThat(actual).isFalse();
    }

    @Test
    void 아래쪽_끝에서는_아래로_갈_수_없다() {
        // given
        Position position = new Position(10, 5);
        // when
        boolean actual = position.canApplyMove(Move.BACK);
        // then
        assertThat(actual).isFalse();
    }

    @ParameterizedTest
    @CsvSource({
            "1, 4, true",
            "8, 4, true",
            "2, 4, false",
            "1, 5, false",
            "9, 4, false",
            "7, 4, false",
            "8, 3, false"
    })
    void 현재_위치가_궁성의_왼쪽_위인지_알_수_있다(int row, int column, boolean expected) {
        // given
        Position position = new Position(row, column);
        // when
        boolean actual = position.isPalaceTopLeft();
        // then
        assertThat(actual).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({
            "1, 6, true",
            "8, 6, true",
            "2, 6, false",
            "1, 7, false",
            "9, 6, false",
            "7, 6, false",
            "8, 7, false"
    })
    void 현재_위치가_궁성의_오른쪽_위인지_알_수_있다(int row, int column, boolean expected) {
        // given
        Position position = new Position(row, column);
        // when
        boolean actual = position.isPalaceTopRight();
        // then
        assertThat(actual).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({
            "3, 4, true",
            "10, 4, true",
            "4, 4, false",
            "2, 4, false",
            "3, 3, false",
            "3, 5, false",
            "9, 4, false",
            "10, 5, false",
            "10, 3, false"
    })
    void 현재_위치가_궁성의_왼쪽_아래인지_알_수_있다(int row, int column, boolean expected) {
        // given
        Position position = new Position(row, column);
        // when
        boolean actual = position.isPalaceBottomLeft();
        // then
        assertThat(actual).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({
            "3, 6, true",
            "10, 6, true",
            "4, 6, false",
            "2, 6, false",
            "3, 5, false",
            "3, 7, false",
            "9, 6, false",
            "10, 5, false",
            "10, 7, false"
    })
    void 현재_위치가_궁성의_오른쪽_아래인지_알_수_있다(int row, int column, boolean expected) {
        // given
        Position position = new Position(row, column);
        // when
        boolean actual = position.isPalaceBottomRight();
        // then
        assertThat(actual).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({
            "2, 5, true",
            "9, 5, true",
            "1, 5, false",
            "3, 5, false",
            "2, 4, false",
            "2, 6, false",
            "8, 5, false",
            "10, 5, false",
            "9, 4, false",
            "9, 6, false"
    })
    void 현재_위치가_궁성의_중심인지_알_수_있다(int row, int column, boolean expected) {
        // given
        Position position = new Position(row, column);
        // when
        boolean actual = position.isPalaceCenter();
        // then
        assertThat(actual).isEqualTo(expected);
    }
}