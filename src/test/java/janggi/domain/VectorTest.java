package janggi.domain;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.move.Vector;
import janggi.domain.move.Vectors;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class VectorTest {

    @DisplayName("주어진 Vector의 행열 값을 90도로 회전한다.")
    @Test
    void test1() {
        // given
        List<Vectors> vectors = List.of(Vectors.of(new Vector(1, 2), new Vector(3, 4)));

        // when
        List<Vectors> actual = Vectors.rotate(vectors);
        List<Vectors> expected = List.of(Vectors.of(new Vector(2, -1), new Vector(4, -3)));

        // then
        assertThat(actual).isEqualTo(expected);
    }
}
