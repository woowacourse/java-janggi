package janggi.domain.value;

import janggi.domain.piece.direction.DirectionVector;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;


class DirectionVectorTest {

    //생성 확인
    @Test
    void test1() {
        //given
        int x = -1;
        int y = 10;

        //when
        DirectionVector directionVector = new DirectionVector(x, y);

        //then
        Assertions.assertThat(directionVector).isInstanceOf(DirectionVector.class);
    }


}