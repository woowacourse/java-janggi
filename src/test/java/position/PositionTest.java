package position;

import static position.Column.I;
import static position.Row.ZERO;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PositionTest {

    @Test
    @DisplayName("기물의 위치는 row와 column으로 나타낼 수 있다.")
    void createPalaceTest(){
        Position position = new Position(I, ZERO);
    }
}
