package janggi.position;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class PositionTest {
    @Test
    @DisplayName("row,column에 따른 position 생성 확인")
    void positionCreateTest() {
        //given
        Position position = new Position(new Row(2), new Column(3));
        //when
        int expectedX = position.getRow();
        int expectedY = position.getColumn();
        //then
        assertAll(
                () -> assertThat(expectedX).isEqualTo(2),
                () -> assertThat(expectedY).isEqualTo(3)
        );
    }

    @Test
    @DisplayName("Position이 board 이용 가능한 위치가 아니면 True")
    void outOfBoardTrueTest() {
        //given
        //when
        //then
        assertAll(
                () -> Assertions.assertThat(new Position(new Row(11), new Column(10)).isOutOfBoards()).isTrue(),
                () -> Assertions.assertThat(new Position(new Row(0), new Column(0)).isOutOfBoards()).isTrue()
        );
    }

    @Test
    @DisplayName("Position이 board 이용 가능한 위치에 있으면 False")
    void outOfBoardFalseTest() {
        //given
        //when
        //then
        assertAll(
                () -> Assertions.assertThat(new Position(new Row(10), new Column(9)).isOutOfBoards()).isFalse(),
                () -> Assertions.assertThat(new Position(new Row(1), new Column(1)).isOutOfBoards()).isFalse()
        );
    }
}
