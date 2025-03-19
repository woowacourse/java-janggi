package janggi.position;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class PositionYTest {
    @DisplayName("position Y 값 갱신 확인")
    @Test
    void exceptionPositionYTest(){
        PositionY positionY = new PositionY(0);
        PositionY expectedPositionY = positionY.plus(1);
        assertThat(expectedPositionY.getValue()).isEqualTo(1);
    }
}
