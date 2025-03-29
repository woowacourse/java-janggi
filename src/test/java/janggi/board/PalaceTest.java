package janggi.board;

import janggi.position.Position;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class PalaceTest {
    @ParameterizedTest
    @ValueSource(strings = {
            "0,3,true", "0,4,true", "0,5,true",
            "1,3,true", "1,4,true", "1,5,true",
            "2,3,true", "2,4,true", "2,5,true",

            "7,3,true", "7,4,true", "7,5,true",
            "8,3,true", "8,4,true", "8,5,true",
            "9,3,true", "9,4,true", "9,5,true"
    })
    @DisplayName("궁성 내에 있는지 확인한다.")
    void test1(String point) {
        // given
        String[] position = point.split(",");
        int column = Integer.parseInt(position[0]);
        int row = Integer.parseInt(position[1]);
        boolean expected = Boolean.getBoolean(position[2]);

        // when
        boolean inPalace = Palace.isInPalace(new Position(column, row));

        // then
        Assertions.assertThat(inPalace).isEqualTo(expected);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "0,3,true", "0,4,false", "0,5,true",
            "1,3,false", "1,4,true", "1,5,false",
            "2,3,true", "2,4,false", "2,5,true",

            "7,3,true", "7,4,false", "7,5,true",
            "8,3,false", "8,4,true", "8,5,false",
            "9,3,true", "9,4,false", "9,5,true"
    })
    @DisplayName("궁성 내 대각선 이동이 가능한 지점에 있는지 확인한다.")
    void test2(String point) {
        // given
        String[] position = point.split(",");
        int column = Integer.parseInt(position[0]);
        int row = Integer.parseInt(position[1]);
        boolean expected = Boolean.getBoolean(position[2]);

        // when
        boolean inPalace = Palace.canDiagonalInPalace(new Position(column, row));

        // then
        Assertions.assertThat(inPalace).isEqualTo(expected);
    }
}