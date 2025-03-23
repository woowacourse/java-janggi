package janggi.value;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;


class RelativePositionTest {

    //생성 확인
    @Test
    void test1() {
        //given
        int x = -1;
        int y = 10;

        //when
        RelativePosition relativePosition = new RelativePosition(x, y);

        //then
        Assertions.assertThat(relativePosition).isInstanceOf(RelativePosition.class);
    }


}