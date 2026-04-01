package domain.movement;

import static org.assertj.core.api.Assertions.assertThat;

import domain.board.Intersection;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class VectorTest {

    private static final Intersection DEFAULT_INTERSECTION = new Intersection(5, 5);

    @ParameterizedTest
    @CsvSource({
            "-1, 0", "-1, -1", "-1, 1",
            "0, -1", "0, 1", "0, 0",
            "1, 0", "1, -1", "1, 1",
    })
    void 값만큼_좌표를_이동시킨다(int rowDelta, int fileDelta) {
        // given
        Vector vector = new Vector(rowDelta, fileDelta);

        Intersection expected = new Intersection(
                DEFAULT_INTERSECTION.getRow() + rowDelta,
                DEFAULT_INTERSECTION.getFile() + fileDelta
        );

        // when
        Intersection movedIntersection = vector.next(DEFAULT_INTERSECTION);

        // then
        assertThat(movedIntersection).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({
            "-1, 0, -1, 1",
            "1, 0, 1, -1",
            "0, -1, -1, -1",
            "0, 1, 1, 1",
            "-1, -1, -1, 0",
            "-1, 1, 0, 1",
            "1, -1, 0, -1",
            "1, 1, 1, 0",
            "0, 0, 0, 0",
    })
    void 왼쪽으로_45도_돌린_벡터를_반환한다(
            int rowDelta,
            int fileDelta,
            int expectedRowDelta,
            int expectedFileDelta
    ) {
        Vector vector = new Vector(rowDelta, fileDelta);
        Vector expected = new Vector(expectedRowDelta, expectedFileDelta);

        assertThat(vector.turnLeft45Degrees()).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({
            "-1, 0, -1, -1",
            "1, 0, 1, 1",
            "0, -1, 1, -1",
            "0, 1, -1, 1",
            "-1, -1, 0, -1",
            "-1, 1, -1, 0",
            "1, -1, 1, 0",
            "1, 1, 0, 1",
            "0, 0, 0, 0",
    })
    void 오른쪽으로_45도_돌린_벡터를_반환한다(
            int rowDelta,
            int fileDelta,
            int expectedRowDelta,
            int expectedFileDelta
    ) {
        Vector vector = new Vector(rowDelta, fileDelta);
        Vector expected = new Vector(expectedRowDelta, expectedFileDelta);

        assertThat(vector.turnRight45Degrees()).isEqualTo(expected);
    }
}
