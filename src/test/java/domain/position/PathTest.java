package domain.position;

import domain.piece.MoveDirection;
import domain.piece.Piece;
import domain.piece.PieceType;
import org.junit.jupiter.api.Test;
import testUtil.TestConstant;

import java.util.List;

import static domain.position.PositionFile.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;
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
    void 현재_위치에서_이동_방향을_통해_경로를_구할_수_있다() {
        // given
        final Position startPosition = new Position(PositionFile.마, RANK_5);

        // when
        final List<Path> result = Path.getMoveablePaths(startPosition, List.of(MoveDirection.DOWN, MoveDirection.DOWN_LEFT));

        // then
        assertThat(result).containsExactlyInAnyOrder(new Path(
                new Position(PositionFile.라, RANK_3),
                List.of(new Position(PositionFile.마, RANK_5), new Position(PositionFile.마, RANK_4), new Position(PositionFile.라, RANK_3))
        ));
    }

    @Test
    void 중간에_마주치는_피스들을_반환할_수_있다() {
        // given
        final Path path = new Path(new Position(마, RANK_5), List.of(
                new Position(마, RANK_1),
                new Position(마, RANK_2),
                new Position(마, RANK_3),
                new Position(마, RANK_4),
                new Position(마, RANK_5)
        ));

        // when
        final List<Piece> result = path.getEncounteredMiddlePieces(List.of(
                new Piece(new Position(마, RANK_2), PieceType.마),
                new Piece(new Position(마, RANK_3), PieceType.마),
                new Piece(new Position(아, RANK_7), PieceType.졸),
                new Piece(new Position(아, RANK_7), PieceType.졸)
        ));

        // then
        assertAll(
                () -> assertThat(result.getFirst().getPosition()).isEqualTo(new Position(마, RANK_2)),
                () -> assertThat(result.get(1).getPosition()).isEqualTo(new Position(마, RANK_3))
        );
    }

    @Test
    void 마지막에_피스를_마주치는지_반환할_수_있다() {
        // given
        final Path path = new Path(new Position(마, RANK_5), List.of(
                new Position(마, RANK_1),
                new Position(마, RANK_2),
                new Position(마, RANK_3),
                new Position(마, RANK_4),
                new Position(마, RANK_5)
        ));

        // when
        final boolean result = path.isEncounteredLast(List.of(
                new Piece(new Position(마, RANK_9), PieceType.장),
                new Piece(new Position(아, RANK_7), PieceType.상),
                new Piece(new Position(마, RANK_5), PieceType.졸)
        ));

        // then
        assertThat(result).isTrue();
    }

    @Test
    void 마지막에_피스를_마주치지_않으면_false를_반환한다() {
        // given
        final Path path = new Path(new Position(마, RANK_5), List.of(
                new Position(마, RANK_1),
                new Position(마, RANK_2),
                new Position(마, RANK_3),
                new Position(마, RANK_4),
                new Position(마, RANK_5)
        ));

        // when
        final boolean result = path.isEncounteredLast(List.of(
                new Piece(new Position(가, RANK_9), PieceType.장),
                new Piece(new Position(가, RANK_7), PieceType.상),
                new Piece(new Position(가, RANK_5), PieceType.졸)
        ));

        // then
        assertThat(result).isFalse();
    }
}
