package janggi.domain.path;

import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import janggi.domain.position.Movement;
import janggi.domain.position.Position;
import janggi.domain.position.PositionFile;
import janggi.test_util.BaseTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static janggi.domain.position.PositionFile.*;
import static janggi.domain.position.PositionRank.*;
import static janggi.test_util.TestConstant.*;
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

    @ParameterizedTest
    @MethodSource("provideNextPositionAndResult")
    void 다음_위치를_통해_새로운_패스_위치를_반환한다(final Position nextPosition, final List<Position> expected) {
        // given
        final Position startPosition = new Position(FILE_5, RANK_5);
        final Path path = Path.start(startPosition);

        // when
        final Path result = path.nextPath(nextPosition);

        // then
        assertThat(result).isEqualTo(new Path(expected));
    }

    public static Stream<Arguments> provideNextPositionAndResult() {
        return Stream.of(
                Arguments.of(POSITION_5_3, List.of(POSITION_5_5, POSITION_5_4, POSITION_5_3)),
                Arguments.of(POSITION_5_7, List.of(POSITION_5_5, POSITION_5_6, POSITION_5_7)),
                Arguments.of(POSITION_3_5, List.of(POSITION_5_5, POSITION_4_5, POSITION_3_5)),
                Arguments.of(POSITION_7_5, List.of(POSITION_5_5, POSITION_6_5, POSITION_7_5))
        );
    }

    @Test
    void 현재_위치에서_이동_방향을_통해_경로를_구할_수_있다() {
        // given
        final Position startPosition = new Position(PositionFile.FILE_5, RANK_5);

        // when
        final Optional<Path> result = Path.start(startPosition).nextPath(Movement.DOWN_DOWNLEFT);

        // then
        assertThat(result.get()).isEqualTo(new Path(List.of(
                new Position(PositionFile.FILE_5, RANK_5),
                new Position(PositionFile.FILE_5, RANK_4),
                new Position(PositionFile.FILE_4, RANK_3))
        ));
    }

    @Test
    void 중간에_마주치는_기물들을_반환할_수_있다() {
        // given
        final List<Piece> pieces = List.of(
                new Piece(PieceType.SA, POSITION_3_3),
                new Piece(PieceType.SA, POSITION_4_4),
                new Piece(PieceType.SA, POSITION_5_5),
                new Piece(PieceType.SA, POSITION_6_6),
                new Piece(PieceType.SA, POSITION_6_8),
                new Piece(PieceType.SA, POSITION_2_4),
                new Piece(PieceType.SA, POSITION_1_3)
        );

        final Path path = new Path(List.of(POSITION_3_3, POSITION_4_4, POSITION_5_5, POSITION_6_6));

        // when
        final List<Piece> result = path.getBlockedPiece(pieces);

        // then
        assertThat(result).extracting("position")
                .containsExactlyInAnyOrder(POSITION_3_3, POSITION_4_4, POSITION_5_5);
    }

    @Test
    void 중간에_마주치는_위치들을_반환할_수_있다() {
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

    @Test
    void 하위_Path들을_반환한다() {
        // given
        final Path path = new Path(List.of(
                new Position(FILE_5, RANK_1),
                new Position(FILE_5, RANK_2),
                new Position(FILE_5, RANK_3)
        ));

        // when
        final List<Path> result = path.subPathAndReverse();

        // then
        assertThat(result).containsExactlyInAnyOrder(
                new Path(List.of(new Position(FILE_5, RANK_1), new Position(FILE_5, RANK_2), new Position(FILE_5, RANK_3))),
                new Path(List.of(new Position(FILE_5, RANK_3), new Position(FILE_5, RANK_2), new Position(FILE_5, RANK_1))),
                new Path(List.of(new Position(FILE_5, RANK_1), new Position(FILE_5, RANK_2))),
                new Path(List.of(new Position(FILE_5, RANK_2), new Position(FILE_5, RANK_1))),
                new Path(List.of(new Position(FILE_5, RANK_2), new Position(FILE_5, RANK_3))),
                new Path(List.of(new Position(FILE_5, RANK_3), new Position(FILE_5, RANK_2)))
        );
    }

    @Test
    void 평행이동_한다() {
        // given
        final Path path = new Path(List.of(
                new Position(FILE_5, RANK_1),
                new Position(FILE_5, RANK_2),
                new Position(FILE_5, RANK_3)
        ));

        // when
        final Path result = path.parallelMove(3, 3);

        // then
        assertThat(result).isEqualTo(new Path(List.of(
                new Position(FILE_8, RANK_4), new Position(FILE_8, RANK_5), new Position(FILE_8, RANK_6)
        )));
    }
}
