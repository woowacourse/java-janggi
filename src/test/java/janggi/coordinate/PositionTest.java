package janggi.coordinate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PositionTest {

    @Test
    @DisplayName("Position에 Vector를 더할 수 있다")
    void canAddVectorToPosition() {
        //given
        Position position = Position.of(2, 1);
        Vector vector = new Vector(1, 1);

        //when
        Position actual = position.add(vector);

        //then
        assertThat(actual).isEqualTo(Position.of(3, 2));
    }
}
