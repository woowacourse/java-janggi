package janggi.board;

import janggi.position.Position;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class PalaceTest {
    @ParameterizedTest
    @ValueSource(strings = {
            "3,0,true", "4,0,true", "5,0,true",
            "3,1,true", "4,1,true", "5,1,true",
            "3,2,true", "4,2,true", "5,2,true",

            "3,7,true", "4,7,true", "5,7,true",
            "3,8,true", "4,8,true", "5,8,true",
            "3,9,true", "4,9,true", "5,9,true"
    })
    @DisplayName("궁성 내에 있는지 확인한다.")
    void test1(String point) {
        // given
        String[] position = point.split(",");
        int column = Integer.parseInt(position[0]);
        int row = Integer.parseInt(position[1]);
        boolean expected = Boolean.parseBoolean(position[2]);

        // when
        boolean inPalace = Palace.isInPalace(new Position(column, row));

        // then
        Assertions.assertThat(inPalace).isEqualTo(expected);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "3,0,true", "4,0,false", "5,0,true",
            "3,1,false", "4,1,true", "5,1,false",
            "3,2,true", "4,2,false", "5,2,true",

            "3,7,true", "4,7,false", "5,7,true",
            "3,8,false", "4,8,true", "5,8,false",
            "3,9,true", "4,9,false", "5,9,true"
    })
    @DisplayName("궁성 내 대각선 이동이 가능한 지점에 있는지 확인한다.")
    void test2(String point) {
        // given
        String[] position = point.split(",");
        int column = Integer.parseInt(position[0]);
        int row = Integer.parseInt(position[1]);
        boolean expected = Boolean.parseBoolean(position[2]);

        // when
        boolean inPalace = Palace.canDiagonalInPalace(new Position(column, row));

        // then
        Assertions.assertThat(inPalace).isEqualTo(expected);
    }
}
