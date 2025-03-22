package janggi.domain.position;

import janggi.BaseTest;
import janggi.domain.piece.MoveDirection;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import org.junit.jupiter.api.Test;

import java.util.List;

import static janggi.domain.position.PositionFile.*;
import static janggi.domain.position.PositionRank.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;

class PathTest extends BaseTest {

    @Test
    void 시작_위치를_통해_패스를_생성한다() {
        // given
        final Position startPosition = new Position(FILE_5, RANK_5);
        final Path expectedPath = new Path(startPosition, List.of(startPosition));

        // when
        final Path path = Path.start(startPosition);

        // then
        assertThat(path).isEqualTo(expectedPath);
    }

    @Test
    void 다음_위치들을_통해_새로운_패스_위치들을_반환한다() {
        // given
        final Position startPosition = new Position(FILE_5, RANK_5);
        final Path path = Path.start(startPosition);
        final List<Position> nextPositions = List.of(
                new Position(FILE_5, RANK_4),
                new Position(FILE_5, RANK_6),
                new Position(FILE_4, RANK_5),
                new Position(FILE_6, RANK_5)
        );

        // when
        final List<Path> result = path.nextPath(nextPositions);

        // then
        assertThat(result).containsExactlyInAnyOrder(
                new Path(new Position(FILE_5, RANK_4), List.of(new Position(FILE_5, RANK_5), new Position(FILE_5, RANK_4))),
                new Path(new Position(FILE_5, RANK_6), List.of(new Position(FILE_5, RANK_5), new Position(FILE_5, RANK_6))),
                new Path(new Position(FILE_4, RANK_5), List.of(new Position(FILE_5, RANK_5), new Position(FILE_4, RANK_5))),
                new Path(new Position(FILE_6, RANK_5), List.of(new Position(FILE_5, RANK_5), new Position(FILE_6, RANK_5)))
        );
    }

    @Test
    void 현재_위치에서_이동_방향을_통해_경로를_구할_수_있다() {
        // given
        final Position startPosition = new Position(PositionFile.FILE_5, RANK_5);

        // when
        final List<Path> result = Path.getMoveablePaths(startPosition, List.of(MoveDirection.DOWN, MoveDirection.DOWN_LEFT));

        // then
        assertThat(result).containsExactlyInAnyOrder(new Path(
                new Position(PositionFile.FILE_4, RANK_3),
                List.of(new Position(PositionFile.FILE_5, RANK_5), new Position(PositionFile.FILE_5, RANK_4), new Position(PositionFile.FILE_4, RANK_3))
        ));
    }

    @Test
    void 중간에_마주치는_기물들을_반환할_수_있다() {
        // given
        final Path path = new Path(new Position(FILE_5, RANK_5), List.of(
                new Position(FILE_5, RANK_1),
                new Position(FILE_5, RANK_2),
                new Position(FILE_5, RANK_3),
                new Position(FILE_5, RANK_4),
                new Position(FILE_5, RANK_5)
        ));

        // when
        final List<Piece> result = path.getEncounteredMiddlePieces(List.of(
                new Piece(new Position(FILE_5, RANK_2), PieceType.마),
                new Piece(new Position(FILE_5, RANK_3), PieceType.마),
                new Piece(new Position(FILE_8, RANK_7), PieceType.졸),
                new Piece(new Position(FILE_8, RANK_7), PieceType.졸)
        ));

        // then
        assertAll(
                () -> assertThat(result.getFirst().getPosition()).isEqualTo(new Position(FILE_5, RANK_2)),
                () -> assertThat(result.get(1).getPosition()).isEqualTo(new Position(FILE_5, RANK_3))
        );
    }

    @Test
    void 마지막에_기물을_마주치는지_반환할_수_있다() {
        // given
        final Path path = new Path(new Position(FILE_5, RANK_5), List.of(
                new Position(FILE_5, RANK_1),
                new Position(FILE_5, RANK_2),
                new Position(FILE_5, RANK_3),
                new Position(FILE_5, RANK_4),
                new Position(FILE_5, RANK_5)
        ));

        // when
        final boolean result = path.isEncounteredLast(List.of(
                new Piece(new Position(FILE_5, RANK_9), PieceType.장),
                new Piece(new Position(FILE_8, RANK_7), PieceType.상),
                new Piece(new Position(FILE_5, RANK_5), PieceType.졸)
        ));

        // then
        assertThat(result).isTrue();
    }

    @Test
    void 마지막에_기물을_마주치지_않으면_false를_반환한다() {
        // given
        final Path path = new Path(new Position(FILE_5, RANK_5), List.of(
                new Position(FILE_5, RANK_1),
                new Position(FILE_5, RANK_2),
                new Position(FILE_5, RANK_3),
                new Position(FILE_5, RANK_4),
                new Position(FILE_5, RANK_5)
        ));

        // when
        final boolean result = path.isEncounteredLast(List.of(
                new Piece(new Position(FILE_1, RANK_9), PieceType.장),
                new Piece(new Position(FILE_1, RANK_7), PieceType.상),
                new Piece(new Position(FILE_1, RANK_5), PieceType.졸)
        ));

        // then
        assertThat(result).isFalse();
    }
}
