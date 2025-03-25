package janggi.domain.position;

import janggi.BaseTest;
import org.junit.jupiter.api.Test;

import java.util.List;

import static janggi.domain.position.PositionFile.*;
import static janggi.domain.position.PositionRank.*;
import static org.assertj.core.api.Assertions.*;

class PathTest extends BaseTest {

    @Test
    void 시작_위치를_통해_패스를_생성한다() {
        // given
        final Position startPosition = new Position(FILE_5, RANK_5);
        final Path expectedPath = Path.start(startPosition);

        // when
        final Path path = Path.start(startPosition);

        // then
        assertThat(path).isEqualTo(expectedPath);
    }

    @Test
    void 다음_위치를_통해_새로운_패스_위치를_반환한다() {
        // given
        final Position startPosition = new Position(FILE_5, RANK_5);
        final Path path = Path.start(startPosition);
        final Position nextPosition = new Position(FILE_5, RANK_4);

        // when
        final Path result = path.nextPath(nextPosition);

        // then
        assertThat(result).isEqualTo(
                new Path(List.of(new Position(FILE_5, RANK_5), new Position(FILE_5, RANK_4)))
        );
    }

    @Test
    void 현재_위치에서_이동_방향을_통해_경로를_구할_수_있다() {
        // given
        final Position startPosition = new Position(PositionFile.FILE_5, RANK_5);

        // when
        final Path result = Path.start(startPosition).nextPath(Movement.DOWN_DOWNLEFT);

        // then
        assertThat(result).isEqualTo(
                new Path(List.of(new Position(PositionFile.FILE_5, RANK_5), new Position(PositionFile.FILE_5, RANK_4), new Position(PositionFile.FILE_4, RANK_3))
        ));
    }

    @Test
    void 중간에_마주치는_기물들을_반환할_수_있다() {
        // given
        final Path path = new Path(List.of(
                new Position(FILE_5, RANK_1),
                new Position(FILE_5, RANK_2),
                new Position(FILE_5, RANK_3),
                new Position(FILE_5, RANK_4),
                new Position(FILE_5, RANK_5)
        ));

        // when
        final boolean result = path.isBlockedWith(List.of(
                new Position(FILE_5, RANK_2),
                new Position(FILE_5, RANK_3),
                new Position(FILE_8, RANK_7),
                new Position(FILE_8, RANK_7)
        ));

        // then
        assertThat(result).isTrue();
    }

    @Test
    void 마지막에_기물을_마주치는지_반환할_수_있다() {
        // given
        final Path path = new Path(List.of(
                new Position(FILE_5, RANK_1),
                new Position(FILE_5, RANK_2),
                new Position(FILE_5, RANK_3),
                new Position(FILE_5, RANK_4),
                new Position(FILE_5, RANK_5)
        ));

        // when
        final boolean result = path.isEndWith(List.of(
                new Position(FILE_5, RANK_9),
                new Position(FILE_8, RANK_7),
                new Position(FILE_5, RANK_5)
        ));

        // then
        assertThat(result).isTrue();
    }

    @Test
    void 마지막에_기물을_마주치지_않으면_false를_반환한다() {
        // given
        final Path path = new Path(List.of(
                new Position(FILE_5, RANK_1),
                new Position(FILE_5, RANK_2),
                new Position(FILE_5, RANK_3),
                new Position(FILE_5, RANK_4),
                new Position(FILE_5, RANK_5)
        ));

        // when
        final boolean result = path.isEndWith(List.of(
                new Position(FILE_1, RANK_9),
                new Position(FILE_1, RANK_7),
                new Position(FILE_1, RANK_5)
        ));

        // then
        assertThat(result).isFalse();
    }
}
