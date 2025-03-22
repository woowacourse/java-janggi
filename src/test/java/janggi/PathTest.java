package janggi;

import static janggi.fixture.PositionFixture.createPosition;
import static org.assertj.core.api.Assertions.assertThat;

import janggi.board.Position;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PathTest {
    @DisplayName("시작과_끝을_제외한_중간_경로를_반환할_수_있다")
    @Test
    void getIntermediatePath() {
        // given
        Position startPosition = createPosition(1, 2);
        Position intermediatePosition = createPosition(1, 2);
        Position goalPosition = createPosition(2, 4);
        Path path = new Path(List.of(startPosition, intermediatePosition, goalPosition));

        // when
        List<Position> result = path.getIntermediatePath();

        // then
        assertThat(result).containsExactly(intermediatePosition);
    }

    @DisplayName("경로의_끝_Position이_주어진_Position과_일치하는_지_여부를_반환한다")
    @Test
    void lastEquals() {
        // given
        Position startPosition = createPosition(1, 2);
        Position intermediatePosition = createPosition(1, 2);
        Position goalPosition = createPosition(2, 4);
        Path path = new Path(List.of(startPosition, intermediatePosition, goalPosition));

        // when
        boolean result = path.lastEquals(goalPosition);

        // then
        assertThat(result).isTrue();
    }
}
