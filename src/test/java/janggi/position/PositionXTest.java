package janggi.position;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class PositionXTest {
    @DisplayName("position x 값 갱신 확인")
    @Test
    void exceptionPositionXTest(){
        PositionX positionX = new PositionX(0);
        PositionX expectedPositionX = positionX.plus(1);
        assertThat(expectedPositionX.getValue()).isEqualTo(1);
    }
}
