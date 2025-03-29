package janggi.coordinate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class VectorTest {

    @Test
    @DisplayName("Position 두 개로부터 방향 벡터를 생성할 수 있다")
    void createVectorFromPositions() {
        // given
        final Position from = Position.of(2, 3);
        final Position to = Position.of(5, 6);

        // when
        final Vector vector = Vector.of(from, to);

        // then
        assertThat(vector).isEqualTo(new Vector(3, 3));
    }

    @Test
    @DisplayName("벡터 덧셈은 각각의 delta를 더한 결과이다")
    void addVector() {
        // given
        final Vector v1 = new Vector(2, 3);
        final Vector v2 = new Vector(-1, 4);

        // when
        final Vector result = v1.add(v2);

        // then
        assertThat(result).isEqualTo(new Vector(1, 7));
    }

    @Test
    @DisplayName("벡터 뺄셈은 각각의 delta를 뺀 결과이다")
    void subtractVector() {
        // given
        final Vector v1 = new Vector(5, 6);
        final Vector v2 = new Vector(2, 3);

        // when
        final Vector result = v1.subtract(v2);

        // then
        assertThat(result).isEqualTo(new Vector(3, 3));
    }

    @Test
    @DisplayName("대각선이 아닐 경우 대각선 이동이 가능하도록 직선 방향을 추출할 수 있다")
    void extractStraightForDiagonal() {
        // given
        final Vector vector = new Vector(5, 3);

        // when
        final Vector straight = vector.extractStraightForDiagonal();

        // then
        assertThat(straight).isEqualTo(new Vector(2, 0));
    }

    @Test
    @DisplayName("이미 대각선인 경우 보정 벡터는 (0,0)이다")
    void extractStraightForPerfectDiagonal() {
        // given
        final Vector diagonal = new Vector(3, 3);

        // when
        final Vector straight = diagonal.extractStraightForDiagonal();

        // then
        assertThat(straight).isEqualTo(new Vector(0, 0));
    }

    @Test
    @DisplayName("벡터를 단위 벡터들로 분해할 수 있다 (양수)")
    void splitToUnitVectors() {
        // given
        final Vector vector = new Vector(3, 2);

        // when
        final List<Vector> units = vector.splitToUnitVectors();

        // then
        assertThat(units).containsExactly(
                new Vector(1, 0),
                new Vector(1, 1),
                new Vector(1, 1)
        );
    }

    @Test
    @DisplayName("벡터를 단위 벡터들로 분해할 수 있다 (음수)")
    void splitToNegativeUnitVectors() {
        // given
        final Vector vector = new Vector(-2, -3);

        // when
        final List<Vector> units = vector.splitToUnitVectors();

        // then
        assertThat(units).containsExactly(
                new Vector(0, -1),
                new Vector(-1, -1),
                new Vector(-1, -1)
        );
    }

    @Test
    @DisplayName("벡터를 단위 벡터들로 분해할 수 있다 (양수, 음수)")
    void splitToMixedUnitVectors() {
        // given
        final Vector vector = new Vector(2, -3);

        // when
        final List<Vector> units = vector.splitToUnitVectors();

        // then
        assertThat(units).containsExactly(
                new Vector(0, -1),
                new Vector(1, -1),
                new Vector(1, -1)
        );
    }
}
