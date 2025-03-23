package janggi.position;

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
}
