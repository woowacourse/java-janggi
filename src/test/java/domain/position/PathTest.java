package domain.position;

import org.junit.jupiter.api.Test;
import testUtil.TestConstant;

import java.util.List;

import static domain.position.PositionFile.*;
import static org.assertj.core.api.Assertions.*;
import static testUtil.TestConstant.*;

class PathTest {

    @Test
    void 시작_위치를_통해_패스를_생성한다() {
        // given
        final Position startPosition = new Position(마, TestConstant.RANK_5);
        final Path expectedPath = new Path(startPosition, List.of(startPosition));

        // when
        final Path path = Path.start(startPosition);

        // then
        assertThat(path).isEqualTo(expectedPath);
    }

    @Test
    void 다음_위치들을_통해_새로운_패스_위치들을_반환한다() {
        // given
        final Position startPosition = new Position(마, RANK_5);
        final Path path = Path.start(startPosition);
        final List<Position> nextPositions = List.of(
                new Position(마, RANK_4),
                new Position(마, RANK_6),
                new Position(라, RANK_5),
                new Position(바, RANK_5)
        );

        // when
        final List<Path> result = path.nextPath(nextPositions);

        // then
        assertThat(result).containsExactlyInAnyOrder(
                new Path(new Position(마, RANK_4), List.of(new Position(마, RANK_5), new Position(마, RANK_4))),
                new Path(new Position(마, RANK_6), List.of(new Position(마, RANK_5), new Position(마, RANK_6))),
                new Path(new Position(라, RANK_5), List.of(new Position(마, RANK_5), new Position(라, RANK_5))),
                new Path(new Position(바, RANK_5), List.of(new Position(마, RANK_5), new Position(바, RANK_5)))
        );
    }

    @Test
    void 해당_경로가_어떠한_위치를_포함한지_반환한다() {
        final Path path = new Path(new Position(마, RANK_4), List.of(new Position(마, RANK_5), new Position(마, RANK_4)));

        // when
        final boolean result = path.havePosition(List.of(new Position(마, RANK_5)));

        // then
        assertThat(result).isTrue();
    }

    @Test
    void 해당_경로가_어떠한_위치를_포함하지_않는지_반환한다() {
        final Path path = new Path(new Position(마, RANK_4), List.of(new Position(마, RANK_5), new Position(마, RANK_4)));

        // when
        final boolean result = path.havePosition(List.of(new Position(라, RANK_5)));

        // then
        assertThat(result).isFalse();
    }
}
