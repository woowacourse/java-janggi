package janggi;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ChaTest {

    @DisplayName("시작 위치와 끝 위치로 유효한 경로를 찾는다")
    @Test
    void getLegalPath(){
        //given
        Position from = new Position(Row.SIX, Column.THREE);
        Position to = new Position(Row.TWO, Column.THREE);
        Cha cha = new Cha(Team.CHO);

        //when & then
        Path path = cha.getLegalPath(from, to);
        assertThat(path).isNotNull();
    }


}