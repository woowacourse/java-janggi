package model;

import java.util.List;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

class PathTest {

    @Test
    void 목적지와_이동_경로를_계산한다() {
        Path path = new Path(new Position(5, 5), List.of(Direction.TOP, Direction.LEFT_TOP));

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(path.getDestinationPosition()).isEqualTo(new Position(3, 4));
        softly.assertThat(path.getCornerPositions()).contains(new Position(4, 5));
        softly.assertAll();
    }
}
